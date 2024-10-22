package com.udea.reservaVuelos.services.reserva;

import com.udea.reservaVuelos.model.entities.Pasajero;
import com.udea.reservaVuelos.model.entities.Reserva;
import com.udea.reservaVuelos.model.entities.ReservaPasajero;
import com.udea.reservaVuelos.repository.PasajeroRepository;
import com.udea.reservaVuelos.repository.ReservaPasajeroRepository;
import com.udea.reservaVuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplObtenerReservaService implements ObtenerReservaServiceInterface {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Autowired
    private ReservaPasajeroRepository reservaPasajeroRepository;

    @Override
    public List<ReservaPasajero> obtenerReservaPorNumeroReservaYNumeroDocumento(String numeroReserva, String numeroDocumento) {
        try {
        // Buscar reserva por número de reserva
        Optional<Reserva> reservaOpt = reservaRepository.findByNumeroReserva(numeroReserva);
        if (reservaOpt.isEmpty()) {
            throw new RuntimeException("Reserva no encontrada para el número de reserva proporcionado");
        }
        Long idReserva = reservaOpt.get().getIdReserva(); // Asegúrate de que el método correcto sea getId()

        // Buscar pasajero por número de documento
        Optional<Pasajero> pasajeroOpt = pasajeroRepository.findByNumeroDocumento(numeroDocumento);
        if (pasajeroOpt.isEmpty()) {
            throw new RuntimeException("Pasajero no encontrado para el número de documento proporcionado");
        }
        Long idPasajero = pasajeroOpt.get().getIdPasajero(); // Asegúrate de que el método correcto sea getId()

        // Buscar en ReservaPasajero usando los IDs obtenidos
        List<ReservaPasajero> reservasPasajeros = reservaPasajeroRepository.findByReserva_IdReservaAndPasajero_IdPasajero(idReserva, idPasajero);
        if (reservasPasajeros.isEmpty()) {
            throw new RuntimeException("No se encontraron datos de reserva para el pasajero con los IDs proporcionados");
        }
        return reservasPasajeros;
    } catch (Exception e) {
        // Registrar el error (puedes usar un logger aquí)
        throw new RuntimeException("Error al obtener la reserva: " + e.getMessage(), e);
    }
}}
