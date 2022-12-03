package com.pruebas.banco.servicio;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebas.banco.entidades.Banco;
import com.pruebas.banco.entidades.Cuenta;
import com.pruebas.banco.reporitory.BancoRepositorio;
import com.pruebas.banco.reporitory.CuentaRepositorio;

@Service
public class CuentaServicioImpl implements CuentaServicio {

	@Autowired
	public CuentaRepositorio cuentaRepositorio;

	@Autowired
	public BancoRepositorio bancoRepositorio;
	
	@Override
	@Transactional(readOnly=true)
	public List<Cuenta> findAll() {
		return cuentaRepositorio.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Cuenta findById(Long id) {
		return cuentaRepositorio.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Cuenta save(Cuenta cuenta) {
		return cuentaRepositorio.save(cuenta);
	}

	@Override
	@Transactional(readOnly = true)
	public int RevisarTotalDeTrandferencias(Long bancoId) {
		Banco banco = bancoRepositorio.findById(bancoId).orElseThrow();
		return banco.getTotalTransferencias();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal revisarSaldo(Long cuentaId) {
		Cuenta cuenta = cuentaRepositorio.findById(cuentaId).orElseThrow();
		return cuenta.getSaldo();
		}

	@Override
	public void TransferirDinero(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long BancoId) {
		Cuenta cuentaOrigen = cuentaRepositorio.findById(numeroCuentaOrigen).orElseThrow();
		cuentaOrigen.realizarDebito(monto);
		cuentaRepositorio.save(cuentaOrigen);
		
		Cuenta cuentaDestino = cuentaRepositorio.findById(numeroCuentaDestino).orElseThrow();
		cuentaDestino.realizarCredito(monto);
		cuentaRepositorio.save(cuentaDestino);

		Banco banco = bancoRepositorio.findById(BancoId).orElseThrow();
		int totalTransferencia = banco.getTotalTransferencias();
		banco.setTotalTransferencias(++totalTransferencia);
		bancoRepositorio.save(banco);
		
	}

}
