package eLagun;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.SelectBeforeUpdate;




@Entity
@Table (name = "paciente") 
@DynamicUpdate(value=true)
@SelectBeforeUpdate(value=true)

public class Paciente {
	@Id
	@Column(name = "idPaciente")
	private int idPaciente;

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_siguiente")
	private Date fechaSiguiente;
	
	@Column(name = "tests_saltados")
	private int testsSaltados;
	
	@OneToOne (mappedBy= "paciente")  
    @JoinColumn(name="idFamiliar")  
	private Familiar familiar;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "paciente")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
        org.hibernate.annotations.CascadeType.DELETE,
        org.hibernate.annotations.CascadeType.MERGE,
        org.hibernate.annotations.CascadeType.PERSIST})
	private Set<Resultado> resultados = new HashSet<Resultado>(0);
	
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "paciente")
	@LazyCollection(LazyCollectionOption.FALSE)
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE,
        org.hibernate.annotations.CascadeType.DELETE,
        org.hibernate.annotations.CascadeType.MERGE,
        org.hibernate.annotations.CascadeType.PERSIST,
        org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Set<PreguntaPaciente> preguntas = new HashSet<PreguntaPaciente>(0);
	
	@Column(name = "contra")
	private String contra;
	
	@Column(name = "user")
	private String user;
	
	public Paciente (int idPaciente, Date fechaSiguiente, int testsSaltados, String contra, String user) {
		this.idPaciente = idPaciente;
		this.fechaSiguiente = fechaSiguiente;
		this.testsSaltados= testsSaltados;
		this.contra=contra;
		this.user=user;
	}

	public Paciente(){
		
	}
	public Paciente (int idPaciente, Date fechaSiguiente, String user) {
		this.idPaciente = idPaciente;
		this.fechaSiguiente = fechaSiguiente;
		this.contra=contra;
		this.user=user;
	}
	

	public int getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Familiar getFamiliar() {
		return familiar;
	}

	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}

	public Set<Resultado> getResultados() {
		return resultados;
	}

	public void setResultados(Set<Resultado> resultados) {
		this.resultados = resultados;
	}

	public Set<PreguntaPaciente> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Set<PreguntaPaciente> preguntas) {
		this.preguntas = preguntas;
	}

	public Date getFechaSiguiente() {
		return fechaSiguiente;
	}
	public void setFechaSiguiente(Date fechaSiguiente) {
		this.fechaSiguiente = fechaSiguiente;
	}
	public int getTestsSaltados() {
		return testsSaltados;
	}
	public void setTestsSaltados(int testsSaltados) {
		this.testsSaltados = testsSaltados;
	}
	public String getContra() {
		return contra;
	}
	public void setContra(String contra) {
		this.contra = contra;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	public void removePregunta (PreguntaPaciente p){
		preguntas.remove(p);
		
	}
	
}
