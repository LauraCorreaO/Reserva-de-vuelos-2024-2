package com.udea.reservaVuelos.repository;

import com.udea.reservaVuelos.model.entities.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
    @Query("SELECT p FROM Pasajero p WHERE p.numeroDocumento = :numeroDocumento")
    Optional<Pasajero> findByNumeroDocumento(@Param("numeroDocumento") String numeroDocumento);
    @Modifying
    @Query("UPDATE Pasajero p SET p.nombre = :nombre, p.apellido = :apellido, p.correo = :correo, p.telefono = :telefono WHERE p.numeroDocumento = :numeroDocumento")
    void updatePasajero(
            @Param("nombre") String nombre,
            @Param("apellido") String apellido,
            @Param("correo") String correo,
            @Param("telefono") String telefono);

}

