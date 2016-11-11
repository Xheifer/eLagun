package eLagun;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

@ManagedBean (name="userBean")
@SessionScoped


public class UserBean implements Serializable{
	private static final long serialVersionUID = 1L;

	private int idFamiliar;
	private Date fechaSiguiente;
	private int idPaciente;
	private int testsSaltados;
	private String tipo;
	private Familiar familiar;
	private Paciente paciente;
	private Paciente pacienteBD;
	private String contraMedico;
	private String userMedico;
	private Paciente pacienteSeleccionado;
	private Familiar familiarSeleccionado;
	private PreguntaPaciente preguntaPacienteSeleccionada;
	private PreguntaFamiliar preguntaFamiliarSeleccionada;
	
	private String userName;
	private Medico medico;
	private String mailMedico;
	private String pagAnterior;
	private PreguntaPaciente preguntaPaciente;
	private PreguntaFamiliar preguntaFamiliar;
	private String textoPregunta;
	private int idPregunta;
	private List<Resultado> resultados;
	private List<Paciente> pacientes;
	private List<Familiar> familiares;
	private Resultado resultado;
	private Date fecha;
	private int valor;
	private List<PreguntaPaciente> preguntasPaciente;
	private List<PreguntaFamiliar> preguntasFamiliar;
	
	private LineChartModel model;

	
	
