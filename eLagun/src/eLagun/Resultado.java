package eLagun;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table (name = "resultado")

public class Resultado implements Serializable{
	
	@Id
	@GeneratedValue
	private int idResultado;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn (name = "idPaciente")
	private Paciente paciente;
	public Paciente getPaciente() {
		return paciente;
	}
	
	private int valor;
	private String alarma;
	private double porcentaje;
	private String tipo;
	
	public Resultado(int idResultado, Date fecha,int valor, String alarma, Paciente paciente) {
		this.idResultado = idResultado;
		this.fecha = fecha;
		this.valor = valor;
		this.alarma = alarma;
		this.paciente = paciente;

	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Resultado(){
		
	}

	public int getIdResultado() {
		return idResultado;
	}
	public void setIdResultado(int idResultado) {
		this.idResultado = idResultado;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public int getValor() {
		return valor;
	}
	public void setResultado(int valor) {
		this.valor = valor;
	}
	public String getAlarma() {
		return alarma;
	}
	public void setAlarma(String alarma) {
		this.alarma = alarma;
	}
	public double getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(double porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
