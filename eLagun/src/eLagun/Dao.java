package eLagun;




import java.util.ArrayList;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import org.hibernate.criterion.Restrictions;



public class Dao {
	private Session session;
	public void openSession(){
	 session = HibernateUtil.getSessionFactory().openSession();
	}
	public void closeSession(){
		
	}

	public void guardarPaciente(Paciente paciente){
		System.out.println("se entra en dao guardar paciente");
		session.beginTransaction();
		System.out.println("se ha hecho begin transaction");
		session.save(paciente);
		System.out.println("se ha guardado el paciente");
		session.getTransaction().commit();
		System.out.println("se ha hecho el commit");
		

	}
	public void borrarPaciente (int idPaciente){
		session.beginTransaction();
		Paciente paciente = session.get(Paciente.class, idPaciente);
		session.delete(paciente);
        session.getTransaction().commit();
		System.out.println("he borrado el paciente");
	}
	public void guardarFamiliar(Familiar familiar){
		System.out.println("Entro en guardar familiar");
		session.beginTransaction();
		session.save(familiar);
		session.getTransaction().commit();
	}
	
	public void borrarFamiliar (int idFamiliar){
		session.beginTransaction();
		Familiar f = session.get(Familiar.class, idFamiliar);
		session.delete(f);
        session.getTransaction().commit();
		System.out.println("he borrado el familiar");
	}
	
	//guardar medico
	public void guardarMedico(Medico medico){
		System.out.println("entro en guardar médico");
		session.beginTransaction();
		session.save(medico);
		session.getTransaction().commit();
		

	}
	//obtener paciente
	public Paciente obtenerPaciente( int idPaciente){
		session.beginTransaction();
		Paciente pacienteBD = (Paciente) session.get(Paciente.class, idPaciente);
		session.getTransaction().commit();
		
		return pacienteBD;
	}
	//comprobar paciente
public boolean autenticarPaciente (String usuario, String passwd){
		
		boolean acceso = false;
		
		System.out.println("entro en autenticar paciente");
		System.out.println(usuario);
		System.out.println(passwd);
		
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Paciente.class);
		Paciente paciente  = (Paciente) criteria.add(Restrictions.eq("user", usuario)).uniqueResult();
		
		session.getTransaction().commit();
		

		
		if(paciente.getContra().equals(passwd)){
			acceso = true;
			System.out.println("el usuario es correcto");
		}
		
