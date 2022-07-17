package it.banca.data;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity
public class ContoCorrente implements Serializable {

	private int numeroConto;
	private String intestatario;
	private float saldo;
	private int pin;
	
	/****************************	COSTRUTTORE		***************************************/
	public ContoCorrente() {}

	public ContoCorrente(int numeroConto, String intestatario, float saldo, int pin) {
		this.numeroConto = numeroConto;
		this.intestatario = intestatario;
		this.saldo = saldo;
		this.pin = pin;
		
	}
	
	public ContoCorrente(int numeroConto, int pin){
		this.numeroConto=numeroConto;
		this.pin= pin;
	}

	/****************************	GETTERS E SETTERS		***************************************/
	
	@Id
	public int getNumeroConto() {
		return numeroConto;
	}

	public String getIntestatario() {
		return intestatario;
	}

	public float getSaldo() {
		return saldo;
	}

	public int getPin() {
		return pin;
	}

	public void setNumeroConto(int numeroConto) {
		this.numeroConto = numeroConto;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}
	
	

}
