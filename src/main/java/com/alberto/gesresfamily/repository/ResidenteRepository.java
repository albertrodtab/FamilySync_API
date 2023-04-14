package com.alberto.gesresfamily.repository;

import com.alberto.gesresfamily.domain.Residente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidenteRepository extends CrudRepository<Residente, Long> {

    Residente findAllById(long id);

    List<Residente> findAll();

    List<Residente> findAllResidentesById(long id);

    List<Residente> findAllResidentesByApellidosOrNombreOrDni(String apellidos, String nombre, String dni);

    // mostrar los residentes que su saldo sea menor que

    //quiero mostrar solo el nombre y apellido y la columna de saldo, pero no fui capaz de hacerlo.
    @Query(value = "SELECT * FROM residentes WHERE saldo <= ?1" , nativeQuery = true)
    List<Residente> saldoMenor (float saldo);
}
