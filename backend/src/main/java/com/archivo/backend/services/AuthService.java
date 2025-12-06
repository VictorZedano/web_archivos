package com.archivo.backend.services;

import com.archivo.backend.dtos.NuevoUsuarioDto;
import com.archivo.backend.entities.*;
import com.archivo.backend.jwt.JwtUtil;
import com.archivo.backend.repositories.*;
import org.springframework.security.authentication.AuthenticationManager; // Importación necesaria
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager; // 🔑 CORRECCIÓN 2: Inyección del Bean

    private final RoleRepository roleRepository;
    private final SedeRepository sedeRepository;
    private final AreaInternaRepository areaInternaRepository;

    public AuthService(UsuarioService usuarioService, RoleRepository roleRepository, SedeRepository sedeRepository,
            UsuarioRepository usuarioRepository, AreaInternaRepository areaInternaRepository,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
            AuthenticationManager authenticationManager) { // 🔑 CORRECCIÓN 3: Reemplazar Builder
        this.usuarioService = usuarioService;
        this.roleRepository = roleRepository;
        this.sedeRepository = sedeRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager; // Asignación del bean
        this.areaInternaRepository = areaInternaRepository;
    }

    public String authenticate(String usuario, String contraseña) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario,
                contraseña);

        // 🔑 CORRECCIÓN 4: Usar el bean AuthenticationManager directamente
        Authentication authResult = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authResult);
        return jwtUtil.generateToken(authResult);
    }

    public void registerUser(NuevoUsuarioDto nuevoUsuarioDto) {
        // ... (Tu lógica de registro, que funciona y hashea la contraseña)
        if (usuarioService.existsByUsuario(nuevoUsuarioDto.getUsuario())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }

        Integer rolId = nuevoUsuarioDto.getRolId();
        Integer sedeId = nuevoUsuarioDto.getSedeId();
        Integer areaInternaId = nuevoUsuarioDto.getAreaInternaId();

        Rol rol = roleRepository.findById(rolId)
                .orElseThrow(() -> new IllegalArgumentException("Rol con ID " + rolId + " no encontrado"));

        Sede sede = sedeRepository.findById(sedeId)
                .orElseThrow(() -> new IllegalArgumentException("Sede con ID " + sedeId + " no encontrada"));

        AreaInterna areaInterna = areaInternaRepository.findById(areaInternaId)
                .orElseThrow(
                        () -> new IllegalArgumentException("Área interna con ID " + areaInternaId + " no encontrada"));

        Usuario usuario = new Usuario();
        usuario.setUsuario(nuevoUsuarioDto.getUsuario());
        usuario.setContraseña(passwordEncoder.encode(nuevoUsuarioDto.getContraseña()));
        usuario.setNombreCompleto(nuevoUsuarioDto.getNombreCompleto());
        usuario.setDni(nuevoUsuarioDto.getDni());
        usuario.setCorreo(nuevoUsuarioDto.getCorreo());
        usuario.setTelefono(nuevoUsuarioDto.getTelefono());

        usuario.setRol(rol);
        usuario.setSede(sede);
        usuario.setAreaInterna(areaInterna);

        usuario.setEstado(true);
        usuario.setFechaCreado(LocalDateTime.now());

        usuarioService.save(usuario);
    }
}