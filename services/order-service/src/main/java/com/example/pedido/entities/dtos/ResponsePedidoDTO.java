package com.example.pedido.entities.dtos;


import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.Data;

@Data
public class ResponsePedidoDTO {
    public long id;
    public String fecha;
    public String estado;
    public ArrayNode platos;
    public int total;
}
