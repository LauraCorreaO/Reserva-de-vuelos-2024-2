package com.udea.reservaVuelos.repository;

import com.udea.reservaVuelos.model.entities.AdicionXReservaPasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionXReservaPasajeroRepository extends JpaRepository<AdicionXReservaPasajero, Long> {
    void deleteByReservaPasajero_IdReservaPasajero(Long idReservaPasajero); // Aquí haces referencia a la propiedad idReservaPasajero
}
