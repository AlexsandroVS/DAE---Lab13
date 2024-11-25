package com.example.Darkness.Controladores;

import com.example.Darkness.Modelos.Usuario;
import com.example.Darkness.Servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String mostrarFormularioRegistro() {
        return "registro"; // Asegúrate de tener una plantilla "registro.html"
    }

    @PostMapping
    public String registrarUsuario(Usuario usuario) {
        usuarioService.registrarUsuario(usuario); // Implementa el servicio para registrar al usuario
        return "redirect:/login"; // Redirige al login después del registro
    }
}
