package com.example.Darkness.Servicios;

import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Repositorios.MangaRepository;
import com.example.Darkness.Repositorios.ResenaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaService {
    private final MangaRepository mangaRepository;
    private final ResenaRepository resenaRepository;

    public ResenaService(MangaRepository mangaRepository, ResenaRepository resenaRepository) {
        this.mangaRepository = mangaRepository;
        this.resenaRepository = resenaRepository;
    }

    public List<Resena> findAll() {
        return resenaRepository.findAll();
    }

    public List<Resena> findByManga(Manga manga) {
        return resenaRepository.findByManga(manga);
    }

    public List<Resena> findByUsuario(Usuario usuario) {
        return resenaRepository.findByUsuario(usuario);
    }

    public Resena save(Long mangaId, Resena resena, String username) {
        // Buscar el manga por ID
        Manga manga = mangaRepository.findById(mangaId)
                .orElseThrow(() -> new RuntimeException("Manga no encontrado"));

        manga.addFavorito(username);

        mangaRepository.save(manga);

        resena.setManga(manga);

        return resenaRepository.save(resena);
    }

    public void deleteById(Long id) {
        resenaRepository.deleteById(id);
    }
}
