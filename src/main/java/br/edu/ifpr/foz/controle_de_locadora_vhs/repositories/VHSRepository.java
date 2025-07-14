package br.edu.ifpr.foz.controle_de_locadora_vhs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.Categoria;
import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.TapeStatus;
import br.edu.ifpr.foz.controle_de_locadora_vhs.entities.VHS;
import java.util.List;


@Repository
public interface VHSRepository extends JpaRepository<VHS, Integer>{

    List<VHS> findByCategoria(Categoria categoria);
    List<VHS> findByStatus(TapeStatus status);
}
