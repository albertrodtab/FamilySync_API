package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Familiar;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.exception.FamiliarNotFoundException;

import java.util.List;

public interface FamiliarService {

    Familiar addFamiliar(Familiar familiar);

    Familiar findFamiliar(long id) throws FamiliarNotFoundException;

    List<Familiar> findAllFamiliares();

    List<Familiar> findAllFamiliares(long id);

    Familiar removeFamiliar(long id) throws FamiliarNotFoundException;

    Familiar modifyFamiliar(long id, Familiar familiar) throws FamiliarNotFoundException;

    void addRelacion(Residente residente, Familiar familiar);

    List<Familiar> findAllFamiliares(String nombre, String dni, String telefono);

    Familiar patchfamiliar(long id, String telefono) throws FamiliarNotFoundException;
}

