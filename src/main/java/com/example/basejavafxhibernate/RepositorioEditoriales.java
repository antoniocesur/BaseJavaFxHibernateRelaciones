package com.example.basejavafxhibernate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class RepositorioEditoriales {
    public void insertar(Editorial editorial){
        Session s=HibernateUtil.openSession();
        Transaction t= s.beginTransaction();
        s.save(editorial);
        t.commit();
        s.close();
    }

    public void modificar(Editorial editorial){
        Session s=HibernateUtil.openSession();
        Transaction t=s.beginTransaction();
        s.update(editorial);
        t.commit();
        s.close();
    }

    public void borrar(Editorial editorial){
        Session s=HibernateUtil.openSession();
        Transaction t=s.beginTransaction();
        s.delete(editorial);
        t.commit();
        s.close();
    }
    public ObservableList<Editorial> listarTodas(){
        Session s=HibernateUtil.openSession();
        Transaction t=s.beginTransaction();
        ArrayList<Editorial> lista= (ArrayList<Editorial>) s.createQuery("from Editorial").list();
        ObservableList<Editorial> listaObservable = FXCollections.observableArrayList(lista);
        return listaObservable;
    }


}
