package com.example.pedido.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@AllArgsConstructor
@Data
public class RequestPatchDTO {
    public long id;
    public String estado;
    public int total;
    public List<Long> platos;
}
