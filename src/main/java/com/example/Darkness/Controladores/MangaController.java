package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Servicios.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mangas")
public class MangaController {

    @Autowired
    private MangaService mangaService;

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Manga> getAllMangas() {
        return mangaService.findAll();
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
}