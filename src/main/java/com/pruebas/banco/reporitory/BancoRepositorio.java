package com.pruebas.banco.reporitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.banco.entidades.Banco;

public interface BancoRepositorio extends JpaRepository<Banco, Long> {

}
