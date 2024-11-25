package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Resena;
import com.example.Darkness.Servicios.MangaService;
import com.example.Darkness.Servicios.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/manga")
public class MangaAdminController {

    @Autowired
    private MangaService mangaService;

    @Autowired
    private ResenaService resenaService;

    // Listar todos los mangas
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String listarMangas(Model model) {
        List<Manga> mangas = mangaService.findAll();
        model.addAttribute("mangas", mangas);
        return "admin/manga/listar"; // Vista que lista los mangas
    }

    // Mostrar el formulario para crear un nuevo manga
    @GetMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public String mostrarFormularioCrearManga(Model model) {
        model.addAttribute("manga", new Manga());
        return "admin/manga/crear"; // Vista para crear manga
    }

    // Crear un nuevo manga
    @PostMapping("/crear")
    @PreAuthorize("hasRole('ADMIN')")
    public String crearManga(@ModelAttribute Manga manga) {
        mangaService.save(manga);
        return "redirect:/admin/manga"; // Redirigir a la lista de mangas
    }

    // Eliminar un manga
    @GetMapping("/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarManga(@PathVariable Long id) {
        mangaService.deleteById(id);
        return "redirect:/admin/manga"; // Redirigir a la lista de mangas
    }

    // Eliminar una reseña de un usuario
    @GetMapping("/resena/eliminar/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String eliminarResena(@PathVariable Long id) {
        resenaService.deleteById(id);
        return "redirect:/admin/manga"; // Redirigir a la lista de mangas
    }
}
