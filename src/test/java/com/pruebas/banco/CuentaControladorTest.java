package com.pruebas.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.banco.controlador.CuentaControlador;
import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.entidades.TransaccionDto;
import com.pruebas.banco.servicio.CuentaServicio;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@WebMvcTest(CuentaControlador.class)
public class CuentaControladorTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CuentaServicio cuentaServicio;
	
	ObjectMapper objectMapper;
	
	@BeforeEach
	void configurar(){
		objectMapper = new ObjectMapper();
	}
	
	@Test
	void testVerDetalles() throws Exception {
		when(cuentaServicio.findById(1L)).thenReturn(Datos.crearCuenta001().orElseThrow());
		
		mockMvc.perform(get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.persona").value("christian"))
		.andExpect(jsonPath("$.saldo").value("1000"));

		verify(cuentaServicio).findById(1L);
	}
	

	@Test
	void testTransferirDinero() throws Exception {
		TransaccionDto transaccionDto = new TransaccionDto();
		transaccionDto.setCuentaOrigenId(1L);
		transaccionDto.setCuentaDestinoId(2L);
		transaccionDto.setMonto(new BigDecimal("100"));
		transaccionDto.setBancoId(1L);
		
		System.out.println(objectMapper.writeValueAsString(transaccionDto));
		
		Map<String, Object> respuesta = new HashMap<>();
		
		respuesta.put("date", LocalDate.now().toString());
		respuesta.put("status", "OK");
		respuesta.put("mensaje", "Transferencia Realizada con Exito");
		respuesta.put("transaccionDto", transaccionDto);
		
		System.out.println(objectMapper.writeValueAsString(respuesta));
		
		mockMvc.perform(post("/api/cuentas/transferir").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transaccionDto)))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
		.andExpect(jsonPath("$.mensaje").value("Transferencia Realizada con Exito"))
		.andExpect(jsonPath("$.transaccionDto.cuentaOrigenId").value(transaccionDto.getCuentaOrigenId()))
		.andExpect(content().json(objectMapper.writeValueAsString(respuesta)));

	}
	
	@Test
	void testListarCuentas() throws Exception {
		List<Cuenta> cuentas = Arrays.asList(Datos.crearCuenta001().orElseThrow(),Datos.crearCuenta002().orElseThrow());
		when(cuentaServicio.findAll()).thenReturn(cuentas);
		
		mockMvc.perform(get("/api/cuentas").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].persona").value("christian"))
		.andExpect(jsonPath("$[1].persona").value("Juan"))
		.andExpect(jsonPath("$[0].saldo").value("1000"))
		.andExpect(jsonPath("[1].saldo").value("2000"))
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(content().json(objectMapper.writeValueAsString(cuentas)));
		
		verify(cuentaServicio).findAll();
		
	}
	
	@Test
	void testguardarCuenta() throws Exception {
		Cuenta cuenta = new Cuenta(null, "Biagio", new BigDecimal("3000"));
		when(cuentaServicio.save(any())).then(invocation -> {
			Cuenta c = invocation.getArgument(0);
			c.setId(3L);
			return c;
		});
		
		mockMvc.perform(post("/api/cuentas").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cuenta)))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id").value("3"))
		.andExpect(jsonPath("$.persona").value("Biagio"))
		.andExpect(jsonPath("$.saldo").value(3000));
		
		verify(cuentaServicio).save(any());
		
	}
	
	
}
