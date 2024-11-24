package com.example.Darkness.Servicios;

import com.example.Darkness.Modelos.Favorito;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Repositorios.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FavoritoService {
    @Autowired
    private FavoritoRepository favoritoRepository;

    public List<Favorito> findByUsuario(Usuario usuario) {
        return favoritoRepository.findByUsuario(usuario);
    }

    public boolean existsByUsuarioAndManga(Usuario usuario, Manga manga) {
        return favoritoRepository.existsByUsuarioAndManga(usuario, manga);
    }
    public Optional<Favorito> findById(Long id) {
        return favoritoRepository.findById(id);
    }

    public Favorito save(Favorito favorito) {
        return favoritoRepository.save(favorito);
    }

    public void deleteById(Long id) {
        favoritoRepository.deleteById(id);
    }
}
