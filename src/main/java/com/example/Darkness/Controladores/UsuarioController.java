package com.example.Darkness.Controladores;


import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Servicios.UsuarioService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Usuario usuario = usuarioService.buscarPorUsername(username);

        if (usuario.getRole().equals("ADMIN")) {
            return "redirect:/admin/usuarios";
        }

        return "home";
    }

    @GetMapping("/registro")
    public String registroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";  // Formulario de registro para usuarios
    }

    @PostMapping("/registro")
    public String procesarRegistroUsuario(Usuario usuario, Model model) {
        System.out.println("Recibiendo datos: " + usuario.getUsername() + " " + usuario.getPassword());
        try {
            usuario.setRole("USER");
            usuarioService.registrarUsuario(usuario);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario.");
            return "registro";
        }
    }


    @GetMapping("/registro-admin")
    public String registroAdmin(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro-admin";
    }

    @PostMapping("/registro-admin")
    public String procesarRegistroAdmin(Usuario usuario, Model model) {
        try {
            usuario.setRole("ADMIN");

            usuarioService.registrarUsuario(usuario);

            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el administrador.");
            return "registro-admin";
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
        return "admin";
    }


}