	//funciones de usuarios
	public String registro(){
		
		/*pagAnterior.setNombre("RegistroUsuario");
		
		System.out.println(pagAnterior.getNombre());*/
		String respuesta;
		//calculamos cuándo será el siguiente test
		DateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		int noOfDays = 14; //el test es cada dos semanas
		Calendar calendar = Calendar.getInstance(); //esta es la fecha de hoy
		//System.out.println(formato.format(calendar.getTime()));            
		calendar.add(Calendar.DAY_OF_YEAR, noOfDays); //y esta es dentro de dos semanas
		String fecha = formato.format(calendar.getTime());
		//System.out.println(fecha);
		try {
			fechaSiguiente = formato.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//según si es paciente o familiar llamamos al método
		if (tipo.equals("paciente")){
			respuesta=registrarPaciente(fechaSiguiente);
		}else{
			respuesta=registrarFamiliar(fechaSiguiente);
		}
		FacesContext fc = FacesContext.getCurrentInstance();
		this.pagAnterior = getPagina(fc);
		System.out.println(pagAnterior);
		this.userName="";
		this.tipo="";
		
	return respuesta;
	}
	public String registrarPaciente(Date fechaSiguiente){
		
		System.out.println("Se va a guardar un paciente");
		if(idPaciente == 0 || userName.length() == 0 || tipo.length() == 0){
			
			return "errorVacio?faces-redirect=true";
		}
		paciente = new Paciente(idPaciente, fechaSiguiente, userName);
		System.out.println("se va a crear dao");
		Dao dao = new Dao();
		dao.openSession();
		System.out.println("se va a guardar el paciente en dao");
		dao.guardarPaciente(paciente);
		dao.closeSession();
		return "successUsuario?faces-redirect=true";
	}
	public String registrarFamiliar(Date fechaSiguiente){
		System.out.println("se va a guardar un familiar");
		
		if(idPaciente == 0 || userName.length() == 0 || tipo.length() == 0){
			//request.setAttribute(“pagAnterior”,"registroUsuario");
			//request.getRequestDispacher(“/errorVacio.jsp”).forward(request,response);
			return "errorVacio?faces-redirect=true";
		}
		else{
			Dao dao= new Dao();
			dao.openSession();
			paciente=dao.obtenerPaciente(idPaciente);
			if (paciente == null){
				System.out.println("No existe el paciente");
				return "errorCic?faces-redirect=true";
			}

			else{
				familiar = new Familiar(fechaSiguiente, paciente, userName);
				dao.guardarFamiliar(familiar);
				dao.closeSession();
				return "successUsuario?faces-redirect=true";
			}
			
		}
		
	}
	//borrar usuarios
	public String borrarPaciente(){
		Dao dao = new Dao();
		dao.openSession();
		System.out.println("he entrado en borrar paciente y el paciente seleccionado es "+ pacienteSeleccionado.getIdPaciente());
		dao.borrarPaciente(pacienteSeleccionado.getIdPaciente());
		dao.closeSession();
		return "successBorrar?faces-redirect=true";
	}
	public String borrarFamiliar(){
		Dao dao = new Dao();
		dao.openSession();
		System.out.println("he entrado en borrar familiar y el paciente seleccionado es "+ familiarSeleccionado.getIdFamiliar());
		dao.borrarFamiliar(familiarSeleccionado.getIdFamiliar());
		dao.closeSession();
		return "successBorrar?faces-redirect=true";
	}

	//funciones de médico
	public String registrarMedico(){
		System.out.println("se va a guardar un medico");
			//pagAnterior.setNombre("registroMedico");
		if(userMedico.length()==0 || contraMedico.length()==0 ){
			FacesContext fc = FacesContext.getCurrentInstance();
			this.pagAnterior = getPagina(fc);
			System.out.println(pagAnterior);
			return "errorVacio?faces-redirect=true";
		}
		else{
			Dao dao= new Dao();
			dao.openSession();
			medico = new Medico(userMedico, contraMedico);
			dao.guardarMedico(medico);
			dao.closeSession();
			this.userMedico="";
			this.contraMedico="";
			return "home?faces-redirect=true";
		}
	}

	public String comprobarMedico(){
		//pagAnterior.setNombre("LoginMedico");
		System.out.println("se va a comprobar un medico");
	
		if(userMedico.length()==0 || contraMedico.length()==0){
			FacesContext fc = FacesContext.getCurrentInstance();
			this.pagAnterior = getPagina(fc);
			System.out.println(pagAnterior);
			return "errorVacio?faces-redirect=true";
		}
		else{
			Dao dao= new Dao();
			dao.openSession();
			medico=dao.obtenerMedico(userMedico);
			dao.closeSession();
			if(medico == null || !(medico.getContraMedico().equals(contraMedico)))
			{
				return "errorLogin?faces-redirect=true";
			
			}
			else{
				return "home?faces-redirect=true";
			}
		}
		
	}
	//funciones de preguntas
	public String registrarPregunta(){
		System.out.println("se va a guardar una pregunta");
		//si alguno de los campos esta vacio
		if((textoPregunta.equals("")) || idPaciente == 0 || tipo.equals("")){
			return "errorVacio?faces-redirect=true";
		}
		//si está todo bien
		else{
			Dao dao= new Dao();
			dao.openSession();
			//si la regunta es para un paciente
			paciente=dao.obtenerPaciente(idPaciente);
			if(paciente == null)
			{
				return "noEncontrado?faces-redirect=true";
			
			}
			else{
				if(tipo.equals("paciente")){
					preguntaPaciente = new PreguntaPaciente(textoPregunta, paciente);
					dao.guardarPreguntaPaciente(preguntaPaciente);
					dao.closeSession();
					return "successPregunta?faces-redirect=true";
				}
				else{
					//obtenemos el familiar de ese paciente
					familiar= dao.obtenerFamiliarPaciente(paciente.getIdPaciente());
					preguntaFamiliar = new PreguntaFamiliar(textoPregunta, familiar);
					dao.guardarPreguntaFamiliar(preguntaFamiliar);
					dao.closeSession();
					return "successPregunta?faces-redirect=true";
				}
			}
		}		
	}
	
	//lista completa de pacientes
	public List<Paciente> listaPacientes(){
		System.out.println("entro a lista pacientes");
		Dao dao= new Dao();
		dao.openSession();

				//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
				this.pacientes= dao.listaPacientes();
				System.out.println("me han devuleto los pacientes");
				for (Iterator<Paciente> iterator = pacientes.iterator(); iterator.hasNext();){
					paciente = iterator.next(); 
					System.out.print("ID: " + paciente.getIdPaciente()); 
					System.out.print("FechaSiguiente: " + paciente.getFechaSiguiente()); 
					System.out.println("Tests saltados: " + paciente.getTestsSaltados()); 
					System.out.println("Usuario: " + paciente.getUser()); 
				}

		dao.closeSession();
		System.out.println("devuelvo los resultados a la página");
		return pacientes;		
	}
	
//	lista completa de familiares
	
	public List<Familiar> listaFamiliares(){
		System.out.println("entro a lista familiares");
		Dao dao= new Dao();
		dao.openSession();

				//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
				this.familiares= dao.listaFamiliares();
				System.out.println("me han devuleto los familiares");
				for (Iterator<Familiar> iterator = familiares.iterator(); iterator.hasNext();){
					familiar = iterator.next(); 
					System.out.print("ID: " + familiar.getIdFamiliar()); 
					System.out.print("FechaSiguiente: " + familiar.getFechaSiguiente()); 
					System.out.println("Usuario: " + familiar.getUser()); 
				}

		dao.closeSession();
		System.out.println("devuelvo los resultados a la página");
		return familiares;		
	}
	
	
	// funciones de resultados
	public List<Resultado> consultarResultadosPaciente(){
		System.out.println("entro en consultar resultados del paciente");
		Dao dao= new Dao();
		dao.openSession();
		System.out.println("idPaciente es "+ idPaciente );
		//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
		this.resultados= dao.obtenerResultadosPaciente(idPaciente);
		System.out.println("me han devuleto los resultados");
		for (Iterator<Resultado> iterator = resultados.iterator(); iterator.hasNext();){
			
			resultado = iterator.next(); 
			System.out.print("Fecha: " + resultado.getFecha()); 
			System.out.print("  Resultado: " + resultado.getValor()); 
			System.out.println("  Porcentaje: " + resultado.getPorcentaje()); 
			System.out.println("  Alarma: " + resultado.getAlarma());

			}

		dao.closeSession();
		//en realidad devolver los resultados que metemos en una tabla en una ventana pop up
		System.out.println("devuelvo los resultados a la página");
		return resultados;
	}
	public List<Resultado> consultarResultadosFamiliar(){
		System.out.println("entro en consultar resultados del paciente");
		Dao dao= new Dao();
		dao.openSession();
		System.out.println("idPaciente es "+ idPaciente );
		//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
		this.resultados= dao.obtenerResultadosFamiliar(idPaciente);
		System.out.println("me han devuleto los resultados");
		for (Iterator<Resultado> iterator = resultados.iterator(); iterator.hasNext();){
			
			resultado = iterator.next(); 
			System.out.print("Fecha: " + resultado.getFecha()); 
			System.out.print("  Resultado: " + resultado.getValor()); 
			System.out.println("  Porcentaje: " + resultado.getPorcentaje()); 
			System.out.println("  Alarma: " + resultado.getAlarma());

			}

		dao.closeSession();
		//en realidad devolver los resultados que metemos en una tabla en una ventana pop up
		System.out.println("devuelvo los resultados a la página");
		return resultados;
	}
public LineChartModel graficoResultados(){
	model = new LineChartModel();
	ChartSeries pacientes = new ChartSeries();
	pacientes.setLabel("Pacientes");
	Dao dao= new Dao();
	dao.openSession();
	int i=1;
	this.resultados= dao.resultadosGraficoPaciente(idPaciente);
	for (Iterator<Resultado> iterator = resultados.iterator(); iterator.hasNext();){
		
		resultado = iterator.next(); 
		pacientes.set(i, resultado.getValor());
		System.out.print("Fecha: " + resultado.getFecha()); 
		System.out.print("  Resultado: " + resultado.getValor()); 
		i++;
	}
	dao.closeSession();
	ChartSeries familiares = new ChartSeries();
	familiares.setLabel("Familiares");
	dao.openSession();
	System.out.println("idPaciente es "+ idPaciente );
		//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
	this.resultados= dao.resultadosGraficoFamiliar(idPaciente);
	int j=1;
	for (Iterator<Resultado> iterator = resultados.iterator(); iterator.hasNext();){
			
		resultado = iterator.next(); 
		familiares.set(j, resultado.getValor());
		System.out.print("Fecha: " + resultado.getFecha()); 
		System.out.print("  Resultado: " + resultado.getValor()); 
		System.out.println("  Porcentaje: " + resultado.getPorcentaje()); 
		j++;
	}

	dao.closeSession();
	model.addSeries(pacientes);
	model.addSeries(familiares);
	model.setTitle("Gráfico de resultados");
	model.setLegendPosition("e");
	model.setShowPointLabels(true);
	Axis yAxis = model.getAxis(AxisType.Y);
	yAxis.setLabel("Resultados");
	yAxis.setMin(0);
	yAxis.setMax(40);
	model.getAxes().put(AxisType.X, new CategoryAxis("Número de test"));
	return model;
}
	//funciones preguntas

public List<PreguntaPaciente> consultarPreguntasPaciente(){
	System.out.println("entro en consultar preguntas de los pacientes");
	Dao dao= new Dao();
	dao.openSession();
	this.preguntasPaciente = dao.obtenerPreguntasPaciente();
	for (Iterator<PreguntaPaciente> iterator = preguntasPaciente.iterator(); iterator.hasNext();){
		
		preguntaPaciente = iterator.next(); 
		System.out.print("ID: " + preguntaPaciente.getIdPregunta()); 
		System.out.print(" Texto: " + preguntaPaciente.getTextoPregunta()); 
		 
	}
	dao.closeSession();
	System.out.println("devuelvo los resultados a la página");	
	return preguntasPaciente;
}
public List<PreguntaFamiliar> consultarPreguntasFamiliar(){
	System.out.println("entro en consultar preguntas de los famaliares");
	Dao dao= new Dao();
	dao.openSession();
	this.preguntasFamiliar = dao.obtenerPreguntasFamiliar();
	for (Iterator<PreguntaFamiliar> iterator = preguntasFamiliar.iterator(); iterator.hasNext();){
		
		preguntaFamiliar = iterator.next(); 
		System.out.print("ID: " + preguntaFamiliar.getIdPregunta()); 
		System.out.print(" Texto: " + preguntaFamiliar.getTextoPregunta()); 
		 
	}
	dao.closeSession();
	System.out.println("devuelvo los resultados a la página");
	return preguntasFamiliar;
}

public List<PreguntaPaciente> consultarPreguntasPacienteId(){
	System.out.println("entro en consultar preguntas de un paciente");
	Dao dao= new Dao();
	dao.openSession();
	this.preguntasPaciente = dao.obtenerPreguntasPacienteId(idPaciente);
	for (Iterator<PreguntaPaciente> iterator = preguntasPaciente.iterator(); iterator.hasNext();){
		
		preguntaPaciente = iterator.next(); 
		System.out.print("ID: " + preguntaPaciente.getIdPregunta()); 
		System.out.print(" Texto: " + preguntaPaciente.getTextoPregunta()); 
		 
	}
	dao.closeSession();
	System.out.println("devuelvo los resultados a la página");	
	return preguntasPaciente;
}
public List<PreguntaFamiliar> consultarPreguntasFamiliarId(){
	System.out.println("entro en consultar preguntas de un familiar");
	Dao dao= new Dao();
	dao.openSession();
	this.preguntasFamiliar = dao.obtenerPreguntasFamiliarId(idPaciente);
	for (Iterator<PreguntaFamiliar> iterator = preguntasFamiliar.iterator(); iterator.hasNext();){
		
		preguntaFamiliar = iterator.next(); 
		System.out.print("ID: " + preguntaFamiliar.getIdPregunta()); 
		System.out.print(" Texto: " + preguntaFamiliar.getTextoPregunta()); 
		 
	}
	dao.closeSession();
	System.out.println("devuelvo los resultados a la página");
	return preguntasFamiliar;
}

/*	public List<PreguntaPaciente> consultarPreguntasPaciente(){
		System.out.println("entro en consultar preguntas de los pacientes");
		System.out.println("tipo de usuario"+ tipo);
		Dao dao= new Dao();
		dao.openSession();
		//si ambos están vacios devolvemos todas las preguntas
		if((idFamiliar== null && idPaciente==null)){
			System.out.println("Quiere todas las preguntas");
			//miramos de qué tipo de usuario quiere las preguntas
			if(tipo.equals("paciente")){
				System.out.println("quiere todas las de paciente");
				this.preguntas= dao.obtenerPreguntasPaciente();
				System.out.println("me han devuleto las reguntas");
				for (Iterator<PreguntaPaciente> iterator = preguntas.iterator(); iterator.hasNext();){
					
					pregunta = iterator.next(); 
					System.out.print("ID: " + pregunta.getIdPregunta()); 
					System.out.print(" Texto: " + pregunta.getTextoPregunta()); 
					 
				}
			}
			else{
				System.out.println("Quiere todas las de familiar");
				this.preguntas= dao.obtenerPreguntasFamiliar();
				System.out.println("me han devuleto las reguntas");
				for (Iterator<PreguntaPaciente> iterator = preguntas.iterator(); iterator.hasNext();){
					
					pregunta = iterator.next(); 
					System.out.print("ID: " + pregunta.getIdPregunta()); 
					System.out.print(" Texto: " + pregunta.getTextoPregunta()); 
					 
				}
			}
		}
		else{
			if(!idFamiliar.equals("")){
				System.out.println("tengo idFamiliar "+ idFamiliar );
				//obtener todos los resultados con ese idFamiliar y ponerlos en una lista
				this.preguntas= dao.obtenerPreguntasDni(idFamiliar);
				System.out.println("me han devuleto las reguntas");
				for (Iterator<PreguntaPaciente> iterator = preguntas.iterator(); iterator.hasNext();){
					
					pregunta = iterator.next(); 
					System.out.print("ID: " + pregunta.getIdPregunta()); 
					System.out.print(" Texto: " + pregunta.getTextoPregunta()); 
					 
				}
			}
			else{
				System.out.println("tengo idPaciente");
				//obtener todos los resultados con ese idPaciente y ponerlos en una lista
				this.preguntas= dao.obtenerPreguntasCic(idPaciente);
				System.out.println("me han devuleto los resultados");
				for (Iterator<PreguntaPaciente> iterator = preguntas.iterator(); iterator.hasNext();){
					

					pregunta = iterator.next(); 
					System.out.print("ID: " + pregunta.getIdPregunta()); 
					System.out.print(" Texto: " + pregunta.getTextoPregunta()); 
				}
			}
		}
		//en realidad devolver los resultados que metemos en una tabla en una ventana pop up
		dao.closeSession();
		System.out.println("devuelvo los resultados a la página");
		return preguntas;
	
	}
	*/

	public String borrarPreguntaPaciente(){
		System.out.println("entramos para eliminar la pregunta de un paciente");
		System.out.println("Pregunta a eliminar"+idPregunta);
		Dao dao= new Dao();
		dao.openSession();
		dao.borrarPreguntaPaciente(preguntaPacienteSeleccionada.getIdPregunta(), preguntaPacienteSeleccionada.getPaciente().getIdPaciente());
		dao.closeSession();
		System.out.println("se ha eliminado la pregunta");
		return "successBorrarPregunta?faces-redirect=true";
		
	}
	public String redirigirPreguntaPaciente(){
		System.out.println("entramos para redirigir a las pregunta de un paciente");
		System.out.println(" el codigo del paciente es "+ preguntaPacienteSeleccionada.getPaciente().getIdPaciente());
		idPaciente= preguntaPacienteSeleccionada.getPaciente().getIdPaciente();
		System.out.println("tengo las preguntas");
		return "preguntasPaciente?faces-redirect=true";
		
	}
	public String redirigirPaciente(){
		System.out.println("entramos para redirigir a las pregunta de un paciente");
		System.out.println(" el codigo del paciente es "+ pacienteSeleccionado.getIdPaciente());
		idPaciente= pacienteSeleccionado.getIdPaciente();
		System.out.println("tengo las preguntas");
		return "preguntasPaciente?faces-redirect=true";
		
	}
	public String borrarPreguntaFamiliar(){
		System.out.println("entramos para eliminar la pregunta de un familiar");
		System.out.println("Pregunta a eliminar"+idPregunta);
		Dao dao= new Dao();
		dao.openSession();
		dao.borrarPreguntaFamiliar(preguntaFamiliarSeleccionada.getIdPregunta());
		dao.closeSession();
		System.out.println("se ha eliminado la pregunta");
		return "successBorrarPregunta?faces-redirect=true";
		
	}
	public String redirigirPreguntaFamiliar(){
		System.out.println("entramos para redirigir a las pregunta de un paciente");
		System.out.println(" el codigo del paciente es "+ preguntaFamiliarSeleccionada.getFamiliar().getPaciente().getIdPaciente());
		idPaciente= preguntaFamiliarSeleccionada.getFamiliar().getPaciente().getIdPaciente();
		System.out.println("tengo las preguntas");
		return "preguntasPaciente?faces-redirect=true";
		
	}
	public String redirigirFamiliar(){
		System.out.println("entramos para redirigir a las pregunta de un paciente");
		System.out.println(" el codigo del paciente es "+ familiarSeleccionado.getPaciente().getIdPaciente());
		idPaciente= familiarSeleccionado.getPaciente().getIdPaciente();
		System.out.println("tengo las preguntas");
		return "preguntasPaciente?faces-redirect=true";
		
	}
	//otras funciones
	public String redirigirResultados(){
		if(idPaciente != 0){
			System.out.println("ha metido idPaciente");
			return "tablaResultados?faces-redirect=true";
		}
		else{
			return "errorVacio?faces-redirect=true";
		}
	}
	
	
	public String redirigirPreguntas(){

		return "preguntasPaciente?faces-redirect=true";
		
	}
	public String getPagina(FacesContext fc){
		System.out.println("entro en get pagina");
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		pagAnterior = params.get("pagAnterior");
		System.out.println(pagAnterior);
		return pagAnterior;
	}
	public List<Resultado> getResultados() {
		
		return resultados;
	}
	public String irAtras(){
		return pagAnterior+"?faces-redirect=true";
	}

	public List<PreguntaPaciente> getPreguntasPaciente() {
		return preguntasPaciente;
	}
	public void setPreguntasPaciente(List<PreguntaPaciente> preguntasPaciente) {
		this.preguntasPaciente = preguntasPaciente;
	}
	public List<PreguntaFamiliar> getPreguntasFamiliar() {
		return preguntasFamiliar;
	}
	public void setPreguntasFamiliar(List<PreguntaFamiliar> preguntasFamiliar) {
		this.preguntasFamiliar = preguntasFamiliar;
	}
	public LineChartModel getModel() {
		return model;
	}
	public void setModel(LineChartModel model) {
		this.model = model;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	public Date getFecha() {
		return resultado.getFecha();
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public int getValor() {
		return resultado.getValor();
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	public Paciente getPacienteBD() {
		return pacienteBD;
	}
	public void setPacienteBD(Paciente pacienteBD) {
		this.pacienteBD = pacienteBD;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Familiar getFamiliar() {
		return familiar;
	}
	public void setFamiliar(Familiar familiar) {
		this.familiar = familiar;
	}
	public Paciente getPaciente() {
		return paciente;
	}
	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
	public int getIdFamiliar() {
		idFamiliar=0;
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
	public int getIdPaciente() {

		return idPaciente;
	}
	public void setIdPaciente(int idPaciente) {
		this.idPaciente = idPaciente;
	}
	public int getTestsSaltados() {
		return testsSaltados;
	}
	public void setTestsSaltados(int testsSaltados) {
		this.testsSaltados = testsSaltados;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getContraMedico() {
		return contraMedico;
	}
	public void setContraMedico(String contraMedico) {
		this.contraMedico = contraMedico;
	}

	public String getUserMedico() {
		return userMedico;
	}
	public void setUserMedico(String userMedico) {
		this.userMedico = userMedico;
	}
	
	public String getMailMedico() {
		return mailMedico;
	}
	public void setMailMedico(String mail) {
		this.mailMedico = mail;
	}
	public Medico getMedico() {
		return medico;
	}
	public void setMedico(Medico medico) {
		this.medico = medico;
	}
	public PreguntaPaciente getPreguntaPaciente() {
		return preguntaPaciente;
	}
	public void setPreguntaPaciente(PreguntaPaciente preguntaPaciente) {
		this.preguntaPaciente = preguntaPaciente;
	}
	public String getTextoPregunta() {
		return textoPregunta;
	}
	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}
	public String getPagAnterior() {
		return pagAnterior;
	}
	public void setPagAnterior(String pagAnterior) {
		this.pagAnterior = pagAnterior;
	}
	
	public void setResultados(List<Resultado> resultados) {
		this.resultados = resultados;
	}
	public int getIdPregunta() {
		return idPregunta;
	}
	public void setIdPregunta(int idPregunta) {
		this.idPregunta = idPregunta;
	}
	public PreguntaFamiliar getPreguntaFamiliar() {
		return preguntaFamiliar;
	}
	public void setPreguntaFamiliar(PreguntaFamiliar preguntaFamiliar) {
		this.preguntaFamiliar = preguntaFamiliar;
	}
	public Paciente getPacienteSeleccionado() {
		return pacienteSeleccionado;
	}
	public void setPacienteSeleccionado(Paciente pacienteSeleccionado) {
		this.pacienteSeleccionado = pacienteSeleccionado;
	}
	public List<Paciente> getPacientes() {
		return pacientes;
	}
	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
	public List<Familiar> getFamiliares() {
		return familiares;
	}
	public void setFamiliares(List<Familiar> familiares) {
		this.familiares = familiares;
	}
	public Familiar getFamiliarSeleccionado() {
		return familiarSeleccionado;
	}
	public void setFamiliarSeleccionado(Familiar familiarSeleccionado) {
		this.familiarSeleccionado = familiarSeleccionado;
	}
	public PreguntaPaciente getPreguntaPacienteSeleccionada() {
		return preguntaPacienteSeleccionada;
	}
	public void setPreguntaPacienteSeleccionada(PreguntaPaciente preguntaPacienteSeleccionada) {
		this.preguntaPacienteSeleccionada = preguntaPacienteSeleccionada;
	}
	public PreguntaFamiliar getPreguntaFamiliarSeleccionada() {
		return preguntaFamiliarSeleccionada;
	}
	public void setPreguntaFamiliarSeleccionada(PreguntaFamiliar preguntaFamiliarSeleccionada) {
		this.preguntaFamiliarSeleccionada = preguntaFamiliarSeleccionada;
	}
	
	
	
}
