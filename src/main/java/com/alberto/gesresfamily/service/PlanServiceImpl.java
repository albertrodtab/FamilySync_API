package com.alberto.gesresfamily.service;

import com.alberto.gesresfamily.domain.Plan;
import com.alberto.gesresfamily.domain.Profesional;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.PlanDto;
import com.alberto.gesresfamily.exception.PlanNotFoundException;
import com.alberto.gesresfamily.exception.ProfesionalNotFoundException;
import com.alberto.gesresfamily.repository.PlanRepository;
import com.alberto.gesresfamily.repository.ProfesionalRepository;
import com.alberto.gesresfamily.repository.ResidenteRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanServiceImpl implements PlanService{

    private final Logger logger = LoggerFactory.getLogger(PlanServiceImpl.class);

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private ResidenteRepository residenteRepository;
    @Autowired
    private ProfesionalRepository profesionalRepository;

    @Override
    public Plan addPlan(PlanDto planDto) throws ProfesionalNotFoundException {
        //tengo que recuperar los objetos enteros para pasarlos a la base de datos no solo el id
        Profesional profesional = profesionalRepository.findById(planDto.getProfesional())
                .orElseThrow(ProfesionalNotFoundException::new);
        /*
        vamos a utilizar un mapeador(introducir dependencia modelmapper) para no tener que ir indicandolo al
        nuevo objeto todos los atributos
        Le indico mapeame el objeto que te indico en el nuevo plan con lo que debe tener cualquier plan y ya cojes la
        información y yo le indico a mayores los objetos completos que no estan en el DTO, no solo los id.
        Con el map me ahorro los get set para poder crear un objeto. Solo mapea lo que es común a ambos objetos y
        lo demas lo ignora.
         */
        ModelMapper mapper = new ModelMapper();
        Plan plan = mapper.map(planDto, Plan.class);
        plan.setProfesional(profesional);
        return planRepository.save(plan);
    }

    @Override
    public void addParticipa(Residente residente, Plan plan) {
        logger.info("Inicio addParticipa");
        residente.getPlanes().add(plan);
        plan.getResidentes().add(residente);
        residenteRepository.save(residente);
        planRepository.save(plan);

        logger.info("Fin addParticipa");
    }

    @Override
    public List<Plan> findAllPlanes(long id, String nombrePlan, boolean importante) {
        return planRepository.findByIdOrNombrePlanOrImportante(id, nombrePlan, importante);
    }

    @Override
    public Plan findPlan(long id) throws PlanNotFoundException {
        return planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
    }

    @Override
    public List<Plan> findAllPlanes() {
        return planRepository.findAll();
    }

    @Override
    public List<Plan> findAllPlanesById(long id) {
        return planRepository.findAllPlanesById(id);
    }

    @Override
    public Plan removePlan(long id) throws PlanNotFoundException{
        Plan plan = planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
        planRepository.delete(plan);
        return plan;
    }

    @Override
    public Plan modifyPlan(long id, Plan newPlan) throws PlanNotFoundException{
        Plan ExistingPlan = planRepository.findById(id).
                orElseThrow(PlanNotFoundException::new);
        /*
         * Con ModelMapper evito escribir todos los getters y setters pero debo incluir el id tambien en Json
         * para que no me cree un nuevo familiar y si realice la modificación sobre el familiar indicado.
         */
        ModelMapper mapper = new ModelMapper();
        ExistingPlan = mapper.map(newPlan, Plan.class);
//        ExistingPlan.setNombrePlan(newPlan.getNombrePlan());
//        ExistingPlan.setTerapia(newPlan.getTerapia());
//        ExistingPlan.setDescripcion(newPlan.getDescripcion());
//        ExistingPlan.setFechaInicio(newPlan.getFechaInicio());
//        ExistingPlan.setFechaFin(newPlan.getFechaFin());
//        ExistingPlan.setImportante(newPlan.getImportante());
        return planRepository.save(ExistingPlan);
    }

    @Override
    public Plan patchPlan(long id, boolean importancia) throws PlanNotFoundException {
        Plan plan = planRepository.findById(id)
                .orElseThrow(PlanNotFoundException::new);
        plan.setImportante(importancia);
        return planRepository.save(plan);
    }

    @Override
    public int numResidentes(long id) throws PlanNotFoundException {
        Plan plan = planRepository.findById(id)
                .orElseThrow(PlanNotFoundException::new);
        return planRepository.numResidentes(id);
    }

}
