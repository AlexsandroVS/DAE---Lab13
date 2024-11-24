package com.example.Darkness.Repositorios;

import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByManga(Manga manga);
    List<Resena> findByUsuario(Usuario usuario);
}
