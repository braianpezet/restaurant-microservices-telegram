package com.example.menu.entities.dtos;

public class PlatoDTO {
    public float id;
    public String nombre;
    public float precio;
    public String descripcion;
    public String categoria;
    public String urlFoto;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    // Setter Methods

    public void setId( float id ) {
        this.id = id;
    }

    public void setNombre( String nombre ) {
        this.nombre = nombre;
    }

    public void setPrecio( float precio ) {
        this.precio = precio;
    }

    public void setDescripcion( String descripcion ) {
        this.descripcion = descripcion;
    }

    public void setCategoria( String categoria ) {
        this.categoria = categoria;
    }

    public void setUrlFoto( String urlFoto ) {
        this.urlFoto = urlFoto;
    }
}
