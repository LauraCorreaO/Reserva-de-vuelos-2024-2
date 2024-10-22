package com.udea.reservaVuelos.repository;
import com.udea.reservaVuelos.model.entities.ReservaPasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaPasajeroRepository extends JpaRepository<ReservaPasajero, Long>{
    List<ReservaPasajero> findByReserva_IdReservaAndPasajero_IdPasajero(Long idReserva, Long idPasajero);

    // Método para encontrar todos los pasajeros asociados a una reserva específica
    List<ReservaPasajero> findByReserva_IdReserva(Long idReserva);

    // Método para eliminar todos los pasajeros asociados a una reserva específica
    void deleteAllByReserva_IdReserva(Long idReserva);

    // Método para obtener una relación específica por ID de reserva y de pasajero
    ReservaPasajero findJustOneByReserva_IdReservaAndPasajero_IdPasajero(Long idReserva, Long idPasajero);
}





