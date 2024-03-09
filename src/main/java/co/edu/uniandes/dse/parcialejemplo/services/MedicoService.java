package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    MedicoEntity createMedico(MedicoEntity medicoEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Starting doctor creation process... ");

        if (medicoEntity.getNombre() == null || medicoEntity.getNombre().equals("")) {
            throw new IllegalOperationException("Name is not valid");
        }

        if (medicoEntity.getApellido() == null || medicoEntity.getApellido().equals("")) {
            throw new IllegalOperationException("Apellido is not valid");
        }
        List<MedicoEntity> nombreAlredyExist = medicoRepository.findByRegistroMedico(medicoEntity.getRegistroMedico());
        if (!nombreAlredyExist.isEmpty()) {
            throw new IllegalOperationException("There already exists a doctor with medical registry number");
        }

        if (medicoEntity.getRegistroMedico() == null || medicoEntity.getRegistroMedico().equals("")
                || !medicoEntity.getRegistroMedico().startsWith("RM")) {
            throw new IllegalOperationException("Registro medico is not valid");
        }
        log.info("The doctor was succesfully created");
        return medicoRepository.save(medicoEntity);
    }
}
