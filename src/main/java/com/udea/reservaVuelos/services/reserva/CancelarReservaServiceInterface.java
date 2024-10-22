package com.udea.reservaVuelos.services.reserva;

import com.udea.reservaVuelos.model.entities.Reserva;

public interface CancelarReservaServiceInterface {
    Reserva cancelarReserva(Long idReserva) throws Exception;
}
