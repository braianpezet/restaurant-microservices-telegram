package com.example.menu.service;


import com.example.menu.entities.dtos.RequestMenuDTO;
import com.example.menu.entities.dtos.ResponseMenuDTO;
import com.example.menu.entities.dtos.RequestPatchDTO;
import com.example.menu.entities.entity.Menu;
import com.example.menu.repository.MenuRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.Optional;

@Service
public class MenuService {
    private final MenuRepository repository; //repositorio
    private final String URL = "http://localhost:8080/plato";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Cliente HTTP para enviar las solicitudes
    private HttpClient httpClient = HttpClients.createDefault();
    @Autowired
    MenuService(MenuRepository menuRepository) {
        this.repository = menuRepository;
    } //autowire del repositorio

    public ResponseMenuDTO SaveMenu(RequestMenuDTO menuDto){
        Menu menu = new Menu();
        menu.setNombre(menuDto.nombre);
        menu.setPlatos(menuDto.platos);
        menu.setIdRestaurante(menuDto.idRestaurante);
        Menu menudb = repository.save(menu);
        ResponseMenuDTO respuesta = new ResponseMenuDTO();
        respuesta.id = menudb.getId();
        return respuesta;
    }

    public void deleteMenu(long id) {
        repository.deleteById(id);
    }

    public RequestPatchDTO updateMenu(@RequestBody RequestPatchDTO requestPatchDTO){
        Optional<Menu> menu = repository.findById(requestPatchDTO.getId());
        Menu menu1 = menu.get();
        menu1.setIdRestaurante(requestPatchDTO.getIdRestaurante());
        menu1.setNombre(requestPatchDTO.getNombre());
        menu1.setPlatos(requestPatchDTO.getPlatos());
        Menu menudb = repository.save(menu1);
        return requestPatchDTO;
    }

    public ArrayNode getPlatosPorMenu(Long id) throws IOException {
        // Realizar la solicitud HTTP para obtener todos los platos
        HttpGet request = new HttpGet(URL + "/platos");
        HttpResponse response = httpClient.execute(request);
        String result = EntityUtils.toString(response.getEntity());

        // Obtener el menú por su ID
        Optional<Menu> menu = repository.findById(id);

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
            if (menu.get().getPlatos().contains(id_plato)) {
                // Agregar el elemento al ArrayNode de platos del menú
                platosEnMenu.add(elemento);
            }
        }

        System.out.println(menu.get().getPlatos());

        // Devolver los platos del menú como un JSON en forma de String
        return platosEnMenu;
    }
}
