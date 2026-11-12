package com.example.pedido.entities.entity;



import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String estado;
    private String fecha;
    private int total;

    //array con los ids de los platos;
    @ElementCollection
    private List<Long> platos;
}
