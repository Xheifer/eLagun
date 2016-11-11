package eLagun;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;


@Entity
@Table (name = "respuesta")

public class Respuesta implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "idRespuesta")
	private int idRespuesta;
	@Column(name = "texto")
	private String textoRespuesta;
	@Column(name = "puntuacion")
	private int puntuacion;
	
	public Respuesta(){
		
	}
	public Respuesta(int idRespuesta, String textoRespuesta, int puntuacion ) {
		this.idRespuesta = idRespuesta;
		this.textoRespuesta = textoRespuesta;
		this.puntuacion = puntuacion;
	}
	
	public int getIdRespuesta() {
		return idRespuesta;
	}
	public void setIdRespuesta(int idRespuesta) {
		this.idRespuesta = idRespuesta;
	}
	public String getTextoRespuesta() {
		return textoRespuesta;
	}
	public void setTextoRespuesta(String textoRespuesta) {
		this.textoRespuesta = textoRespuesta;
	}
	public int getPuntuacion() {
		return puntuacion;
	}
	public void setPuntuacion(int puntuacion) {
		this.puntuacion = puntuacion;
	}

	

}
