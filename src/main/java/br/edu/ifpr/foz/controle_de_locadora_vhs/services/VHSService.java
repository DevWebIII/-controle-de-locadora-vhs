package br.edu.ifpr.foz.controle_de_locadora_vhs.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.VHS;
import br.edu.ifpr.foz.controle_de_locadora_vhs.repositories.VHSRepository;

@Service
public class VHSService {

    @Autowired
    VHSRepository vhsRepository;

    public List<VHS> findAll() {
        return vhsRepository.findAll()
                            .stream()
                            .filter(VHS::isDisponivel)
                            .toList();
    }

    public void save(VHS vhs) {

        if (vhs.getTitulo() == null || vhs.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha o campo de título");
        }

        if (vhs.getTitulo().length() < 4) {
            throw new IllegalArgumentException("O título deve ter pelo menos 4 caracteres");
        }

        if (vhs.getDataCadastro() == null) {
            throw new IllegalArgumentException("Preencha o campo de data de cadastro");
        }

        vhsRepository.save(vhs);
    }

    public void indisponibilizar(Integer id) {
        VHS vhs = findById(id);
        if (vhs != null) {
            vhs.setDisponivel(false);
            save(vhs);
        }
    }

    public void delete(VHS vhs) {

        vhsRepository.deleteById(vhs.getId());
    }

    public VHS findById(Integer id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Id inválido");
        }

        if (!vhsRepository.existsById(id)) {
            throw new IllegalArgumentException("Fita não encontrada");
        }

        return vhsRepository.findById(id).orElse(null);

    }
    
}
