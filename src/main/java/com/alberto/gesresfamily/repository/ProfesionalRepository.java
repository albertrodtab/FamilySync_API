package com.alberto.gesresfamily.repository;

import com.alberto.gesresfamily.domain.Profesional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfesionalRepository extends CrudRepository<Profesional, Long> {

    Profesional findAllById(long id);
    List<Profesional> findAll();
    List<Profesional> findAllProfesionalesById(long id);

    List<Profesional> findAllProfesionalesByIdOrNombreOrDni(long id, String nombre, String dni);
}
