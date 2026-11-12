package com.example.Plantilla_micro.service;

import com.example.Plantilla_micro.entities.dtos.RequestPathPlatoDTO;
import com.example.Plantilla_micro.entities.dtos.RequestPlatoDTO;
import com.example.Plantilla_micro.entities.dtos.ResponsePlatoDTO;
import com.example.Plantilla_micro.entities.entity.Plato;
import com.example.Plantilla_micro.repository.PlatoRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {
    PlatoRepository repository;

    public PlatoService( PlatoRepository _repository){
        this.repository = _repository;
    }

    public ResponsePlatoDTO SavePlato(RequestPlatoDTO platoDto){
        Plato plato = new Plato();

        plato.setNombre(platoDto.nombre);
        plato.setCategoria(platoDto.categoria);
        plato.setDescripcion(platoDto.descripcion);
        plato.setPrecio(platoDto.precio);
        plato.setUrlFoto(platoDto.urlFoto);

        Plato platoDB = repository.save(plato);

        ResponsePlatoDTO respuesta = new ResponsePlatoDTO();
        respuesta.id = platoDB.getId();
        return respuesta;
    }

    public void deletePlato(Integer id){
        repository.deleteById(id);
    }

    public RequestPathPlatoDTO pathPlatoById(RequestPathPlatoDTO requestPathPlatoDTO){
        Optional<Plato> Optionalplato = repository.findById(requestPathPlatoDTO.id);

        Plato plato = Optionalplato.get();
        plato.setPrecio(requestPathPlatoDTO.precio);

        Plato platoDB = repository.save(plato);

        RequestPathPlatoDTO respuesta = new RequestPathPlatoDTO();
        respuesta.id = platoDB.getId();
        respuesta.precio = platoDB.getPrecio();

        return respuesta;
    }

    public List<Plato> getPlatos(){
        return repository.findAll();
    }
}
