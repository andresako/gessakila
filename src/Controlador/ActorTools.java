package Controlador;

import Modelo.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Andres
 */
public class ActorTools {

    private MyTools MT;
    private final SessionFactory sessionFactory;
    private ArrayList<Actor> listaActores;

    public ActorTools() {
        sessionFactory = NewHibernateUtil.getSessionFactory();
        listaActores = new ArrayList<>();
        MT = new MyTools();
    }

    public void RellenarActores() {
        Session session = sessionFactory.openSession();

        Transaction tx;
        tx = session.beginTransaction();
        Query q = session.createQuery("from Actor");
        List<Actor> lista = q.list();
        Iterator<Actor> iter = lista.iterator();
        tx.commit();
        session.close();

        while (iter.hasNext()) {
            Actor act = (Actor) iter.next();
            listaActores.add(act);
        }
    }

    public int ContarActores() {
        return listaActores.size();
    }

    public Actor getActor(int num) {
        return listaActores.get(num - 1);
    }

    public void addActor(Actor act) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.save(act);
            tx.commit();
            listaActores.add(act);

            MT.mostrarError("Añadido correctamente");
        } catch (ConstraintViolationException e) {
            MT.mostrarError("Error, el actor ya existe");
        } finally {
            session.close();
        }
    }

    public void updActor(int pos, Actor act) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.update(act);
            tx.commit();
            listaActores.get(pos - 1).setFirstName(act.getFirstName());
            listaActores.get(pos - 1).setLastName(act.getLastName());
            listaActores.get(pos - 1).setLastUpdate(act.getLastUpdate());

            MT.mostrarError("Editado correctamente");
        } catch (ConstraintViolationException e) {
            MT.mostrarError("Error, al editar");
        } finally {
            session.close();
        }
    }

    public void delActor(int pos) {
        Session session = sessionFactory.openSession();
        Transaction tx;

        try {
            tx = session.beginTransaction();
            session.delete(listaActores.get(pos - 1));
            tx.commit();
            listaActores.remove(pos - 1);

            MT.mostrarError("Borrado correctamente");
        } catch (Exception e) {

            String[] buttons = {"Si", "No"};
            int rc = JOptionPane.showOptionDialog(null, "El actor sale en peliculas,\n quiere limpiar los registros automaticamente??",
                    "Confirmation", JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[1]);
            if (rc == 0) {
                tx = session.beginTransaction();
                Query q = session.createQuery("from FilmActor");
                List<FilmActor> lista = q.list();
                Iterator<FilmActor> iter = lista.iterator();

                while (iter.hasNext()) {
                    FilmActor fAct = (FilmActor) iter.next();
                    if (fAct.getActor().getActorId() == listaActores.get(pos - 1).getActorId()) {
                        session.delete(fAct);
                        tx.commit();
                    }
                }
                session.delete(listaActores.get(pos - 1));
                tx.commit();
                listaActores.remove(pos - 1);

                MT.mostrarError("Borrado correctamente");
            }
        } finally {
            session.close();
        }

    }

    public DefaultListModel dlmActores(int reg) {
        DefaultListModel dlm = new DefaultListModel();
        Session session = sessionFactory.openSession();

        Transaction tx;
        tx = session.beginTransaction();
        Query q = session.createQuery("from FilmActor");
        List<FilmActor> lista = q.list();
        Iterator<FilmActor> iter = lista.iterator();

        while (iter.hasNext()) {
            FilmActor fAct = (FilmActor) iter.next();

            if (fAct.getActor().getActorId() == reg) {
                dlm.addElement(fAct.getFilm().getTitle());
            }
        }

        tx.commit();
        session.close();

        return dlm;
    }

    public Actor[] getListaActores() {
        Actor[] act = new Actor[listaActores.size()];

        for (int i = 0; i < listaActores.size(); i++) {
            act[i] = listaActores.get(i);
        }

        return act;
    }

}