		return acceso;
		
	}
	
	//obtener familiar
		public Familiar obtenerFamiliar( String dni){
			session.beginTransaction();
			Familiar familiarBD = (Familiar) session.get(Familiar.class, dni);
			session.getTransaction().commit();
			
			return familiarBD;
		}
		public Familiar obtenerFamiliarPaciente(int idPaciente){
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Familiar.class);
			criteria.createCriteria( "paciente", "p");
			Familiar familiarBD = (Familiar) criteria.add(Restrictions.eq("p.idPaciente", idPaciente)).uniqueResult();
			session.getTransaction().commit();
			return familiarBD;
		}
	//obtener medico
	public Medico obtenerMedico(String userMedico){
		session.beginTransaction();
		Medico medicoBD = (Medico) session.get(Medico.class, userMedico);
		session.getTransaction().commit();
		
		return medicoBD;
		
	}
	//obtener todos los pacientes
	
	public List<Paciente> listaPacientes(){
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Paciente.class);
		criteria.addOrder(Order.asc("idPaciente"));
		@SuppressWarnings("unchecked")
		List<Paciente> pacientes = criteria.list();
		
		session.getTransaction().commit();
		
		return  pacientes;
	}
	
	//obtener todos los familiares
	
	public List<Familiar> listaFamiliares(){
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Familiar.class);

		@SuppressWarnings("unchecked")
		List<Familiar> familiares = criteria.list();
		
		session.getTransaction().commit();
		
		return  familiares;
	}
	//guardar pregunta paciente
	public void guardarPreguntaPaciente(PreguntaPaciente pregunta){
		System.out.println("Entro en guardar pregunta");
		session.beginTransaction();
		session.save(pregunta);
		session.getTransaction().commit();
		
	}
	//guardar pregunta
	public void guardarPreguntaFamiliar(PreguntaFamiliar pregunta){
		System.out.println("Entro en guardar pregunta");
		session.beginTransaction();
		session.save(pregunta);
		session.getTransaction().commit();
		
	}
	//obtener preguntas paciente
	public List<PreguntaPaciente> obtenerPreguntasPaciente(){
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PreguntaPaciente.class);
		criteria.createCriteria( "paciente", "p");
		criteria.addOrder(Order.asc("p.idPaciente"));
		@SuppressWarnings("unchecked")
		List<PreguntaPaciente> preguntas = criteria.list();
		
		session.getTransaction().commit();
		
		return  preguntas;
	}
	

	//obtener preguntas familiar
	public List<PreguntaFamiliar> obtenerPreguntasFamiliar(){
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PreguntaFamiliar.class);
		criteria.createCriteria( "familiar", "f");
		criteria.createCriteria( "f.paciente", "p");
		criteria.addOrder(Order.asc("p.idPaciente"));
		@SuppressWarnings("unchecked")
		List<PreguntaFamiliar> preguntas = criteria.list();
		
		session.getTransaction().commit();
		
		return  preguntas;
	}
	
	//obtener pregunta por id
	public PreguntaPaciente obtenerPreguntas(int idPregunta){
		session.beginTransaction();
		PreguntaPaciente preguntaBD = (PreguntaPaciente) session.get(PreguntaPaciente.class, idPregunta);
		session.getTransaction().commit();
		
		return preguntaBD;
		
	}
	
	//obtener preguntas cic
	public List<PreguntaPaciente> obtenerPreguntasPacienteId(int idPaciente)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PreguntaPaciente.class);
		criteria.createCriteria( "paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", idPaciente));
		criteria.addOrder(Order.asc("idPregunta"));
		@SuppressWarnings("unchecked")
		List<PreguntaPaciente> preguntas = criteria.list();
		
		session.getTransaction().commit();
		
		return  preguntas;
	}
	//obtener preguntas dni
	public List<PreguntaFamiliar> obtenerPreguntasFamiliarId(int idPaciente)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(PreguntaFamiliar.class);
		criteria.createCriteria( "familiar", "f");
		criteria.createCriteria( "f.paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", idPaciente));
		criteria.addOrder(Order.asc("idPregunta"));
		@SuppressWarnings("unchecked")
		List<PreguntaFamiliar> preguntas = criteria.list();
		
		session.getTransaction().commit();
		
		return  preguntas;
	}
	//obtener resultados por cic
	public List<Resultado> obtenerResultadosPaciente(int id)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Resultado.class);
		criteria.createCriteria( "paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", id));
		criteria.add(Restrictions.eq("tipo", "paciente"));
		criteria.addOrder(Order.desc("fecha"));
		@SuppressWarnings("unchecked")
		List<Resultado> resultados = criteria.list();
		
		session.getTransaction().commit();
		
		return  resultados;
	}
	public List<Resultado> resultadosGraficoPaciente(int id)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Resultado.class);
		criteria.createCriteria( "paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", id));
		criteria.add(Restrictions.eq("tipo", "paciente"));
		criteria.addOrder(Order.asc("fecha"));
		@SuppressWarnings("unchecked")
		List<Resultado> resultados = criteria.list();
		
		session.getTransaction().commit();
		
		return  resultados;
	}
	//obtener resultaos por dni
	public List<Resultado> obtenerResultadosFamiliar(int id)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Resultado.class);
		criteria.createCriteria( "paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", id));
		criteria.add(Restrictions.eq("tipo", "familiar"));
		criteria.addOrder(Order.desc("fecha"));
		@SuppressWarnings("unchecked")
		List<Resultado> resultados = criteria.list();
		
		session.getTransaction().commit();
		
		return  resultados;
	}
	public List<Resultado> resultadosGraficoFamiliar(int id)
	{
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Resultado.class);
		criteria.createCriteria( "paciente", "p");
		criteria.add(Restrictions.eq("p.idPaciente", id));
		criteria.add(Restrictions.eq("tipo", "familiar"));
		criteria.addOrder(Order.asc("fecha"));
		@SuppressWarnings("unchecked")
		List<Resultado> resultados = criteria.list();
		
		session.getTransaction().commit();
		
		return  resultados;
	}
	
	public Resultado obtenerResultado(int idResultado){
		session.beginTransaction();
		Resultado resulBD = (Resultado) session.get(Resultado.class, idResultado);
		session.getTransaction().commit();
		
		return resulBD;
		
	}
	//eliminar pregunta
	public void borrarPreguntaPaciente(int idPregunta, int idPaciente){
		Paciente p = session.get(Paciente.class, idPaciente);
		session.beginTransaction();
		
		List<PreguntaPaciente> preguntas = new ArrayList<PreguntaPaciente>();
		for(PreguntaPaciente pregunta : p.getPreguntas()) {
			preguntas.add(pregunta);
		}
		for (PreguntaPaciente pregunta : preguntas) {
			if(pregunta.getIdPregunta()==idPregunta){
			 pregunta.setPaciente(null);
			 p.removePregunta(pregunta);
			 session.save(p);
			 session.save(pregunta);
			 session.getTransaction().commit();
			session.delete(pregunta);
			}
		}
		
		//session.getTransaction().commit();
		
	}
	public void borrarPreguntaFamiliar(int idPregunta){
		session.beginTransaction();
		PreguntaFamiliar preguntaBD = (PreguntaFamiliar) session.get(PreguntaFamiliar.class, idPregunta);
		session.delete(preguntaBD);
		session.getTransaction().commit();
		
	}
	//guardar resultado
	//obtener resultado

}
