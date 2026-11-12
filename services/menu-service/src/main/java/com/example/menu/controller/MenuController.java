package com.example.menu.controller;


import com.example.menu.entities.dtos.RequestMenuDTO;
import com.example.menu.entities.dtos.RequestPatchDTO;
import com.example.menu.entities.dtos.ResponseMenuDTO;
import com.example.menu.service.MenuService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController

@RequestMapping("menu")
public class MenuController {
    private final MenuService menuService;

    @Autowired
    MenuController(MenuService menuService){
        this.menuService = menuService;
    }
    @PostMapping
    public ResponseMenuDTO saveMenu(@RequestBody RequestMenuDTO responseMenuDTO){
        return menuService.SaveMenu(responseMenuDTO);
    }

    @DeleteMapping
    public void deleteMenu(@RequestBody long id){
        menuService.deleteMenu(id);
    }
    @PatchMapping
    public void updateMenu(@RequestBody  RequestPatchDTO requestPatchDTO){
        menuService.updateMenu(requestPatchDTO);
    }

    @CrossOrigin(origins = "https://www.tecnologiasbraian.com.ar")
    @GetMapping("/platos/{id}")
    public ArrayNode getPlatosPorMenu(@PathVariable long id) throws IOException{
        return menuService.getPlatosPorMenu(id);
    }
}
