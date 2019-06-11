package hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import inscriptions.*;


/**
 **** Passerelle Hibernate *****
 ******************************* 
 * Couche : Persistance
 * @author Brahim
 *
 */
public class Passerelle
{
	private static Session session = null;
	private static SessionFactory sessionFactory = null;
	private static final String CONF_FILE = "hibernate/config.cfg.xml";
	private static Transaction transaction = null;
	
	/**
	 * Initialisation Hibernate
	 */
	static void initHibernate()
	{
		try
		{
			Configuration configuration = new Configuration()
					.configure(CONF_FILE);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		catch (HibernateException ex)
		{
			throw new RuntimeException("Probleme de configuration : "
					+ ex.getMessage(), ex);
		}		
	}
	
	/**
	 * Ouverture de la connexion
	 */
	public static void open()
	{
		if (sessionFactory == null)
			initHibernate();
		if (!isOpened())
			session = sessionFactory.openSession();
	}

	/**
	 * Test si la connexion est ouverte
	 * @return boolean
	 * 			
	 */
	public static boolean isOpened()
	{
		return session != null && session.isOpen();
	}
	
	/**
	 * Fermeture de la connexion
	 */
	public static void close()
	{
		if (isOpened())
			session.close();
	}
	
	/**
	 * Supprimer l'élément
	 * @param o
	 */
	public static void delete(Object o)
	{
		transaction = session.beginTransaction();
		session.delete(o);
		transaction.commit();
		transaction = null; 
		session.flush();
	}

	/**
	 * Sauvegarder l'élément
	 * @param o
	 */
	public static void save(Object o)
	{
		Transaction tx = session.beginTransaction();
		session.save(o);
		tx.commit();
		session.flush();
	}

	/**
	 * Récuperation des éléments
	 * @param <T>
	 * @param className
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getData(String className)
	{
		Query query = session.createQuery("from " + className);
		return new ArrayList<T>((List<T>) query.list());
	}

	/**
	 * Récuperation des éléments
	 * @param <T>
	 * @param className
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getData(String className, int id)
	{
		Query query = session.createQuery("from " + className + " where num = "+ id);
		return (T) (query.list().get(0));
	}
	
	
	/**
	 * Récuperation des équipes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static java.util.List<Equipe> getEquipe()
	{
		Query query = session.createQuery("from Equipe");
		return query.list();
	}
	
	/**
	 * Récuperation des personnes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static java.util.List<Personne> getPersonne()
	{
		Query query = session.createQuery("from Personne");
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public static java.util.List<Personne> getPersonne(Equipe equipe)
	{
		Query query = session.createQuery("from Personne p, appartenir a where p.id_ca=a.id_ca and a.id_eq="+equipe.getId());
		return query.list();
	}
	
	/**
	 * Récuperation des candidats
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static java.util.List<Candidat> getCandidat()
	{
		Query query = session.createQuery("from Candidat");
		return query.list();
	}
	
	/**
	 * Récuperation des compétitions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static java.util.List<Competition> getCompetition()
	{
		Query query = session.createQuery("from Competition");
		return query.list();
	}
	
}
