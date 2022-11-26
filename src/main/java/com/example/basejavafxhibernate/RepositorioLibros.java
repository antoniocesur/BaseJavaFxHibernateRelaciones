package com.example.basejavafxhibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class RepositorioLibros {
    public void insertar(Libro elemento){
        Session s=HibernateUtil.openSession();
        Transaction t= s.beginTransaction();
        s.save(elemento);
        t.commit();
        s.close();
    }

    public void modificar(Libro elemento){
        Session s=HibernateUtil.openSession();
        Transaction t=s.beginTransaction();
        s.update(elemento);
        t.commit();
        s.close();
    }

    public void borrar(Libro elemento){
        Session s=HibernateUtil.openSession();
        Transaction t=s.beginTransaction();
        s.delete(elemento);
        t.commit();
        s.close();
    }
    public ObservableList<Libro> listarTodos(){
        Session s=HibernateUtil.openSession();

        Transaction t=s.beginTransaction();
        ArrayList<Libro> lista= (ArrayList<Libro>) s.createQuery("from Libro").list();
        ObservableList<Libro> listaObservable = FXCollections.observableArrayList(lista);
        return listaObservable;
    }

}
