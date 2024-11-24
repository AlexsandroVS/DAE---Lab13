package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Servicios.ResenaService;
import com.example.Darkness.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Resena> getResenas() {
        return resenaService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Resena createResena(@RequestBody Resena resena) {
        // Obtener el nombre de usuario desde el contexto de seguridad
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUser.getUsername();

        // Buscar usuario y asociar la reseña
        Usuario usuario = usuarioService.buscarPorUsername(username);
        resena.setUsuario(usuario);

        return resenaService.save(resena);
    }
}
