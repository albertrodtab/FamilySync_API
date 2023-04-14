package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.ResidenteDto;
import com.alberto.gesresfamily.exception.CentroNotFoundException;
import com.alberto.gesresfamily.exception.ResidenteNotFoundException;

import java.util.List;

public interface ResidenteService {

    Residente addResidenteCentro(ResidenteDto residenteDto) throws CentroNotFoundException;

    Residente findResidente(long id) throws ResidenteNotFoundException;

    List<Residente> findAllResidentes();

    List<Residente> findAllResidentes(long id);

    Residente removeResidente(long id) throws ResidenteNotFoundException;

    Residente modifyResidente(long id, Residente residente) throws ResidenteNotFoundException;

    Residente finbById(long idResidente);

    List<Residente> findAllResidentes(String apellidos, String nombre, String dni);

    Residente patchProfesional(long id, float saldo) throws ResidenteNotFoundException;

    List<Residente> saldoMenor(float saldo);
}
