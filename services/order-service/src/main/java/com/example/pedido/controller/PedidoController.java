package com.example.pedido.controller;



import com.example.pedido.entities.dtos.DeleteDTO;
import com.example.pedido.entities.dtos.RequestPatchDTO;
import com.example.pedido.entities.dtos.RequestPedidoDTO;
import com.example.pedido.entities.dtos.ResponsePedidoDTO;
import com.example.pedido.service.PedidoService;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("pedido")
public class PedidoController {
    private final PedidoService pedidoService;

    @Autowired
    PedidoController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }
    @PostMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public ResponsePedidoDTO savePedido(@RequestBody RequestPedidoDTO requestPedidoDTO) throws IOException {
        return pedidoService.SavePedido(requestPedidoDTO);
    }

    @DeleteMapping
    @CrossOrigin(origins = "http://127.0.0.1:5500")
    public void deletePedido(@RequestBody DeleteDTO deleteDTO){
        pedidoService.deletePedido(deleteDTO);
    }
    @PatchMapping
    public void updatePedido(@RequestBody RequestPatchDTO requestPatchDTO){
        pedidoService.updatePedido(requestPatchDTO);
    }
    @CrossOrigin("http://127.0.0.1:5500")
    @GetMapping("/platos/{id}")
    public ArrayNode getPlatosEnPedido(@PathVariable long id) throws IOException{
        return pedidoService.getPlatosEnPedido(id);
    }
}
