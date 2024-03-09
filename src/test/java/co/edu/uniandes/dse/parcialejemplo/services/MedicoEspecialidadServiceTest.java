package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.*;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Pruebas de logica de la relacion Medico - Especialidad
 *
 * @author Felipe Mesa
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
class MedicoEspecialidadServiceTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    @Autowired
    private TestEntityManager entityManager;

    private List<EspecialidadEntity> especialidadsList = new ArrayList<>();
    private List<MedicoEntity> medicosList = new ArrayList<>();

    /**
     * Configuración inicial de la prueba.
     */
    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity medicos = factory.manufacturePojo(MedicoEntity.class);
            medicos.setRegistroMedico("RM"+i);
            entityManager.persist(medicos);
            medicosList.add(medicos);
        }
        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            
            entityManager.persist(entity);
            especialidadsList.add(entity);
            if (i == 0) {
                medicosList.get(i).getEspecialidades().add(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Medico existente a un Especialidad.
     * 
     * @throws EntityNotFoundException
     */
    @Test
    void testAddEspecialidad() throws EntityNotFoundException {
        EspecialidadEntity entity = especialidadsList.get(0);
        MedicoEntity medicoEntity = medicosList.get(1);
        EspecialidadEntity response = medicoEspecialidadService.addEspecialidad(entity.getId(), medicoEntity.getId());

        assertNotNull(response);
        assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para asociar un Medico existente a un Especialidad que no existe.
     * 
     * @throws EntityNotFoundException
     */
    @Test
    void testAddInvalidEspecialidad() {
        assertThrows(EntityNotFoundException.class, () -> {
            MedicoEntity medicoEntity = medicosList.get(1);
            medicoEspecialidadService.addEspecialidad(0L, medicoEntity.getId());
        });
    }

    /**
     * Prueba para asociar un Medico que no existe a un Especialidad.
     * 
     * @throws EntityNotFoundException
     */
    @Test
    void testAddEspecialidadInvalidMedico() {
        assertThrows(EntityNotFoundException.class, () -> {
            EspecialidadEntity entity = especialidadsList.get(0);
            medicoEspecialidadService.addEspecialidad(entity.getId(), 0L);
        });
    }
}