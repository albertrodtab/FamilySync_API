package com.alberto.gesresfamily.controller;

import com.alberto.gesresfamily.domain.Centro;
import com.alberto.gesresfamily.exception.*;
import com.alberto.gesresfamily.service.CentroService;
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
public class CentroController {

    private final Logger logger = LoggerFactory.getLogger(CentroController.class);

    @Autowired
    private CentroService centroService;


    @PostMapping("/centros")
    public ResponseEntity<Centro> addCentro (@Valid @RequestBody Centro centro) {
        logger.info("Inicio addCentro");
        Centro newCentro = centroService.addCentro(centro);
        logger.info("Fin addCentro");
        return ResponseEntity.status(HttpStatus.CREATED).body(newCentro);

    }

    @GetMapping("/centros/{id}")
    public ResponseEntity<Centro> getCentro (@PathVariable long id) throws CentroNotFoundException {
        logger.info("Inicio getCentro");
        Centro centro = centroService.findById(id);
        logger.info("Fin getCentro");
        return ResponseEntity.ok(centro);
    }

    @GetMapping("/centros")
    public ResponseEntity<List<Centro>> getCentros(
            @RequestParam(name = "nombre", required = false) String nombre,
            @RequestParam(name = "numRegistro", required = false) String numRegistro,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "all", defaultValue = "true") boolean all){
        logger.info("Inicio getCentros");
        List<Centro> centros;

        if(all){
            logger.info("Muestra todos los centros");
            centros = centroService.findAll();
        } else {
            logger.info("Muestra centros que cumplen los parametros");
            centros = centroService.findAllCentros(nombre, numRegistro, email);
        }
        logger.info("Fin getCentros");
        return ResponseEntity.ok(centros);
    }

    @DeleteMapping("/centros/{id}")
    public ResponseEntity<Void> removeCentro(@PathVariable long id) throws CentroNotFoundException {
        logger.info("Inicio removeCentro");
        centroService.removeCentro(id);
        logger.info("Fin removeCentro");
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/centros/{id}")
    public ResponseEntity<Centro> modifyCentro(@RequestBody Centro centro, @PathVariable long id)
            throws CentroNotFoundException{
        logger.info("Inicio modifyCentro");
        Centro newCentro = centroService.modifyCentro(id, centro);
        logger.info("Fin modifyCentro");
        return ResponseEntity.status(HttpStatus.OK).body(newCentro);
    }

    // Cambiar el telefono de un centro
    @PatchMapping("/centro/{id}")
    public Centro patchCentro (@PathVariable long id, @RequestBody String telefono) throws CentroNotFoundException {
        logger.info("Start Patchcentro " + id);
        Centro centro = centroService.patchCentro(id, telefono);
        logger.info("End patchCentro " + id);
        return centro;
    }

    // Contar los residentes totales de un centro. SQL
    @GetMapping("/centro/{id}/numResidentes")
    public int numResidentes(@PathVariable long id) throws CentroNotFoundException {
        logger.info("Inicio numResidentes " + id);
        int residentes = centroService.numResidentes(id);
        logger.info("rin numResidentes " + id);
        return residentes;
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
        logger.error(bre.getMessage(), bre);
        logger.error(Arrays.toString(bre.getStackTrace()));
        return ResponseEntity.badRequest().body(ErrorResponse.badRequest(bre.getMessage()));
    }

    @ExceptionHandler(CentroNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCentroNotFoundException(CentroNotFoundException cnfe) {
        logger.error(cnfe.getMessage(), cnfe);
        logger.error(Arrays.toString(cnfe.getStackTrace()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.resourceNotFound(cnfe.getMessage()));
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException isee) {
        logger.error(isee.getMessage(), isee);
        logger.error(Arrays.toString(isee.getStackTrace()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.internalServerError(isee.getMessage()));
    }

}
