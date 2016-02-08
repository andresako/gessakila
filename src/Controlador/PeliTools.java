package Controlador;

import Modelo.Film;
import Modelo.FilmActor;
import Modelo.FilmActorId;
import Modelo.Language;
import Modelo.LenguajeCombo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Andres
 */
public class PeliTools {

    private final MyTools MT = new MyTools();
    private final SessionFactory sessionFactory;
    private ArrayList<Film> listaPelis;

    public PeliTools() {
        sessionFactory = NewHibernateUtil.getSessionFactory();
        listaPelis = new ArrayList<>();
    }

    public void RellenarPelis() {
        listaPelis = new ArrayList<>();
        Session session = sessionFactory.openSession();

        Transaction tx;
        tx = session.beginTransaction();
        Query q = session.createQuery("from Film");
        List<Film> lista = q.list();
        Iterator<Film> iter = lista.iterator();
        tx.commit();
        session.close();

        while (iter.hasNext()) {
            Film fil = (Film) iter.next();
            listaPelis.add(fil);
        }
    }

    public int ContarPelis() {
        return listaPelis.size();
    }

    public Film getPeli(int pos) {
        return listaPelis.get(pos - 1);
    }

    public void addFilm(Film fil) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.save(fil);
            tx.commit();
            listaPelis.add(fil);

            MT.mostrarError("Añadido correctamente");
        } catch (ConstraintViolationException e) {
            MT.mostrarError("Error, la peli ya existe");
        } finally {
            session.close();
        }

    }

    public void updFilm(int pos, Film fil) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.update(fil);
            tx.commit();

            MT.mostrarError("Editado correctamente");
        } catch (Exception e) {
            MT.mostrarError(e.getMessage());
        } finally {
            session.close();
        }
        RellenarPelis();
    }

    public void delFilm(int pos) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.delete(listaPelis.get(pos-1));
            tx.commit();
            listaPelis.remove(pos-1);

            MT.mostrarError("Eliminado correctamente");
        } catch (ConstraintViolationException e) {
            MT.mostrarError("Error");
        } finally {
            session.close();
        }
    }

    public int fechaEstreno(int reg) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(listaPelis.get(reg - 1).getReleaseYear());

        return cal.get(Calendar.YEAR);
    }

    public void RellenarComboLenguaje(JComboBox jcom) {
        Session session = sessionFactory.openSession();

        Transaction tx;
        tx = session.beginTransaction();
        Query q = session.createQuery("from Language");
        List<Language> lista = q.list();
        Iterator<Language> iter = lista.iterator();
        tx.commit();
        session.close();
        LenguajeCombo[] listaLang = new LenguajeCombo[lista.size()];
        //Language[] listal = new Language[lista.size()];
        int cnt = 0;
        while (iter.hasNext()) {
            Language lan = (Language) iter.next();
            listaLang[cnt] = new LenguajeCombo(lan, lan.getName(), lan.getLanguageId());
            //listal[cnt] = lan;
            cnt++;
        }
        jcom.setModel(new DefaultComboBoxModel(listaLang));
    }

    public void RellenarComboRating(JComboBox jcom) {

        String[] listaRating = new String[]{"G", "PG", "PG-13", "R", "NC-17"};
        jcom.setModel(new DefaultComboBoxModel(listaRating));

    }

    public DefaultListModel dlmPelis(int reg) {
        DefaultListModel dlm = new DefaultListModel();
        Session session = sessionFactory.openSession();

        Transaction tx;
        tx = session.beginTransaction();
        Query q = session.createQuery("from FilmActor");
        List<FilmActor> lista = q.list();
        Iterator<FilmActor> iter = lista.iterator();

        while (iter.hasNext()) {
            FilmActor fAct = (FilmActor) iter.next();

            if (fAct.getFilm().getFilmId() == reg) {
                dlm.addElement(fAct.getActor().getFirstName());
                //dlm.addElement(fAct.getActor());

            }
        }

        tx.commit();
        session.close();

        return dlm;
    }

    public void addActor(Short peliId, Short actorId) {
        FilmActorId faId = new FilmActorId(actorId, peliId);
        FilmActor fA = new FilmActor(faId, null, null, new Date());

        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.save(fA);
            tx.commit();

            System.out.println("Añadido correctamente: Actor: " + actorId + ", Peli: " + peliId);
        } catch (ConstraintViolationException e) {
            MT.mostrarError("Error" + e.getMessage());
        } finally {
            session.close();
        }
    }

    public void delActor(Short peliId, String nombre) {
        ActorTools AT = new ActorTools();
        AT.RellenarActores();

        for (int x = 1; x <= AT.ContarActores(); x++) {
            String actor = AT.getActor(x).getFirstName();
            if (actor.equalsIgnoreCase(nombre)) {

                FilmActorId faId = new FilmActorId(AT.getActor(x).getActorId(), peliId);
                FilmActor fA = new FilmActor(faId, null, null, new Date());

                Session session = sessionFactory.openSession();
                Transaction tx;

                try {
                    tx = session.beginTransaction();
                    session.delete(fA);
                    tx.commit();

                    System.out.println("Eliminado correctamente: Actor: " + nombre + ", Peli: " + peliId);
                } catch (ConstraintViolationException e) {
                    MT.mostrarError("Error" + e.getMessage());
                } finally {
                    session.close();
                }

            }
        }
    }
}
