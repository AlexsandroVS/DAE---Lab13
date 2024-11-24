package com.example.Darkness.Modelos;

import jakarta.persistence.*;
import lombok.Data;

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
}
