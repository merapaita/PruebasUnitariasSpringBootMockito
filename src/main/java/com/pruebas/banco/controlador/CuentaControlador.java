package com.pruebas.banco.controlador;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.entidades.TransaccionDto;
import com.pruebas.banco.servicio.CuentaServicio;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {
	
	@Autowired
	private CuentaServicio cuentaServicio;
	
	@GetMapping
	@ResponseStatus(code=HttpStatus.OK)
	public List<Cuenta> listarCuentas() {
		return cuentaServicio.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(code=HttpStatus.OK)
	public Cuenta verDetalles(@PathVariable Long id) {
		return cuentaServicio.findById(id);
	}
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Cuenta guardarCuenta(@RequestBody Cuenta cuenta) {
		return cuentaServicio.save(cuenta);
	}
	
	@PostMapping("/transferir")
	public ResponseEntity<?> tranferirDinero(@RequestBody TransaccionDto transaccionDto){
		cuentaServicio.TransferirDinero(transaccionDto.getCuentaOrigenId(), transaccionDto.getCuentaDestinoId(), transaccionDto.getMonto(), transaccionDto.getBancoId());
		Map<String,Object> respuesta = new HashMap<>();
		respuesta.put("date", LocalDate.now().toString());
		respuesta.put("status", "OK");
		respuesta.put("mensaje", "Transferencia Realizada con Exito");
		respuesta.put("transaccionDto", transaccionDto);
		
		return ResponseEntity.ok(respuesta);
		
	}

}
