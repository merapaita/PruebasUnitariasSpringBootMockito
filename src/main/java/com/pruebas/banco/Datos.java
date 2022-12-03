package com.pruebas.banco;

import java.math.BigDecimal;
import java.util.Optional;

import com.pruebas.banco.entidades.Banco;
import com.pruebas.banco.entidades.Cuenta;

public class Datos {
	public static Optional<Cuenta> crearCuenta001() {
		return Optional.of(new Cuenta(1L, "christian", new BigDecimal(1000)));
	}
	public static Optional<Cuenta> crearCuenta002() {
		return Optional.of(new Cuenta(2L, "Juan", new BigDecimal(2000)));
	}
	public static Optional<Banco> crearBanco() {
		return Optional.of(new Banco(1L, "Banco Financiero", 0));
	}
}
