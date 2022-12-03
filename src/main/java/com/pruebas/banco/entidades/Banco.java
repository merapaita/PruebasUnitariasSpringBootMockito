package com.pruebas.banco.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "banco")
public class Banco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String nombre;
	
	@Column(name = "totalTranferencias")
	private int totalTransferencias;

	public Banco() {
	}
	
	public Banco(Long id, String nombre, int totalTransferencias) {
		super();
		Id = id;
		this.nombre = nombre;
		this.totalTransferencias = totalTransferencias;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTotalTransferencias() {
		return totalTransferencias;
	}

	public void setTotalTransferencias(int totalTransferencias) {
		this.totalTransferencias = totalTransferencias;
	}

	@Override
	public String toString() {
		return "Banco [Id=" + Id + ", nombre=" + nombre + ", totalTransferencias=" + totalTransferencias + "]";
	}
	
	// Test de Controladores en Spring Boot con Mockito y JUnit usando MockMvc (WebMvcTest)
	
}
