package com.alberto.gesresfamily.service;


import com.alberto.gesresfamily.domain.Centro;
import com.alberto.gesresfamily.exception.CentroNotFoundException;

import java.util.List;

public interface CentroService {

    Centro addCentro(Centro centro);

    List<Centro> findAll();

    List<Centro> findAllCentros(String nombre, String numRegistro, String email);

    Centro removeCentro(long id) throws CentroNotFoundException;

    Centro modifyCentro(long id, Centro centro) throws CentroNotFoundException;

    Centro findById(long id) throws CentroNotFoundException;

    Centro patchCentro(long id, String telefono) throws CentroNotFoundException;

    int numResidentes(long id) throws CentroNotFoundException;
}
