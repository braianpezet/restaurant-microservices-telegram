package com.example.menu.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class RequestPatchDTO {
    public long id;
    public String nombre;
    public long idRestaurante;
    public List<Long> platos;
}
