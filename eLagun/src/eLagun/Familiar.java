package eLagun;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



@Entity
@Table (name = "FAMILIAR") 

public class Familiar {
	@Id
	@GeneratedValue (strategy = IDENTITY)
	@Column(name = "idFamiliar")
	private int idFamiliar;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_siguiente")
	private Date fechaSiguiente;
	
	@Column(name = "contra")
	private String contra;
	
	@Column(name = "user")
	private String user;
	
	@OneToOne
	@JoinColumn (name = "idPaciente")
	private Paciente paciente;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "familiar")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
        org.hibernate.annotations.CascadeType.DELETE,
        org.hibernate.annotations.CascadeType.MERGE,
        org.hibernate.annotations.CascadeType.PERSIST,
        org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Set<PreguntaFamiliar> preguntas = new HashSet<PreguntaFamiliar>(0);
	
	
	public Familiar(){
		
	}
	
	public Familiar (int idFamiliar, Date fechaSiguiente, Paciente paciente, String contra, String user) {
		this.idFamiliar = idFamiliar;
		this.fechaSiguiente = fechaSiguiente;
		this.paciente = paciente;
		this.contra = contra;
		this.user=user;
	}
	public Familiar (Date fechaSiguiente, Paciente paciente, String user) {
		this.fechaSiguiente = fechaSiguiente;
		this.paciente = paciente;
		this.user=user;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getContra() {
		return contra;
	}
	public void setContra(String contra) {
		this.contra = contra;
	}

	public int getIdFamiliar() {
		return idFamiliar;
	}

	public void setIdFamiliar(int idFamiliar) {
		this.idFamiliar = idFamiliar;
	}

	public Date getFechaSiguiente() {
		return fechaSiguiente;
	}
	public void setFechaSiguiente(Date fechaSiguiente) {
		this.fechaSiguiente = fechaSiguiente;
	}
	
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Set<PreguntaFamiliar> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Set<PreguntaFamiliar> preguntas) {
		this.preguntas = preguntas;
	}
	

}
