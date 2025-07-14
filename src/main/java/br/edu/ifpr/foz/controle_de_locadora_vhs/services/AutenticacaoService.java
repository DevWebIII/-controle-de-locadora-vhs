package br.edu.ifpr.foz.controle_de_locadora_vhs.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Usuario;
import br.edu.ifpr.foz.controle_de_locadora_vhs.repositories.UsuarioRepository;

@Service
public class AutenticacaoService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario login(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de e-mail");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de senha");
        }

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email.trim());

        if (!usuarioOptional.isPresent()) {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }

        Usuario usuario = usuarioOptional.get();

        if (!encoder.matches(senha, usuario.getSenha())) {
            throw new IllegalArgumentException("Usuário ou senha incorretos");
        }

        return usuario;
    }

    public void cadastrar(Usuario usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
        throw new IllegalArgumentException("Email já cadastrado");
    }

    String senhaCriptografada = encoder.encode(usuario.getSenha());
    usuario.setSenha(senhaCriptografada);

    usuarioRepository.save(usuario);
    }
}
