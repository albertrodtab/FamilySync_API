package com.alberto.gesresfamily.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Usuario {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = " idusuario " )
    private Long id ;
    private String nombre ;
    private String email ;
    private String password;
}
