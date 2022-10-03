package com.example.security.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Users {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;

}
