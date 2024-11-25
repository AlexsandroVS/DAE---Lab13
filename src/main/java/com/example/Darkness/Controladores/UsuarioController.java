package com.example.Darkness.Controladores;


import com.example.Darkness.Modelos.Manga;
import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Servicios.MangaService;
import com.example.Darkness.Servicios.UsuarioService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final MangaService mangaService ;

    public UsuarioController(UsuarioService usuarioService, MangaService mangaService) {
        this.usuarioService = usuarioService;
        this.mangaService = mangaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        // Obtener la lista de mangas y agregarla al modelo
        List<Manga> mangas = mangaService.findAll();
        model.addAttribute("mangas", mangas);

        // Redirigir a la página de administración si es un administrador
        if (usuario.getRole().equals("ADMIN")) {
            return "redirect:/admin/usuarios";
        }

        return "home";  // Página de inicio con la lista de mangas
    }
    @PostMapping("/register")
    public String UsuarioRegisterP(Usuario usuario, Model model) {
        System.out.println("Recibiendo datos: " + usuario.getUsername() + " " + usuario.getPassword());
        try {
            usuario.setRole("USER");
            usuarioService.registrarUsuario(usuario);
            System.out.println("Usuario registrado exitosamente: " + usuario.getUsername());
            return "redirect:/login";
        } catch (Exception e) {
            System.err.println("Error al registrar el usuario: " + e.getMessage());
            model.addAttribute("error", "Error al registrar el usuario.");
            return "user-register";
        }
    }
    @GetMapping("/manga/{id}")
    public String verDetalleManga(@PathVariable Long id, Model model) {
        Optional<Manga> manga = mangaService.findById(id);  // Obtener manga por ID desde el servicio
        model.addAttribute("manga", manga);  // Agregar manga al modelo
        return "user-manga";  // El nombre del archivo HTML con el detalle del manga
    }


    @GetMapping("/adminRegister")
    public String RegisterAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin-register";
    }

    @PostMapping("/adminRegister")
    public String adminRegisterP(Usuario usuario, Model model) {
        try {
            usuario.setRole("ADMIN");

            usuarioService.registrarUsuario(usuario);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el administrador.");
            return "admin-register";
        }
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/usuarios")
    @PostFilter("filterObject.role == 'USER'")
    @PostAuthorize("hasPermission(returnObject, 'VIEW')")
    public String obtenerUsuarios(@RequestParam(required = false) String roleFilter, Model model) {
        List<Usuario> usuarios;

        if (roleFilter != null && !roleFilter.isEmpty()) {
            usuarios = usuarioService.obtenerUsuariosPorRol(roleFilter);
        } else {
            usuarios = usuarioService.obtenerTodosUsuarios();
        }

        model.addAttribute("usuarios", usuarios);
        return "dashboard";
    }


}
