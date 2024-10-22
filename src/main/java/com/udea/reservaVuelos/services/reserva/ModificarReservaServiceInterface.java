package com.udea.reservaVuelos.services.reserva;

import com.udea.reservaVuelos.model.DTOs.ReservaDTO;
import com.udea.reservaVuelos.model.entities.Reserva;

public interface ModificarReservaServiceInterface {
    Reserva modificarReserva(Long idReserva, ReservaDTO reservaDTO) throws Exception;
}
