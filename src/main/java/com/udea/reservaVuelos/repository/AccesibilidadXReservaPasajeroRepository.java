package com.udea.reservaVuelos.repository;

import com.udea.reservaVuelos.model.entities.AccesibilidadXReservaPasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesibilidadXReservaPasajeroRepository extends JpaRepository<AccesibilidadXReservaPasajero, Long> {
    void deleteByReservaPasajero_IdReservaPasajero(Long idReservaPasajero); // De manera similar
}