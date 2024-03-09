package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.*;

import lombok.extern.slf4j.Slf4j;

/**
 * Clase que implementa la conexión con la persistencia para las entidades Medico y Especialidad.
 *
 * @author Felipe Mesa
 */

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

	/**
	 * Agregar un especialidad a un world cup
	 *
	 * @param medicoId  El id world cup a guardar
	 * @param especialidadId El id del especialidad al cual se le va a guardar el world cup.
	 * @return El world cup que fue agregado al especialidad.
	 * @throws EntityNotFoundException
	 */
	@SuppressWarnings("null")
	@Transactional
	public EspecialidadEntity addEspecialidad(Long especialidadId, Long medicoId) throws EntityNotFoundException {
		log.info("Inicia proceso de asociar el especialidad con id = {0} al world cup con id = " + medicoId, especialidadId);
		Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);
		if (especialidadEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.ESPECIALIDAD_NOT_FOUND);

		Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
		if (medicoEntity.isEmpty())
			throw new EntityNotFoundException(ErrorMessage.MEDICO_NOT_FOUND);

		medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
		log.info("Termina proceso de asociar el especialidad con id = {0} al medico con id = {1}", especialidadId, medicoId);
		return especialidadEntity.get();
	}
}