package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Plan;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.PlanDto;
import com.alberto.gesresfamily.exception.PlanNotFoundException;
import com.alberto.gesresfamily.exception.ProfesionalNotFoundException;

import java.util.List;

public interface PlanService {

    Plan addPlan(PlanDto planDto) throws ProfesionalNotFoundException;

    Plan findPlan(long id) throws PlanNotFoundException;

    List<Plan> findAllPlanes();

    List<Plan> findAllPlanesById(long id);

    Plan removePlan(long id) throws PlanNotFoundException;

    Plan modifyPlan(long id, Plan plan) throws PlanNotFoundException;


    void addParticipa(Residente residente, Plan plan);

    List<Plan> findAllPlanes(String terapia, String nombrePlan, String descripcion);

    Plan patchPlan(long id, boolean importante) throws PlanNotFoundException;

    int numResidentes(long id)throws PlanNotFoundException;


}

