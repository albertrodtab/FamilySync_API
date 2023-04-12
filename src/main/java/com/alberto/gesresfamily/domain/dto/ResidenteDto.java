package com.alberto.gesresfamily.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResidenteDto {

   /* Es una especie de clon del plan pero no tiene correspondencia con la base de datos solo sirve para definir
    como quiero mostrar mis datos de una forma acotada
    Así envio los campos del residente y un campo a mayores que me servirá para saber con que familiar está relacionado.
    */
    //debo indicarle en que formato voy a introducir las fechas

    @NotNull(message="El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @NotNull(message="los apellidos son obligatorios")
    @NotBlank(message = "el campo apellidos no puede estar vacío")
    private String apellidos;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "[0-9]{7,8}[A-Za-z]", message= "Escribe un dni Válido")
    private String dni;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;
    @NotBlank(message = "El campo sexo es obligatorio")
    @Pattern(regexp = "^[MF]$", message = "Debes introducir M o F")
    private String sexo;
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    private float saldo;
    private long centro;


}
