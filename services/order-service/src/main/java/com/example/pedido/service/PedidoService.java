package com.example.pedido.service;


import com.example.pedido.CurrentTimeService;
import com.example.pedido.entities.dtos.DeleteDTO;
import com.example.pedido.entities.dtos.RequestPatchDTO;
import com.example.pedido.entities.dtos.RequestPedidoDTO;
import com.example.pedido.entities.dtos.ResponsePedidoDTO;
import com.example.pedido.entities.entity.Pedido;
import com.example.pedido.repository.PedidoRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Service
@RequestMapping("/pedido")
public class PedidoService {
    private final PedidoRepository repository; //repositorio
    private final String URL = "http://localhost:8080/plato";
    private final CurrentTimeService currentTimeService=  new CurrentTimeService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Cliente HTTP para enviar las solicitudes
    private HttpClient httpClient = HttpClients.createDefault();
    @Autowired
    PedidoService(PedidoRepository pedidoRepository) {
        this.repository = pedidoRepository;
    } //autowire del repositorio



    public ResponsePedidoDTO SavePedido(RequestPedidoDTO pedidoDTO) throws IOException {
        Pedido pedido = new Pedido();
        pedido.setEstado(pedidoDTO.estado);
        pedido.setPlatos(pedidoDTO.platos);
        pedido.setFecha(currentTimeService.getCurrentTime());
        //pedido.setTotal(pedidoDTO.total);
        Pedido pedidodb = repository.save(pedido);
        ResponsePedidoDTO respuesta = new ResponsePedidoDTO();
        respuesta.id = pedidodb.getId();
        respuesta.estado = pedido.getEstado();
        //respuesta.total = pedido.getTotal();
        respuesta.fecha = pedido.getFecha();
        HttpGet request = new HttpGet(URL + "/platos");
        HttpResponse response = httpClient.execute(request);
        System.out.println(response);
        String result = EntityUtils.toString(response.getEntity());
        JsonNode jsonNode = objectMapper.readTree(result);
        ArrayNode platosEnPedido = objectMapper.createArrayNode();
        for (Long platoId : pedidoDTO.platos) {
            for (JsonNode platoNode : jsonNode) {
                long id = platoNode.get("id").asLong();
                //respuesta.total = respuesta.total + platoNode.get("precio").asInt();
                if (id == platoId) {
                    platosEnPedido.add(platoNode);
                    break;
                }
            }
        }
        respuesta.setPlatos(platosEnPedido);

        int totalPlato = 0;

        for(JsonNode plato: respuesta.getPlatos()){
            totalPlato = totalPlato + plato.get("precio").asInt();
        }
        respuesta.setTotal(totalPlato);
        return respuesta;
    }


    public void deletePedido(DeleteDTO deleteDTO) {
        repository.deleteById(deleteDTO.id);
    }


    public RequestPatchDTO updatePedido(@RequestBody RequestPatchDTO requestPatchDTO){
        Optional<Pedido> pedido = repository.findById(requestPatchDTO.getId());
        Pedido pedido1 = pedido.get();
        pedido1.setEstado(requestPatchDTO.getEstado());
        pedido1.setTotal(requestPatchDTO.getTotal());
        pedido1.setPlatos(requestPatchDTO.getPlatos());
        Pedido pedidodb = repository.save(pedido1);
        return requestPatchDTO;
    }


    public ArrayNode getPlatosEnPedido(Long id) throws IOException {
        // Realizar la solicitud HTTP para obtener todos los platos
        HttpGet request = new HttpGet(URL + "/platos");
        HttpResponse response = httpClient.execute(request);
        String result = EntityUtils.toString(response.getEntity());

        // Obtener el menú por su ID
        Optional<Pedido> pedido = repository.findById(id);

        // Convertir el JSON en un array de objetos JsonNode
        JsonNode jsonNode = objectMapper.readTree(result);

        // Crear un nuevo ArrayNode para almacenar los platos del menú
        ArrayNode platosEnMenu = objectMapper.createArrayNode();

        // Iterar sobre los elementos del JSON
        for (JsonNode elemento : jsonNode) {
            // Obtener el valor del campo "id" del plato
            long id_plato = elemento.get("id").asLong();
            System.out.println("ID: " + id_plato);

            // Verificar si el ID del plato está en la lista de platos del menú
            if (pedido.get().getPlatos().contains(id_plato)) {
                // Agregar el elemento al ArrayNode de platos del menú
                platosEnMenu.add(elemento);
            }
        }

        System.out.println(pedido.get().getPlatos());

        // Devolver los platos del menú como un JSON en forma de String
        return platosEnMenu;
    }
}
