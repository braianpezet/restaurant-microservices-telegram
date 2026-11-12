package com.example.Plantilla_micro.controller;

import com.example.Plantilla_micro.entities.dtos.RequestPathPlatoDTO;
import com.example.Plantilla_micro.entities.dtos.RequestPlatoDTO;
import com.example.Plantilla_micro.entities.dtos.ResponsePlatoDTO;
import com.example.Plantilla_micro.entities.entity.Plato;
import com.example.Plantilla_micro.service.PlatoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("plato")
public class PlatoController {
    private PlatoService service;

    public  PlatoController(PlatoService _service){
        this.service = _service;
    }

    @PostMapping
    public ResponsePlatoDTO savePlato(@RequestBody RequestPlatoDTO requestPlatoDTO){
        return service.SavePlato(requestPlatoDTO);
    }

    @DeleteMapping
    public void deletePlato(@RequestBody Integer id){
        service.deletePlato(id);
    }

    @PatchMapping
    public RequestPathPlatoDTO pathPlato(@RequestBody RequestPathPlatoDTO requestPathPlatoDTO){
        return service.pathPlatoById(requestPathPlatoDTO);
    }

    @GetMapping("/platos")
    List<Plato> all() {
        return service.getPlatos();
    }
}
