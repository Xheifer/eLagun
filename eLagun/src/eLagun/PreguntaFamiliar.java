package eLagun;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table (name = "PREGUNTAFAMILIAR")

public class PreguntaFamiliar {

		@Id
		@GeneratedValue
		@Column(name = "idPregunta")
		private int idPregunta;
		@Column(name = "texto")
		private String textoPregunta;
		@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
		@LazyCollection(LazyCollectionOption.FALSE)
		@JoinColumn (name = "idFamiliar")
		private Familiar familiar;
		public Familiar getFamiliar() {
			return this.familiar;
		}

		public PreguntaFamiliar(int idPregunta, String textoPregunta,  Familiar familiar) {
			this.idPregunta = idPregunta;
			this.textoPregunta = textoPregunta;
			this.familiar = familiar;
		}
		public PreguntaFamiliar(String textoPregunta, Familiar familiar) {
			this.textoPregunta = textoPregunta;
			this.familiar = familiar;
		}
		public PreguntaFamiliar(){
			
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

		public void setFamiliar(Familiar familiar) {
			this.familiar = familiar;
		}


	}

