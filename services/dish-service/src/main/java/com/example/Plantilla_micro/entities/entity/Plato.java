package com.example.Plantilla_micro.entities.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nombre;
    private Integer precio;
    private String descripcion;
    private String categoria;
    private String urlFoto;

    //private List<String> ingredientes;
}
