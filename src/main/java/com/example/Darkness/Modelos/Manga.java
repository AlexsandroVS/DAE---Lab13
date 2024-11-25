package com.example.Darkness.Modelos;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "mangas")
public class Manga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String autor;

    @Column(nullable = false)
    private String genero;

    @Lob
    private String sinopsis;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private String visibilidad;

    @ElementCollection
    @CollectionTable(name = "manga_favoritos", joinColumns = @JoinColumn(name = "manga_id"))
    @Column(name = "usuario")
    private List<String> favoritos = new ArrayList<>();

    public void addFavorito(String username) {
        if (!favoritos.contains(username)) {
            favoritos.add(username);
        }
    }
}
