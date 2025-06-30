package com._5.scaffolding.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RolUsuarioTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllArticulos_SinAutenticacion_DebeFallar() throws Exception {
        // Probamos acceder al endpoint sin estar logueado.
        // Esperamos un 403 Forbidden (o 401 si no se ha configurado del todo)
        mockMvc.perform(get("/api/articulos"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "USUARIO") // Simulamos un usuario con rol EMPLEADO/CAJA
    void testGetAllArticulos_ConRolEmpleado_DebeFuncionar() throws Exception {
        // Ahora probamos con un usuario que tiene el rol permitido.
        // Esperamos un 200 OK.
        mockMvc.perform(get("/api/articulos"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN") // Simulamos un usuario con rol ADMINISTRADOR
    void testGetAllArticulos_ConRolAdmin_DebeFuncionar() throws Exception {
        // El administrador también debería tener acceso.
        // Esperamos un 200 OK.
        mockMvc.perform(get("/api/articulos/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ROL_NO_PERMITIDO") // Un rol cualquiera
    void testGetAllArticulos_ConRolIncorrecto_DebeFallar() throws Exception {
        // Probamos con un rol que no tiene acceso.
        // Esperamos un 403 Forbidden.
        mockMvc.perform(get("/api/articulos/1"))
                .andExpect(status().isForbidden());
    }
}
