package br.edu.ifpr.foz.controle_de_locadora_vhs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Categoria;
import br.edu.ifpr.foz.controle_de_locadora_vhs.repositories.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository categoriaRepository;

    public List<Categoria> findAll() {

        if (categoriaRepository.findAll().isEmpty()) {
            throw new IllegalArgumentException("Nenhuma categoria encontrada");
        }

       return categoriaRepository.findAll();
    }

    public Categoria findById(Integer id) {

        if (id <= 0){
            throw new IllegalArgumentException("Id inválido");
        }

        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria save(Categoria categoria) {

        if (categoria.getNome() == null || categoria.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da categoria não pode ser vazio");
        }

        if (categoria.getNome().length() < 4){
            throw new IllegalArgumentException("O nome da categoria deve ter pelo menos 4 caracteres");
        }

        return categoriaRepository.save(categoria);
    }

    public void delete(Integer id) {
        categoriaRepository.deleteById(id);
    }
    
}
