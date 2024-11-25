package com.example.Darkness.Servicios;

import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Repositorios.MangaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MangaService {
    @Autowired
    private MangaRepository mangaRepository;

    public List<Manga> findAll() {
        return mangaRepository.findAll();
    }

    public Optional<Manga> findById(Long id) {
        return mangaRepository.findById(id);
    }

    public Manga save(Manga manga) {
        return mangaRepository.save(manga);
    }

    public void deleteById(Long id) {
        mangaRepository.deleteById(id);
    }

    public Manga markFavorite(Long id, String username) {
        Manga manga = mangaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Manga no encontrado"));
        manga.getFavoritos().add(username);
        return mangaRepository.save(manga);
    }
}
