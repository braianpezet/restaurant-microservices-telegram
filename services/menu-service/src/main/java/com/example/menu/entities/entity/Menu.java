package com.example.menu.entities.entity;



import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private long idRestaurante;

    //array con los ids de los platos;
    @ElementCollection
    private List<Long> platos;


}
