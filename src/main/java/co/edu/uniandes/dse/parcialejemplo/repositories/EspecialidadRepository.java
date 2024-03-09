package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;

@Repository
public interface EspecialidadRepository extends JpaRepository<EspecialidadEntity,Long>{

    List<EspecialidadEntity> findByNombre(String nombre);
    Optional<EspecialidadEntity> findById(Long id);
}
