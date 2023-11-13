package com.alberto.gesresfamily.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "familiares")
public class Familiar {

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
    @Column(name = "fecha_nacimiento")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento;
    @Column
    private String telefono;
    @Column
    @NotBlank(message="El mail no puede estar vacío")
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message= "Escribe un correo válido, ejemplo:" +
            "user.name@domain.com, username@domain.com")
    private String email;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    @JoinTable(
            name = "rel_fam_res",
            joinColumns = @JoinColumn(name = "familiar_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name="residente_id", nullable = false)
    )
    //para evitar serializaciones Pero tengo que mejorarlo todavía.
    @JsonBackReference(value = "familiarResidente")
    private List<Residente> residentes;

    @OneToMany(mappedBy = "familiar")
    private List<Foto> fotos;
}
