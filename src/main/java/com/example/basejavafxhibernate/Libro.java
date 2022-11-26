package com.example.basejavafxhibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
@Entity
@Table(name = "libros")
@AllArgsConstructor
@Setter
@Getter
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @ManyToOne
    @JoinColumn(name = "FK_editorial", nullable = false, updatable = false)
    private Editorial editorial;

    String titulo;
    String autor;
    Date fecha;

    public Libro(){

    }

    public String toString(){
        return titulo + " (" + autor + ")";
    }
}
