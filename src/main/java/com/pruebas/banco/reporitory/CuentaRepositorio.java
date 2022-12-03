package com.pruebas.banco.reporitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pruebas.banco.entidades.Cuenta;

public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

	@Query("select c from Cuenta c where c.persona=?1")
	Optional<Cuenta> findbyPersona(String persona);
}
