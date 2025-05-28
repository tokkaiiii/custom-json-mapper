package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UserSimpleDto {
    private Long id;
    private String username;
    private String email;
    private String password;
}
