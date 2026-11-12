package com.example.telegrambot2;


import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
// Clase para gestionar la información del usuario
public class ProcesInformation {
    private Map<Long, ProcesState> procesStates;


    public ProcesInformation() {
        procesStates = new HashMap<>();

    }

    public void setProcesState(Long chatId, ProcesState state) {
        procesStates.put(chatId, state);
    }

    public ProcesState getProcesState(Long chatId) {
        return procesStates.get(chatId);
    }
}