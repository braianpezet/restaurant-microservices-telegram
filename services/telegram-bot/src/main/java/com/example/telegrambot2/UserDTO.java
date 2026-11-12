package com.example.telegrambot2;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    String userName;
    String password;
    String phoneNumber;
    String email;
    String adress;
}
