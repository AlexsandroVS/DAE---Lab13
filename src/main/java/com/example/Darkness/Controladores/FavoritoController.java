package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Favorito;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Servicios.FavoritoService;
import com.example.Darkness.Servicios.MangaService;
import com.example.Darkness.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MangaService mangaService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Favorito> getFavoritosByUsuario() {
        // Obtener el nombre de usuario desde el contexto de seguridad
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUser.getUsername();

        // Buscar usuario y obtener sus favoritos
        Usuario usuario = usuarioService.buscarPorUsername(username);
        return favoritoService.findByUsuario(usuario);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public Favorito addFavorito(@RequestParam Long mangaId) {
        // Obtener el nombre de usuario desde el contexto de seguridad
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = currentUser.getUsername();

        // Buscar usuario y manga
        Usuario usuario = usuarioService.buscarPorUsername(username);
        Manga manga = mangaService.findById(mangaId)
                .orElseThrow(() -> new RuntimeException("Manga no encontrado"));

        // Crear y guardar favorito
        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setManga(manga);

        return favoritoService.save(favorito);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public void deleteFavorito(@PathVariable Long id) {
        favoritoService.deleteById(id);
    }
}
