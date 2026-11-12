package com.example.Plantilla_micro.controller;

import com.example.Plantilla_micro.entities.dtos.RequesRestauranteDTO;
import com.example.Plantilla_micro.entities.dtos.ResponseRestauranteDTO;
import com.example.Plantilla_micro.entities.entity.Restaurante;
import com.example.Plantilla_micro.service.RestauranteService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("restaurante")
public class RestauranteController {

    private RestauranteService service;

    public RestauranteController(RestauranteService _service){
        this.service = _service;
    }

    @PostMapping
    public ResponseRestauranteDTO saveRestaurante(@RequestBody RequesRestauranteDTO requesRestauranteDTO){
       return service.saveRestaurante(requesRestauranteDTO);
    }

    @DeleteMapping
    public void deleteRestaurante(@RequestBody Long id){
        service.deleteRestaurante(id);
    }

    @GetMapping("/restaurantes")
    List<Restaurante> all(){
        return service.getRestaurantes();

    }
    @GetMapping("/{id}")
    public Optional<Restaurante> getRestaurante (@PathVariable long id) throws IOException {
        return service.getRestauranteById(id);
    }

}
