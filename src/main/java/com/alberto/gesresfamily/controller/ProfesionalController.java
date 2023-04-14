package com.alberto.gesresfamily.controller;

import com.alberto.gesresfamily.domain.Profesional;
import com.alberto.gesresfamily.exception.*;
import com.alberto.gesresfamily.service.ProfesionalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ProfesionalController {

    private final Logger logger = LoggerFactory.getLogger(ProfesionalController.class);

    @Autowired
    private ProfesionalService profesionalService;


    @PostMapping("/profesionales")
    public ResponseEntity<Profesional> addProfesional (@Valid @RequestBody Profesional profesional){
        logger.info("Inicio addFamiliar");
        Profesional newProfesional = profesionalService.addProfesional(profesional);
        logger.info("Fin addFamiliar");
        return ResponseEntity.status(HttpStatus.CREATED).body(newProfesional);
    }

    @GetMapping("/profesional/{id}")
    public ResponseEntity<Profesional> getProfesional (@PathVariable long id) throws ProfesionalNotFoundException {
        Profesional profesional = profesionalService.findProfesional(id);
        return ResponseEntity.ok(profesional);
    }

    @GetMapping("/profesionales")
    public ResponseEntity<List<Profesional>> getProfesionales (
            @RequestParam(name = "apellidos", required = false, defaultValue = "") String apellidos,
            @RequestParam(name = "nombre", required = false, defaultValue = "") String nombre,
            @RequestParam(name = "dni", required = false, defaultValue = "") String dni){

        logger.info("Inicio getProfesionales");
        List<Profesional> profesionales;

        if(apellidos.equals("") && nombre.equals("") && dni.equals("")){
            logger.info("Muestra todos los profesionales");
            profesionales = profesionalService.findAllProfesionales();
        } else {
            logger.info("Muestra los profesionales que cumplen alguno de los criterios");
            profesionales = profesionalService.findAllProfesionales(apellidos, nombre, dni);
        }
        logger.info("Fin getProfesinales");
        return ResponseEntity.ok(profesionales);
    }

    @DeleteMapping("/profesional/{id}")
    public ResponseEntity<Void> removeProfesional (@PathVariable long id) throws ProfesionalNotFoundException {
        logger.info("Inicio removeFamiliar");
        Profesional profesional = profesionalService.removeProfesional(id);
        logger.info("Fin removeFamiliar");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/profesional/{id}")
    public ResponseEntity<Profesional> modifyProfesional(@RequestBody Profesional profesional, @PathVariable long id) throws ProfesionalNotFoundException {
        logger.info("Inicio modifyFamiliar");
        Profesional newProfesional = profesionalService.modifyProfesional(id, profesional);
        logger.info("Fin modifyFamiliar");
        return ResponseEntity.status(HttpStatus.OK).body(newProfesional);
    }

    // Cambiar la categoria de un profesional
    @PatchMapping("/profesional/{id}")
    public Profesional patchProfesional (@PathVariable long id, @RequestBody String categoria) throws ProfesionalNotFoundException {
        logger.info("Inicio PatchProfesional " + id);
        Profesional profesional = profesionalService.patchProfesional(id, categoria);
        logger.info("Fin patchprofesional " + id);
        return profesional;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        logger.info("400: Argument not valid");
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        logger.info("400: Bad request");
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }


    @ExceptionHandler(ProfesionalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProfesionalNotFoundException(ProfesionalNotFoundException pnfe) {
        logger.info("404: Profesional not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(pnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.internalServerError(isee.getMessage()));
    }


}
