package com.example.basejavafxhibernate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "editoriales")
@Getter
@Setter
public class Editorial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;
    @Column(name="editorial")
    String editorial;
    @Column(name="pais")
    String pais;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "editorial")
    private List<Libro> libros;

    public Editorial() {
    }

    //En el constructor no es necesario poner el id, se autogenera
    public Editorial(String editorial, String pais) {
        this.editorial = editorial;
        this.pais = pais;
    }

    public Editorial(int id, String editorial, String pais) {
        this.id=id;
        this.editorial = editorial;
        this.pais = pais;
    }
    public String toString(){
        return editorial + " (" + pais + ")";
    }



}
