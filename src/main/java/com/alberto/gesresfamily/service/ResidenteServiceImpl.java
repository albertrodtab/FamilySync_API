package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Centro;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.ResidenteDto;
import com.alberto.gesresfamily.exception.CentroNotFoundException;
import com.alberto.gesresfamily.exception.ResidenteNotFoundException;
import com.alberto.gesresfamily.repository.CentroRepository;
import com.alberto.gesresfamily.repository.ResidenteRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidenteServiceImpl implements ResidenteService{

    private final Logger logger = LoggerFactory.getLogger(ResidenteService.class);

    @Autowired
    private ResidenteRepository residenteRepository;

    @Autowired
    private CentroRepository centroRepository;

    @Override
    public Residente addResidenteCentro (ResidenteDto residenteDto) throws CentroNotFoundException {
        Centro centro = centroRepository.findById(residenteDto.getCentro())
                .orElseThrow(CentroNotFoundException::new);

        ModelMapper mapper = new ModelMapper();
        Residente residente = mapper.map(residenteDto, Residente.class);
        residente.setCentro(centro);
        return residenteRepository.save(residente);
    }

    @Override
    public Residente findResidente(long id) throws ResidenteNotFoundException {
        return residenteRepository.findById(id).
                orElseThrow(ResidenteNotFoundException::new);
    }

    @Override
    public List<Residente> findAllResidentes() {
        return residenteRepository.findAll();
    }

    @Override
    public List<Residente> findAllResidentes(long id) {
        return residenteRepository.findAllResidentesById(id);
    }

    @Override
    public Residente removeResidente(long id) throws ResidenteNotFoundException {
        Residente residente = residenteRepository.findById(id).
                orElseThrow(ResidenteNotFoundException::new);
        residenteRepository.delete(residente);
        return residente;
    }

    @Override
    public Residente modifyResidente(long id, Residente newResidente) throws ResidenteNotFoundException {
        Residente existingResidente = residenteRepository.findById(id).
                orElseThrow(ResidenteNotFoundException::new);
        /*
         *ToDo Con ModelMapper evito escribir todos los getters y setters pero debo incluir el id tambien en Json
         * para que no me cree un nuevo familiar y si realice la modificación sobre el familiar indicado.
         */
        ModelMapper mapper = new ModelMapper();
        existingResidente = mapper.map(newResidente, Residente.class);
//        residente.setApellidos(newResidente.getApellidos());
//        residente.setNombre(newResidente.getNombre());
//        residente.setDni(newResidente.getDni());
//        residente.setFechaNacimiento(newResidente.getFechaNacimiento());
//        residente.setSexo(newResidente.getSexo());
//        residente.setSaldo(newResidente.getSaldo());
        return residenteRepository.save(existingResidente);
    }

    @Override
    public Residente finbById(long idResidente) {
        return residenteRepository.findAllById(idResidente);
    }

    @Override
    public List<Residente> findAllResidentes(String apellidos, String nombre, String dni) {
        return residenteRepository.findAllResidentesByApellidosOrNombreOrDni(apellidos,nombre, dni);
    }

    @Override
    public Residente patchProfesional(long id, float saldo) throws ResidenteNotFoundException {
        Residente residente = residenteRepository.findById(id).
                orElseThrow(ResidenteNotFoundException::new);
        residente.setSaldo(saldo);
        return residenteRepository.save(residente);
    }

    @Override
    public List<Residente> saldoMenor(float saldo) {
        return residenteRepository.saldoMenor(saldo);
    }

}
