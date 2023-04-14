package com.alberto.gesresfamily.controller;

import com.alberto.gesresfamily.domain.Centro;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.ResidenteDto;
import com.alberto.gesresfamily.exception.*;
import com.alberto.gesresfamily.service.CentroService;
import com.alberto.gesresfamily.service.ResidenteService;
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
public class ResidenteController {

    private final Logger logger = LoggerFactory.getLogger(ResidenteController.class);

    @Autowired
    private ResidenteService residenteService;

    @Autowired
    private CentroService centroService;

    //Añadir Residente
    @PostMapping("/residentes")
    public Residente addResidenteCentro (@Valid @RequestBody ResidenteDto residenteDto) throws CentroNotFoundException {
        logger.info("Inicio addResidenteCentro");
        Residente newResidente = residenteService.addResidenteCentro(residenteDto);
        logger.info("Fin addResidenteCentro");
        return newResidente;
    }
    //Consultar residente por id
    @GetMapping("/residente/{id}")
    public ResponseEntity<Residente> getResidente (@PathVariable long id) throws ResidenteNotFoundException {
        Residente residente = residenteService.findResidente(id);
        return ResponseEntity.ok(residente);
    }
    //Consultar residentes
    @GetMapping("/residentes")
    public ResponseEntity<List<Residente>> getResidentes(
            @RequestParam(name = "apellidos", defaultValue = "") String apellidos,
            @RequestParam(name = "nombre", required = false,defaultValue = "") String nombre,
            @RequestParam(name = "dni", required = false, defaultValue = "") String dni){

        logger.info("Inicio getResidentes");
        List<Residente> residentes;

        if(apellidos.equals("") && nombre.equals("") && dni.equals("")){
            logger.info("Muestra todos los residentes");
            residentes = residenteService.findAllResidentes();
        } else {
            logger.info("Muestra los residentes que cumplen los parámetros de búsqueda");
            residentes = residenteService.findAllResidentes(apellidos, nombre, dni);
        }
        return ResponseEntity.ok(residentes);
    }
    //Eliminar Residente
    @DeleteMapping("/residente/{id}")
    public ResponseEntity<Void> removeResidente (@PathVariable long id) throws ResidenteNotFoundException{
        logger.info("Inicio removeResidente");
        Residente residente = residenteService.removeResidente(id);
        logger.info("Fin removeResidente");
        return ResponseEntity.noContent().build();
    }

    //Modificar residente
    @PutMapping("/residente/{id}")
    public ResponseEntity<Residente> modifyResidente(@RequestBody Residente residente, @PathVariable long id) throws ResidenteNotFoundException{
        logger.info("Inicio modifyResidente");
        Residente newResidente = residenteService.modifyResidente(id, residente);
        logger.info("Fin modifyResidente");
        return ResponseEntity.status(HttpStatus.OK).body(newResidente);
    }

    // Actualizar el saldo de un residente
    @PatchMapping("/residente/{id}")
    public Residente patchResidente (@PathVariable long id, @RequestBody float saldo) throws ResidenteNotFoundException {
        logger.info("Start PatchResidente " + id);
        Residente residente = residenteService.patchProfesional(id, saldo);
        logger.info("End patchResidente " + id);
        return residente;
    }

    // Consultar los residentes con una saldo inferior a una cifra determinada. SQL
    @GetMapping("/residentes/{saldo}")
    public List<Residente> saldoMenor (@PathVariable float saldo) {
        logger.info("Inicio saldoMenor " + saldo);
        List<Residente> residentes = null;
        residentes = residenteService.saldoMenor(saldo);
        logger.info("Fin SaldoMenor " + saldo);
        return residentes;
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

    @ExceptionHandler(ResidenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResidenteNotFoundException(ResidenteNotFoundException rnfe) {
        logger.info("404: Residente not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(rnfe.getMessage()));
    }

    @ExceptionHandler(CentroNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCentroNotFoundException(CentroNotFoundException cnfe) {
        logger.info("404: Centro not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(cnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
