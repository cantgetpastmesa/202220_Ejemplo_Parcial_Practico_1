package co.edu.uniandes.dse.parcialejemplo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity,Long>{

    List<MedicoEntity> findByNombre(String nombre);
    List<MedicoEntity> findByApellido(String apellido);
    List<MedicoEntity> findByNombreCompleto(String nombre,String apellido);
    List<MedicoEntity> findByRegistroMedico(String registroMedico);
    Optional<MedicoEntity> findById(Long id);
}
