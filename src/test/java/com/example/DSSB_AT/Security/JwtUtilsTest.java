package com.example.DSSB_AT.Security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;
    private final String secret = "12345678901234567890123456789012"; // deve ter 32 bytes para HS256
    private final long expiration = TimeUnit.MINUTES.toMillis(5); // 5 minutos

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils(secret, expiration);
    }

    @Test
    void deveGerarTokenNaoNulo() {
        String token = jwtUtils.gerarToken("usuario");
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deveExtrairUsernameCorretamente() {
        String username = "usuario";
        String token = jwtUtils.gerarToken(username);

        String extraido = jwtUtils.extrairUsername(token);
        assertEquals(username, extraido);
    }

    @Test
    void deveValidarTokenValido() {
        String username = "usuario";
        String token = jwtUtils.gerarToken(username);

        boolean valido = jwtUtils.validarToken(token, username);
        assertTrue(valido);
    }

    @Test
    void deveRetornarFalsoParaUsernameIncorreto() {
        String username = "usuario";
        String token = jwtUtils.gerarToken(username);

        boolean valido = jwtUtils.validarToken(token, "outroUsuario");
        assertFalse(valido);
    }

    @Test
    void deveDetectarTokenExpirado() throws InterruptedException {
        // token com expiração de 1 segundo para teste
        JwtUtils curto = new JwtUtils(secret, 1000);
        String token = curto.gerarToken("usuario");

        // esperar 1.1 segundo para garantir expiração
        Thread.sleep(1100);

        boolean valido = curto.validarToken(token, "usuario");
        assertFalse(valido);
    }

    @Test
    void deveRetornarUsernameMesmoParaTokenExpirado() throws InterruptedException {
        JwtUtils curto = new JwtUtils(secret, 1000);
        String token = curto.gerarToken("usuario");

        Thread.sleep(1100);

        String extraido = curto.extrairUsername(token);
        assertEquals("usuario", extraido);
    }
}
