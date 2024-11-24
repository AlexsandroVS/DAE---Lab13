package com.example.Darkness.Modelos;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "favoritos")
public class Favorito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "manga_id", nullable = false)
    private Manga manga;
}
