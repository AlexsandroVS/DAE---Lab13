package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Servicios.MangaService;
import com.example.Darkness.Servicios.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/mangas")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ResenaService resenaService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Manga> getAllMangas() {
        return mangaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Manga getMangaById(@PathVariable Long id) {
        return mangaService.findById(id).orElseThrow(() -> new RuntimeException("Manga no encontrado"));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Manga createManga(@RequestBody Manga manga) {
        return mangaService.save(manga);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteManga(@PathVariable Long id) {
        mangaService.deleteById(id);
    }

    @PostMapping("/{id}/resena")
    @PreAuthorize("hasRole('USER')")
    public Resena addResena(@PathVariable Long id, @RequestBody Resena resena, Principal principal) {
        return resenaService.save(id, resena, principal.getName());
    }

    @PutMapping("/{id}/favorito")
    @PreAuthorize("hasRole('USER')")
    public Manga markFavorite(@PathVariable Long id, Principal principal) {
        return mangaService.markFavorite(id, principal.getName());
    }

    @DeleteMapping("/resena/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteResena(@PathVariable Long id) {
        resenaService.deleteById(id);
    }
}
