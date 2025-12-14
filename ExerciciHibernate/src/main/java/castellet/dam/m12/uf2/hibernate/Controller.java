package castellet.dam.m12.uf2.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public static void main(String[] args) {

        final int AF_ESPORT = 1, AF_ATLETA = 2, CERCAR_AT = 3, LLISTAR_AT = 4, SORTIR = 5;

        System.out.println("Iniciant hibernate...");
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Hibernate iniciat.");

        // Crear la vista
        MainView mainView = new MainView();

        boolean sortir = false;
        while (!sortir) {
            // Mostra el menú principal
            int opcio = mainView.mainMenu();
            switch (opcio) {
                case AF_ESPORT:
                    String nameEsport = mainView.sportForm();
                    mainView.printSeparador();
                    Esport newEsport = new Esport(0, nameEsport);
                    saveEntity(session, newEsport);
                    break;

                case AF_ATLETA:
                    Atleta newAtleta = mainView.atletaForm();
                    mainView.printSeparador();
                    saveEntity(session, newAtleta);
                    break;

                case CERCAR_AT:
                    String nomAtleta = mainView.cercaAtleta();
                    mainView.printSeparador();

                    List<Atleta> atletes = findAtletaByName(session, nomAtleta);
                    if (atletes.isEmpty()) {
                        System.out.println("No hi ha atletes amb aquest nom.");
                    } else {
                        mainView.llistaAtletes(atletes);
                    }
                    break;

                case LLISTAR_AT:

                    int codEsport = mainView.demanaEsport();
                    mainView.printSeparador();

                    List<Atleta> atletas = getAtletasByEsport(session, codEsport);

                    if (atletas.isEmpty()) {
                        System.out.println("No hi ha atletes per aquest esport.");
                    } else {
                        mainView.llistaAtletes(atletas);
                    }
                    break;

                case SORTIR:
                    sortir = true;
                    break;
            }
        }
        session.close();
        HibernateUtil.getSessionFactory().close();

    }

    private static void saveEntity(Session session, Object entity) {
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
    }

    private static List<Atleta> findAtletaByName(Session session, String nombre) {
        session.clear();
        Query<Atleta> query = session.createQuery(
                "FROM Atleta WHERE nom LIKE :nombre", Atleta.class);
        query.setParameter("nombre", "%" + nombre + "%");
        return query.getResultList();
    }

    private static List<Atleta> getAtletasByEsport(Session session, int codEsport) {
        session.clear();
        Query<Atleta> query = session.createQuery(
                "FROM Atleta WHERE esport.cod = :codEsport", Atleta.class);
        query.setParameter("codEsport", codEsport);
        return query.getResultList();
    }

}
