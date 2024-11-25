package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Servicios.MangaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/mangas")
public class MangaAdminController {

    @Autowired
    private MangaService mangaService;

    // Lista de mangas
    @GetMapping
    public String listarMangas(Model model) {
        model.addAttribute("mangas", mangaService.findAll());
        return "admin/lista_mangas";
    }

    // Crear un nuevo manga
    @GetMapping("/crear")
    public String mostrarFormularioCrearManga(Model model) {
        model.addAttribute("manga", new Manga());
        return "admin/crear_manga";
    }

    // Guardar un nuevo manga
    @PostMapping("/guardar")
    public String guardarManga(@ModelAttribute("manga") Manga manga) {
        // Save the manga object
        mangaService.save(manga);
        return "redirect:/admin/mangas";
    }

    // Editar un manga
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarManga(@PathVariable("id") Long id, Model model) {
        Optional<Manga> manga = mangaService.findById(id);
        model.addAttribute("manga", manga.orElse(null));
        return "admin/editar_manga";
    }

    // Actualizar manga
    @PostMapping("/actualizar/{id}")
    public String actualizarManga(@PathVariable("id") Long id, @ModelAttribute("manga") Manga manga) {
        manga.setId(id);
        mangaService.save(manga);
        return "redirect:/admin/mangas";
    }

    // Eliminar manga
    @GetMapping("/eliminar/{id}")
    public String eliminarManga(@PathVariable("id") Long id) {
        mangaService.deleteById(id);
        return "redirect:/admin/mangas";
    }

    // Detalle del manga
    @GetMapping("/detalle/{id}")
    public String mostrarDetalleManga(@PathVariable("id") Long id, Model model) {
        Optional<Manga> manga = mangaService.findById(id);
        model.addAttribute("manga", manga.orElse(null));
        return "admin/detalle_manga";
    }
}
