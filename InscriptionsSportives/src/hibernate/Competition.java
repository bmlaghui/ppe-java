package hibernate;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Entity
@Table(name = "competition")
class Competition
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idCompetition")
	private int idCompetition;

	@Column(name = "nomCompetition")
	private String nomCompetition;

	@Column(name = "dateClotureCompetition")
	private LocalDate  dateClotureCompetition;

	@Column(name = "enEquipe")
	private boolean enEquipe;
	
	public Competition(String nomCompetition,LocalDate  dateClotureCompetition, boolean enEquipe)
	{
		this.nomCompetition = nomCompetition;
		this.dateClotureCompetition = dateClotureCompetition;
		this.enEquipe= enEquipe;
	}


private static Session getSession() throws HibernateException
	{
		Configuration configuration = new Configuration()
				.configure("hibernate/PremierExemple.cfg.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
		return sessionFactory.openSession();
	}

	public static void main(String[] args)
	{
		try
		{
			Session s = getSession();
			
			Competition football = new Competition("test",LocalDate.now(),false);
			Transaction t = s.beginTransaction();
			s.persist(football);
			t.commit();
			s.close();
		}
		catch (HibernateException ex)
		{
			throw new RuntimeException("Probleme de configuration : "
					+ ex.getMessage(), ex);
		}
	}
}
