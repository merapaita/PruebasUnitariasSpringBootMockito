package com.pruebas.banco.entidades;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import com.pruebas.banco.exepciones.DineroInsuficienteException;

import javax.persistence.Id;

@Entity
@Table(name = "cuentas")
public class Cuenta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String persona;
	private BigDecimal saldo;
	
	public Cuenta() {
	}
	
	public Cuenta(Long id, String persona, BigDecimal saldo) {
		super();
		Id = id;
		this.persona = persona;
		this.saldo = saldo;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPersona() {
		return persona;
	}

	public void setPersona(String persona) {
		this.persona = persona;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public void realizarDebito(BigDecimal monto) {
		BigDecimal nuevoSaldo = this.saldo.subtract(monto);
		
		if (nuevoSaldo.compareTo(BigDecimal.ZERO)<0) {
			throw new DineroInsuficienteException("Dinero Insuficinete en la Cuenta");
		}
		this.saldo = nuevoSaldo;
	}
	
	public void realizarCredito(BigDecimal monto) {
		this.saldo = saldo.add(monto);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
		result = prime * result + ((persona == null) ? 0 : persona.hashCode());
		result = prime * result + ((saldo == null) ? 0 : saldo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cuenta other = (Cuenta) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (persona == null) {
			if (other.persona != null)
				return false;
		} else if (!persona.equals(other.persona))
			return false;
		if (saldo == null) {
			if (other.saldo != null)
				return false;
		} else if (!saldo.equals(other.saldo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cuenta [Id=" + Id + ", persona=" + persona + ", saldo=" + saldo + "]";
	}

}
