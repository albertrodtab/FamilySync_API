package com.alberto.gesresfamily.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "residentes")
public class Residente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotNull(message="El nombre es obligatorio")
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    @Column
    @NotNull(message="los apellidos son obligatorios")
    @NotBlank(message = "el campo apellidos no puede estar vacío")
    private String apellidos;
    @Column
    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "[0-9]{7,8}[A-Za-z]", message= "Escribe un dni Válido")
    private String dni;
    @Column (name = "fecha_nacimiento")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;
    @Column
    @NotBlank(message = "El campo sexo es obligatorio")
    @Pattern(regexp = "^[MF]$")
    private String sexo;
    @Column
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    private float saldo;

    @ManyToOne
    @JoinColumn(name = "centro_id")
    @JsonBackReference(value = "residenteCentro")
    private Centro centro;

    @ManyToMany (mappedBy = "residentes")
    //para evitar serializaciones pongo el backreference en el otro lado Pero tengo que mejorarlo todavía.
    @JsonBackReference (value = "ResidenteFamiliar")
    private List<Familiar> familiares;

    //como ya está relacionado en el otro lado aquí solo indico por que objeto tiene mapearse
    // 1 residente varios planes, pero 1 plan solo 1 residente
    @ManyToMany(mappedBy = "residentes")
    @JsonBackReference(value = "residentePlan")
    private List<Plan> planes;

}
