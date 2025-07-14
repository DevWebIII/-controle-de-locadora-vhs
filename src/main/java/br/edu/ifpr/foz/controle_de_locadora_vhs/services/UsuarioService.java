package br.edu.ifpr.foz.controle_de_locadora_vhs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Usuario;
import br.edu.ifpr.foz.controle_de_locadora_vhs.repositories.UsuarioRepository;

public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void save(Usuario usuario) throws IllegalArgumentException {

        String email = usuario.getEmail();
        String senha = usuario.getSenha();

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de e-mail");
        }

        if (email.length() < 6) {
            throw new IllegalArgumentException("E-mail inválido");
        }

        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de senha");
        }

        String senhaCriptografada = encoder.encode(senha);
        usuario.setSenha(senhaCriptografada);

        usuarioRepository.save(usuario);
    }
    
}
