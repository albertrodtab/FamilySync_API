package com.alberto.gesresfamily.controller;

import com.alberto.gesresfamily.domain.Familiar;
import com.alberto.gesresfamily.domain.Residente;
import com.alberto.gesresfamily.domain.dto.RelacionDTO;
import com.alberto.gesresfamily.exception.*;
import com.alberto.gesresfamily.service.FamiliarService;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
public class FamiliarController {

    private final Logger logger = LoggerFactory.getLogger(FamiliarController.class);

    @Autowired
    private FamiliarService familiarService;

    @Autowired
    private ResidenteService residenteService;

    @PostMapping("/familiares")
    public ResponseEntity<Familiar> addFamiliar(@Valid @RequestBody Familiar familiar) {
        logger.info("Inicio addFamiliar");
        Familiar newFamiliar = familiarService.addFamiliar(familiar);
        logger.info("Fin addFamiliar");
        return ResponseEntity.status(HttpStatus.CREATED).body(newFamiliar);
    }

    /*
    * Relacionar un familiar con un residente
     */
    @PostMapping("/familiares/{familiarId}/residentes/{residenteId}")
    public ResponseEntity<Response> relacion(@RequestBody RelacionDTO relacionDTO)
            throws FamiliarNotFoundException, ResidenteNotFoundException {
        logger.info("Inicio relacion");
        Residente residente = residenteService.findResidente(relacionDTO.getResidenteId());
        logger.info("Residente encontrado " + residente.getId());
        Familiar familiar = familiarService.findFamiliar(relacionDTO.getFamiliarId());
        logger.info("Familiar encontrado " + familiar.getId());
        familiarService.addRelacion(residente, familiar);


        Response response = new Response("1", "Residente añadido al familiar " +
                relacionDTO.getFamiliarId());
        logger.info("Fin relacion");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/familiares/{id}")
    public ResponseEntity<Familiar> getFamiliar (@PathVariable long id) throws FamiliarNotFoundException {
        Familiar familiar = familiarService.findFamiliar(id);
        return ResponseEntity.ok(familiar);
    }

    @GetMapping("/familiares")
    public ResponseEntity<List<Familiar>> getFamiliares (
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "dni", required = false) String dni,
            @RequestParam(name = "telefono", required = false) String telefono,
            @RequestParam(name = "all", defaultValue = "true") boolean all){
        logger.info("Inicio getFamiliares");
        List<Familiar> familiares;

        if(all){
            logger.info("Muestra todos los familiares");
            familiares = familiarService.findAllFamiliares();
        } else {
            logger.info("Muestra centros que cumplen los parámetros de busqueda");
            familiares = familiarService.findAllFamiliares(nombre, dni, telefono);
        }
        logger.info("Fin getFamiliares");
        return ResponseEntity.ok(familiares);
    }



    @DeleteMapping("/familiares/{id}")
    public ResponseEntity<Void> removeFamiliar (@PathVariable long id) throws FamiliarNotFoundException{
        logger.info("Inicio removeFamiliar");
        Familiar familiar = familiarService.removeFamiliar(id);
        logger.info("Fin removeFamiliar");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/familiares/{id}")
    public ResponseEntity<Familiar> modifyFamiliar(@RequestBody Familiar familiar, @PathVariable long id) throws FamiliarNotFoundException{
        logger.info("Inicio modifyFamiliar");
        Familiar newFamiliar = familiarService.modifyFamiliar(id, familiar);
        logger.info("Fin modifyFamiliar");
        return ResponseEntity.status(HttpStatus.OK).body(newFamiliar);
    }

    // Cambiar el telefono de un familiar
    @PatchMapping("/familiar/{id}")
    public Familiar patchFamiliar (@PathVariable long id, @RequestBody String telefono) throws FamiliarNotFoundException {
        logger.info("Inicio PatchFamiliar " + id);
        Familiar familiar = familiarService.patchfamiliar(id, telefono);
        logger.info("Fin patchFamiliar " + id);
        return familiar;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException manve) {
        logger.info("400: Argument not valid");
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        logger.error(manve.getMessage(), manve);
        logger.error(Arrays.toString(manve.getStackTrace()));
        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException bre) {
        logger.info("400: Bad request");
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }


    @ExceptionHandler(FamiliarNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFamiliarNotFoundException(FamiliarNotFoundException fnfe) {
        logger.info("404: Familiar not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(fnfe.getMessage()));
    }

    @ExceptionHandler(ResidenteNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResidenteNotFoundException(ResidenteNotFoundException rnfe) {
        logger.info("404: Residente not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(rnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.info("500: Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.internalServerError(isee.getMessage()));
    }
}
