package com.example.DSSB_AT.Security;

import com.example.DSSB_AT.DTO.LoginRequestDTO;
import com.example.DSSB_AT.DTO.RegisterRequestDTO;
import com.example.DSSB_AT.Models.Professor;
import com.example.DSSB_AT.Repository.ProfessorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthController authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private ProfessorRepository professorRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authenticationManager, jwtUtils, professorRepository, encoder);
    }

    PasswordEncoder encoder = new PasswordEncoder() {
        @Override
        public String encode(CharSequence rawPassword) {
            return rawPassword.toString();
        }

        @Override
        public boolean matches(CharSequence rawPassword, String encodedPassword) {
            return rawPassword.toString().equals(encodedPassword);
        }
    };


    @Test
    void loginDeveRetornarTokenQuandoCredenciaisValidas() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("professor@email.com");
        login.setSenha("123456");

        Authentication authMock = mock(Authentication.class);
        ProfessorUserDetails userDetailsMock = mock(ProfessorUserDetails.class);

        when(userDetailsMock.getUsername()).thenReturn("professor@email.com");
        when(authMock.getPrincipal()).thenReturn(userDetailsMock);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authMock);
        when(jwtUtils.gerarToken("professor@email.com")).thenReturn("token123");

        ResponseEntity<?> response = authController.login(login);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("token123", response.getBody());

        verify(authenticationManager).authenticate(any());
        verify(jwtUtils).gerarToken("professor@email.com");
    }

    @Test
    void loginDeveRetornar401QuandoCredenciaisInvalidas() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setEmail("professor@email.com");
        login.setSenha("123456");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Falha de autenticação"));

        ResponseEntity<?> response = authController.login(login);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Credenciais inválidas", response.getBody());
    }

    @Test
    void registerDeveSalvarProfessorQuandoEmailNaoExiste() {
        RegisterRequestDTO register = new RegisterRequestDTO();
        register.setNome("Carlos");
        register.setEmail("carlos@email.com");
        register.setSenha("123456");

        when(professorRepository.findByEmail("carlos@email.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.register(register);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Professor registrado com sucesso", response.getBody());
        verify(professorRepository).save(any(Professor.class));
    }

    @Test
    void registerDeveRetornarBadRequestQuandoEmailJaExiste() {
        RegisterRequestDTO register = new RegisterRequestDTO();
        register.setNome("Carlos");
        register.setEmail("carlos@email.com");
        register.setSenha("123456");

        when(professorRepository.findByEmail("carlos@email.com"))
                .thenReturn(Optional.of(new Professor()));

        ResponseEntity<?> response = authController.register(register);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Email já está em uso", response.getBody());
        verify(professorRepository, never()).save(any());
    }
}
