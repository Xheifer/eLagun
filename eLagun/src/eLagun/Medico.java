package eLagun;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "medico") 

public class Medico {
	@Id
	@Column (name= "usuario")
	private String userMedico;
	
	@Column (name = "contra")
	private String contraMedico;
	
	@Column (name = "mail")
	private String mail;
	
	public Medico(){
		
	}

	public Medico(String userMedico, String contraMedico, String mail) {
		this.userMedico = userMedico;
		this.contraMedico = contraMedico;
		this.mail = mail;
	}
	public Medico(String userMedico, String contraMedico) {
		this.userMedico = userMedico;
		this.contraMedico = contraMedico;

	}

	public String getUserMedico() {
		return userMedico;
	}

	public void setUserMedico(String userMedico) {
		this.userMedico = userMedico;
	}

	public String getContraMedico() {
		return contraMedico;
	}

	public void setContraMedico(String contraMedico) {
		this.contraMedico = contraMedico;
	}
	
	

}
