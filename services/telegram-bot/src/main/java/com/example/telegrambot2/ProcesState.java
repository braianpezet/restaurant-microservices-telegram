package com.example.telegrambot2;

public enum ProcesState {
    START,          // Estado inicial
    ENTER_EMAIL,    // El usuario debe ingresar su correo electrónico
    ENTER_PHONE,    // El usuario debe ingresar su número de teléfono

    ENTER_ADRESS, // El usuario debe ingresar su direccion
    CONFIRMATION    // El usuario debe confirmar sus datos

}