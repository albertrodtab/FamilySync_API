package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Profesional;
import com.alberto.gesresfamily.exception.ProfesionalNotFoundException;

import java.util.List;

public interface ProfesionalService {

    Profesional addProfesional(Profesional profesional);

    Profesional findProfesional(long id) throws ProfesionalNotFoundException;

    List<Profesional> findAllProfesionales();

    List<Profesional> findAllProfesionales(long id);


    Profesional removeProfesional(long id) throws ProfesionalNotFoundException;

    Profesional modifyProfesional(long id, Profesional profesional) throws ProfesionalNotFoundException;

    List<Profesional> findAllProfesionales(long id, String nombre, String dni);

    Profesional patchProfesional(long id, String categoria) throws ProfesionalNotFoundException;
}
