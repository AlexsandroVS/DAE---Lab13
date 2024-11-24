package com.example.Darkness.Repositorios;
import com.example.Darkness.Modelos.Favorito;
import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuario(Usuario usuario);
    boolean existsByUsuarioAndManga(Usuario usuario, Manga manga);
}
