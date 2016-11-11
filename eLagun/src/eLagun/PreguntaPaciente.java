package eLagun;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.GeneratedValue;

@Entity
@Table (name = "PREGUNTAPACIENTE")

public class PreguntaPaciente implements Serializable{
	

	@Id
	@GeneratedValue
	@Column(name = "idPregunta")
	private int idPregunta;
	@Column(name = "texto")
	private String textoPregunta;
	@ManyToOne(fetch = FetchType.LAZY)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinColumn (name = "idPaciente")
	private Paciente paciente;
	public Paciente getPaciente() {
		return paciente;
	}

	public PreguntaPaciente(int idPregunta, String textoPregunta, Paciente paciente) {
		this.idPregunta = idPregunta;
		this.textoPregunta = textoPregunta;
		this.paciente = paciente;
	}
	public PreguntaPaciente(String textoPregunta, Paciente paciente) {
		this.textoPregunta = textoPregunta;
		this.paciente = paciente;
	}
	public PreguntaPaciente(){
		
	}
	
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	
}

