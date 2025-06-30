package com._5.scaffolding.dtos.auth;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginResponseDTO {
    private String token;
}
