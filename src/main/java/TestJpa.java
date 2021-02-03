import domaine.Livre;

import javax.persistence.*;
import java.util.List;

public class TestJpa {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("pu-ys");
        EntityManager em = entityManagerFactory.createEntityManager();

        Livre l = em.find(Livre.class, 5);
        if(l != null){
            System.out.println("Livre 5 : " + l.getTitre() + " " + l.getAuteur());
        }
        EntityTransaction et = em.getTransaction();
        et.begin();


        Livre l2 = new Livre();
        l2.setTitre("Mon livre");
        l2.setAuteur("Yoan Saudrais");
        em.persist(l2);

        if(l2 != null){
            System.out.println("Insertion du livre : " + l2.getId());
        }

        Livre l5 = em.find(Livre.class, 5);

        if(l5 != null){
            l5.setTitre("Du plaisir dans la cuisine");
            em.merge(l5);
            System.out.println("Nouveau titre du livre 5 : " + l5.getTitre());
        }

        TypedQuery<Livre> query = em.createQuery("select l from Livre l where l.titre='Guerre et paix'", Livre.class);
        Livre lTitre = query.getResultList().get(0);
        System.out.println("Livre par rapport au titre : " + lTitre.getId() + " " + lTitre.getTitre() + " " + lTitre.getAuteur());

        TypedQuery<Livre> query2 = em.createQuery("select l from Livre l where l.auteur='Jules Verne'", Livre.class);
        Livre lAuteur = query2.getResultList().get(0);
        System.out.println("Livre par rapport à l'auteur : " + lAuteur.getId() + " " + lAuteur.getTitre() + " " + lAuteur.getAuteur());

        Livre lSuppr = em.find(Livre.class, 7);
        if (lSuppr != null){
            System.out.println("Suppression du livre 7 ...");
            em.remove(lSuppr);
        }

        TypedQuery<Livre> query3 = em.createQuery("select l from Livre l ", Livre.class);
        List<Livre> livres = query3.getResultList();
        System.out.println("Liste des livres : " + livres);


        et.commit();
        em.close();
    }
}
