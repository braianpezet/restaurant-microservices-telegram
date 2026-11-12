package com.example.telegrambot2;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@Service
public class TelegramBoot extends TelegramLongPollingBot {
    private final HttpClient httpClient = HttpClients.createDefault();
    private long lastProcessedMessageId = 0;

    private final ProcesInformation procesInformation = new ProcesInformation();
    private final UserDTO userDTO = new UserDTO();

    @Override
    public void onUpdateReceived(Update update) {
        String messageTextReceived = update.getMessage().getText();
        final String username = update.getMessage().getFrom().getFirstName();
        final Long userid = update.getMessage().getFrom().getId();
        final Long chatId = update.getMessage().getChatId();
        System.out.println("Escribieron en el bot: " + messageTextReceived);
        System.out.println("Estado: " + procesInformation.getProcesState(chatId));

        if (procesInformation.getProcesState(chatId) == null) {
            if (messageTextReceived.equals("/start")) {
                sendMessage(chatId, "¡Bienvenido/a a nuestro bot de pedidos! Puedes usar los botones de navegación para realizar el pedido.");
            } else if (messageTextReceived.equals("/help")) {
                sendMessage(chatId, "Los comandos disponibles son:\n/informacion: Te muestra la información del restaurante\n/menu: Te muestra todos los platos en el menú\n/login: Con el podes acceder al sistema o registrarte");
            } else if (messageTextReceived.equals("/login")) {
                try {
                    System.out.println("/login");
                    System.out.println(userDTO.toString());
                    System.out.println(username);
                    System.out.println(userid);
                    boolean usuarioRegistrado = loginUsuario(chatId, username, userid);
                    System.out.println(usuarioRegistrado);
                    if (!usuarioRegistrado) {
                        sendMessage(chatId, "Por favor ingresa tu correo electrónico");
                        userDTO.password = String.valueOf(userid);
                        userDTO.userName = username;
                        procesInformation.setProcesState(chatId, ProcesState.ENTER_EMAIL);
                    } else {
                        sendMessage(chatId, "Bienvenido: " + username + ". Ya puedes realizar tu pedido");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (messageTextReceived.equals("/menu")) {
                try {
                    getMenu(chatId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else if (messageTextReceived.equals("/informacion")) {
                try {
                    getRestaurante(chatId);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (procesInformation.getProcesState(chatId) == ProcesState.ENTER_EMAIL) {
            userDTO.email = update.getMessage().getText();
            sendMessage(chatId, "Por favor ingrese su número de teléfono");
            procesInformation.setProcesState(chatId, ProcesState.ENTER_PHONE);
        } else if (procesInformation.getProcesState(chatId) == ProcesState.ENTER_PHONE) {
            userDTO.phoneNumber = update.getMessage().getText();
            sendMessage(chatId,"Por favor ingresa tu direccion de entrega");
            procesInformation.setProcesState(chatId, ProcesState.ENTER_ADRESS);
        }else if (procesInformation.getProcesState(chatId) == ProcesState.ENTER_ADRESS){
            userDTO.adress = update.getMessage().getText();
            sendMessage(chatId, "Confirma tus datos:\nEmail: " + userDTO.email + "\nTeléfono: " + userDTO.phoneNumber + "\nDireccion: " + userDTO.getAdress() );
            procesInformation.setProcesState(chatId,ProcesState.CONFIRMATION);
        }else if (procesInformation.getProcesState(chatId) == ProcesState.CONFIRMATION) {
            procesInformation.setProcesState(chatId, null);
            if ("si".equalsIgnoreCase(update.getMessage().getText())) {
                try {
                    String registro = registerUser(userDTO);
                    sendMessage(chatId, registro);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                sendMessage(chatId, "Ingresa nuevamente tu correo");
                procesInformation.setProcesState(chatId, ProcesState.ENTER_EMAIL);
            }
        }
    }

    public boolean loginUsuario(long chatId, String userName, Long idUser) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            String URL = "http://localhost:8085/oauth/users/login";
            HttpPost httpPost = new HttpPost(URL);
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode jsonBody = objectMapper.createObjectNode();
            jsonBody.put("user_name", userName);
            jsonBody.put("password", idUser);
            StringEntity requestEntity = new StringEntity(jsonBody.toString(), ContentType.APPLICATION_JSON);
            httpPost.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println(response);
            System.out.println(response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            } else {
                return false;
            }
        } finally {
            // Cerrar el HttpClient después de su uso
            httpClient.close();
        }
    }

    public String registerUser(UserDTO userDTO) throws IOException {
        String URL = "http://localhost:8085/oauth/users/register";
        HttpPost httpPost = new HttpPost(URL);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonBody = objectMapper.createObjectNode();
        jsonBody.put("user_name", userDTO.userName);
        jsonBody.put("password", userDTO.password);
        jsonBody.put("email", userDTO.email);
        jsonBody.put("mobile", userDTO.phoneNumber);
        jsonBody.put("adress",userDTO.adress);
        StringEntity requestEntity = new StringEntity(jsonBody.toString(), ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response);
        System.out.println(response.getStatusLine().getStatusCode());
        return response.getStatusLine().getStatusCode() == 201 ? "Registro exitoso" : "Fallo el registro";
    }

    public void getMenu(long chatId) throws IOException {
        String URL = "http://localhost:7070/menu/platos/1";
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        String menu = EntityUtils.toString(response.getEntity());
        System.out.println(menu);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(menu);
        for (JsonNode platoNode : jsonNode) {
            String nombre = platoNode.get("nombre").asText();
            String precio = platoNode.get("precio").asText();
            String descripcion = platoNode.get("descripcion").asText();
            String categoria = platoNode.get("categoria").asText();
            String urlFoto = platoNode.get("urlFoto").asText();
            sendMessage(chatId, nombre + "\n" + "$" + precio + "\n" + descripcion + "\n" + "Categoría: " + categoria + "\n" + urlFoto);
        }
    }

    public void getRestaurante(Long chatId) throws IOException {
        String URL = "http://localhost:8088/restaurante/1";
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        String restaurante = EntityUtils.toString(response.getEntity());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(restaurante);
        String nombre = jsonNode.get("nombre").asText();
        String direccion = jsonNode.get("direccion").asText();
        String presentacion = jsonNode.get("presentacion").asText();
        String telefono = jsonNode.get("telefono").asText();
        sendMessage(chatId, nombre + "\n" + presentacion + "\n" + "Nuestra dirección es " + direccion + "\n" + "Por cualquier inconveniente, puedes comunicarte a nuestro teléfono: " + telefono);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            // Manejar la excepción adecuadamente
        }
    }


    @org.springframework.beans.factory.annotation.Value("${telegram.bot.username:restauranteAcme_bot}")
    private String botUsername;

    @org.springframework.beans.factory.annotation.Value("${telegram.bot.token:YOUR_TELEGRAM_BOT_TOKEN_HERE}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


}