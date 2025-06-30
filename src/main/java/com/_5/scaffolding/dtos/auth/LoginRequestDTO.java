package com._5.scaffolding.dtos.auth;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDTO {
    private String username;
    private String password;
}
