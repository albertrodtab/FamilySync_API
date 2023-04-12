package com.alberto.gesresfamily.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PlanDto {

   /* Es una especie de clon del plan pero no tiene correspondencia con la base de datos solo sirve para definir
    como quiero mostrar mis datos de una forma acotada
    Así envio los campos del plan y dos campos a mayores que me servirán para saber con que residente y profesional está relacionado.
    */
    //debo indicarle en que formato voy a introducir las fechas
    @NotNull(message="El nombre del centro es obligatorio")
    @NotBlank(message = "El nombre del centro no puede estar vacío")
    private String nombrePlan;
    private String terapia;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaInicio;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaFin;
    private Boolean importante;
    @Length(min= 6)
    private String descripcion;
    private long profesional;


}
