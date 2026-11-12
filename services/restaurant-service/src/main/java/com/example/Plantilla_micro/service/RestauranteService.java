package com.example.Plantilla_micro.service;

import com.example.Plantilla_micro.entities.dtos.RequesRestauranteDTO;
import com.example.Plantilla_micro.entities.dtos.ResponseRestauranteDTO;
import com.example.Plantilla_micro.entities.entity.Restaurante;
import com.example.Plantilla_micro.repository.RestauranteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    RestauranteRepository repository;

    public RestauranteService(RestauranteRepository repository){
        this.repository = repository;
    }

    public ResponseRestauranteDTO saveRestaurante(RequesRestauranteDTO requesRestauranteDTO) {
        Restaurante restaurante = new Restaurante();
        restaurante.setNombre(requesRestauranteDTO.nombre);
        restaurante.setDireccion(requesRestauranteDTO.direccion);
        restaurante.setPresentacion(requesRestauranteDTO.presentacion);
        restaurante.setTelefono(requesRestauranteDTO.telefono);
        Restaurante restauranteDB = repository.save(restaurante);
        ResponseRestauranteDTO responseRestauranteDTO = new ResponseRestauranteDTO();
        responseRestauranteDTO.id = restauranteDB.getId();
        return responseRestauranteDTO;
    }

    public void deleteRestaurante(Long id){
        repository.deleteById(id);
    }

    public List<Restaurante> getRestaurantes(){
        return  repository.findAll();
    }

    public Optional<Restaurante> getRestauranteById(Long id){
        return repository.findById(id);
    }









}
