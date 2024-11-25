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

    @PostMapping("/{mangaId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Resena createResena(@PathVariable Long mangaId, @RequestBody Resena resena) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUser.getUsername();

        Usuario usuario = usuarioService.buscarPorUsername(username);
        resena.setUsuario(usuario);

        return resenaService.save(mangaId, resena, username);
    }
}
