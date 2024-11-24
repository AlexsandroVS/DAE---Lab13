package com.example.Darkness.Servicios;

import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Repositorios.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResenaService {
    @Autowired
    private ResenaRepository resenaRepository;

    public List<Resena> findAll() {
        return resenaRepository.findAll();
    }

    public List<Resena> findByManga(Manga manga) {
        return resenaRepository.findByManga(manga);
    }

    public List<Resena> findByUsuario(Usuario usuario) {
        return resenaRepository.findByUsuario(usuario);
    }

    public Resena save(Resena resena) {
        return resenaRepository.save(resena);
    }

    public void deleteById(Long id) {
        resenaRepository.deleteById(id);
    }
}
