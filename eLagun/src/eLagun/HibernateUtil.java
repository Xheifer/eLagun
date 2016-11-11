package eLagun;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static ServiceRegistry serviceRegistry;

	private static SessionFactory buildSessionFactory() {

		try {
			Configuration configuration = new Configuration();
			configuration.configure();
			
			configuration.addAnnotatedClass(Paciente.class);
			configuration.addAnnotatedClass(PreguntaPaciente.class);
			configuration.addAnnotatedClass(PreguntaFamiliar.class);
			configuration.addAnnotatedClass(Familiar.class);
			configuration.addAnnotatedClass(Respuesta.class);
			configuration.addAnnotatedClass(Resultado.class);
			configuration.addAnnotatedClass(Medico.class);
			
			serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
					configuration.getProperties()).build();

			return configuration.buildSessionFactory(serviceRegistry);

		} catch (Throwable ex) {

			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		System.out.println("entro en hibernate util get session factory");
		return sessionFactory;
	}
}

