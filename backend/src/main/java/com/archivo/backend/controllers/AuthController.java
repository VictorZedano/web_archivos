package com.archivo.backend.controllers;

import com.archivo.backend.dtos.*; // Asegúrate de que JwtResponseDto esté aquí
import com.archivo.backend.entities.Sede;
import com.archivo.backend.repositories.SedeRepository;
import com.archivo.backend.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.archivo.backend.repositories.RoleRepository;
import com.archivo.backend.entities.Rol;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final RoleRepository roleRepository;
    private final SedeRepository sedeRepository;

    public AuthController(AuthService authService,
            RoleRepository roleRepository,
            SedeRepository sedeRepository) {
        this.authService = authService;
        this.roleRepository = roleRepository;
        this.sedeRepository = sedeRepository;
    }

    /**
     * Endpoint de Login. Ahora devuelve un objeto JSON (JwtResponseDto) que
     * contiene el token.
     * URL: POST /auth/login
     */
    @PostMapping("/login")
    // 1. Tipo de retorno cambiado a JwtResponseDto
    public ResponseEntity<?> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Error de validación de campos
            return ResponseEntity.badRequest().body(new MensajeDto("Revise sus credenciales"));
        }
        try {
            // El servicio debe devolver la cadena del token JWT (String)
            String jwt = authService.authenticate(loginUserDto.getUsuario(), loginUserDto.getContraseña());

            // 2. Éxito: Envolvemos el token en el DTO de respuesta
            return ResponseEntity.ok(new JwtResponseDto(jwt));

        } catch (Exception e) {
            // Error de autenticación (credenciales inválidas, etc.)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensajeDto(e.getMessage()));
        }
    }

    // El resto de los métodos se mantiene igual, ya que devuelven String o
    // List<DTO>

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NuevoUsuarioDto NuevoUsuarioDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {
            authService.registerUser(NuevoUsuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth() {
        return ResponseEntity.ok().body("Autenticado");
    }

    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getRoles() {
        List<Rol> roles = roleRepository.findAll();
        List<RoleDto> dtos = roles.stream().map(r -> {
            RoleDto dto = new RoleDto();
            dto.setId(r.getId());
            dto.setRoles(r.getRoles());
            return dto;
        }).toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/sedes-nombres")
    public ResponseEntity<List<SedeNameDto>> getSedesParaRegistro() {
        List<Sede> sedes = sedeRepository.findAll();

        List<SedeNameDto> dtos = sedes.stream().map(s -> {
            SedeNameDto dto = new SedeNameDto();
            dto.setId(s.getId());
            dto.setNombre(s.getNombre());
            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/sedes")
    public ResponseEntity<List<SedeDto>> getSedes() {
        List<Sede> sedes = sedeRepository.findAll();

        List<SedeDto> dtos = sedes.stream().map(s -> {
            SedeDto dto = new SedeDto();
            dto.setId(s.getId());
            dto.setNombre(s.getNombre());
            dto.setDireccion(s.getDireccion());
            return dto;
        }).toList();

        return ResponseEntity.ok(dtos);
    }
}