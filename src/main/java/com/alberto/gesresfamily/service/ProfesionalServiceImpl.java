package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Profesional;
import com.alberto.gesresfamily.exception.ProfesionalNotFoundException;
import com.alberto.gesresfamily.repository.PlanRepository;
import com.alberto.gesresfamily.repository.ProfesionalRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesionalServiceImpl implements ProfesionalService{

    private final Logger logger = LoggerFactory.getLogger(ProfesionalService.class);

    @Autowired
    private ProfesionalRepository profesionalRepository;
    @Autowired
    private PlanRepository planRepository;

    @Override
    public Profesional addProfesional(Profesional profesional) {
        return profesionalRepository.save(profesional);
    }

    @Override
    public Profesional findProfesional(long id) throws ProfesionalNotFoundException {
        return profesionalRepository.findById(id).
                orElseThrow(ProfesionalNotFoundException::new);
    }

    @Override
    public List<Profesional> findAllProfesionales() {
        return profesionalRepository.findAll();
    }

    @Override
    public List<Profesional> findAllProfesionales(long id) {
        return profesionalRepository.findAllProfesionalesById(id);
    }

    @Override
    public Profesional removeProfesional(long id) throws ProfesionalNotFoundException {
        Profesional profesional = profesionalRepository.findById(id).
                orElseThrow(ProfesionalNotFoundException::new);
        profesional.getPlanes().forEach(planes-> planRepository.delete(planes));
        profesionalRepository.delete(profesional);
        return profesional;
    }

    @Override
    public Profesional modifyProfesional(long id, Profesional newProfesional) throws ProfesionalNotFoundException {
        Profesional existingProfesional = profesionalRepository.findById(id).
                orElseThrow(ProfesionalNotFoundException::new);
        /*
         *ToDo Con ModelMapper evito escribir todos los getters y setters pero debo incluir el id tambien en Json
         * para que no me cree un nuevo familiar y si realice la modificación sobre el familiar indicado.
         */
        ModelMapper mapper = new ModelMapper();
        existingProfesional = mapper.map(newProfesional, Profesional.class);
//        profesional.setNombre(newProfesional.getNombre());
//        profesional.setApellidos(newProfesional.getApellidos());
//        profesional.setDni(newProfesional.getDni());
//        profesional.setFechaNacimiento(newProfesional.getFechaNacimiento());
//        profesional.setCategoria(newProfesional.getCategoria());
        return profesionalRepository.save(existingProfesional);
    }

    @Override
    public List<Profesional> findAllProfesionales(String apellidos, String nombre, String dni) {
        return profesionalRepository.findAllProfesionalesByApellidosOrNombreOrDni(apellidos, nombre, dni);
    }

    @Override
    public Profesional patchProfesional(long id, String categoria) throws ProfesionalNotFoundException {
        Profesional profesional = profesionalRepository.findById(id).
                orElseThrow(ProfesionalNotFoundException::new);
        profesional.setCategoria(categoria);
        return profesionalRepository.save(profesional);
    }


}
