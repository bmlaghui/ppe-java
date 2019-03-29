package hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Configuration config = new Configuration();
        SessionFactory sessionFactory = config.configure().buildSessionFactory();
            
        // Ouverture session
                Session session = sessionFactory.openSession() ;
                session = sessionFactory.openSession();
                
        //Fermeture session
                session.close();
    }
}