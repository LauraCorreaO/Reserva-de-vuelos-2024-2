package com.udea.reservaVuelos.resolvers;

import com.udea.reservaVuelos.handlers.GlobalExceptionHandler;
import com.udea.reservaVuelos.model.DTOs.ReservaDTO;
import com.udea.reservaVuelos.model.entities.Reserva;
import com.udea.reservaVuelos.model.entities.ReservaPasajero;
import com.udea.reservaVuelos.services.reserva.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservaResolver {

    private final CrearReservaService crearReservaService;
    private static final Logger logger = LoggerFactory.getLogger(ReservaResolver.class);
    private final ObtenerReservaServiceInterface obtenerReservaService;
    private final CancelarReservaServiceInterface cancelarReservaService;
    private final ModificarReservaServiceInterface modificarReservaService;

    public ReservaResolver(CrearReservaService crearReservaService, ObtenerReservaServiceInterface obtenerReservaService, CancelarReservaServiceInterface cancelarReservaService, ModificarReservaServiceInterface modificarReservaService) {
        this.crearReservaService = crearReservaService;
        this.obtenerReservaService = obtenerReservaService;
        this.cancelarReservaService = cancelarReservaService;
        this.modificarReservaService = modificarReservaService;
    }

    @QueryMapping
    public List<ReservaPasajero> obtenerReserva(@Argument String numeroReserva, @Argument String numeroDocumento) {
        return obtenerReservaService.obtenerReservaPorNumeroReservaYNumeroDocumento(numeroReserva, numeroDocumento);
    }

    @MutationMapping
    public Reserva crearReserva(@Argument("input") ReservaDTO input) {
            return crearReservaService.crearReserva(input);

    }

    @MutationMapping
    public Reserva cancelarReserva(@Argument Long idReserva) {
        try {
            return cancelarReservaService.cancelarReserva(idReserva);
        } catch (Exception e) {
            logger.error("Error al cancelar la reserva: " + e.getMessage(), e);
            throw new RuntimeException("No se pudo cancelar la reserva. Intente nuevamente.");
        }
    }

    @MutationMapping
    public Reserva modificarReserva(@Argument Long idReserva, @Argument("input") ReservaDTO reservaDTO) {
        try {
            return modificarReservaService.modificarReserva(idReserva, reservaDTO);
        } catch (GlobalExceptionHandler.NotFoundException e) {
            logger.error("Error al modificar la reserva: " + e.getMessage(), e);
            throw new RuntimeException("Reserva no encontrada. " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error al modificar la reserva: " + e.getMessage(), e);
            throw new RuntimeException("No se pudo modificar la reserva. Intente nuevamente.");
        }
    }

}