package com.example.Darkness.Repositorios;


import com.example.Darkness.Modelos.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MangaRepository extends JpaRepository<Manga, Long> {
    List<Manga> findByGenero(String genero);
    List<Manga> findByVisibilidad(String visibilidad);
}
