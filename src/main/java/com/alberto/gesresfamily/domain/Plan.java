package com.alberto.gesresfamily.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "planes")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "nombre_plan")
    @NotNull(message="El nombre del centro es obligatorio")
    @NotBlank(message = "El nombre del centro no puede estar vacío")
    private String nombrePlan;
    @Column
    private String terapia;
    @Column (name = "fecha_inicio")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaInicio;
    @Column (name = "fecha_fin")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaFin;
    @Column
    private Boolean importante;
    @Column
    @Length(min= 6)
    private String descripcion;


    //Debemos establecer como se relaciona con los residentes
    //Indicamos el tipo de relación, 1 plan tendrá asociado uno o n residentes, y un residente puede tener 1 o n planes
    // por eso es ManyToMany porque plan es el lado n
    //CascadeType.Persist y Merge para que no permita añadir dos veces la misma relación y permita borrar si eliminar el otro elemento asociado.
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    //para evitar el bucle de que asocie residentes completos a planes y sea algo infinito añadimos @JsonBackReference
    @JsonBackReference(value = "planResidente")
    private List<Residente> residentes;

    //Debemos establecer como se relaciona con los profesionales
    //Indicamos el tipo de relación, 1 plan solo tendrán asociado un profesional, pero un profesional puede tener n planes
    // por eso es ManyToOne porque plan es el lado n
    @ManyToOne
    //indica la columa por la que estaran relacionadas que tendra la clave ajena profesional_id
    @JoinColumn(name = "profesional_id")
    //para evitar el bucle de que asocie profesionales completos a planes y sea algo infinito añadimos @JsonBackReference
    @JsonBackReference(value = "planProfesional")
    private Profesional profesional;
}
