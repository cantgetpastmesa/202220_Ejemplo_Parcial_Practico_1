package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity)
            throws EntityNotFoundException, IllegalOperationException {
        log.info("Starting especialidad creation process... ");

        if (especialidadEntity.getNombre() == null || especialidadEntity.getNombre().equals("")) {
            throw new IllegalOperationException("Name is not valid");
        }

        if (especialidadEntity.getDescripcion() == null || especialidadEntity.getDescripcion().equals("")
                || especialidadEntity.getDescripcion().length() >= 10) {
            throw new IllegalOperationException("Name is not valid");
        }
        List<EspecialidadEntity> nombreAlredyExist = especialidadRepository
                .findByNombre(especialidadEntity.getNombre());
        if (!nombreAlredyExist.isEmpty()) {
            throw new IllegalOperationException("There already exists a especialidad with that name");
        }

        log.info("The especialidad was succesfully created");
        return especialidadRepository.save(especialidadEntity);
    }
}
