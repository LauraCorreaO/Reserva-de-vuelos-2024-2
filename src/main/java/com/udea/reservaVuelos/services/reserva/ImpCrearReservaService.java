package com.udea.reservaVuelos.services.reserva;
import com.udea.reservaVuelos.model.DTOs.PasajeroDTO;
import com.udea.reservaVuelos.model.DTOs.ReservaDTO;
import com.udea.reservaVuelos.model.entities.*;
import com.udea.reservaVuelos.repository.*;
import com.udea.reservaVuelos.services.pasajero.CrearPasajeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImpCrearReservaService implements CrearReservaService{
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CrearPasajeroService crearPasajeroService;

    @Autowired
    private ReservaPasajeroRepository reservaPasajeroRepository;

    @Autowired
    private AccesibilidadRepository accesibilidadRepository;

    @Autowired
    private AdicionRepository adicionRepository;
    @Autowired
    private AccesibilidadXReservaPasajeroRepository accesibilidadXReservaPasajeroRepository;

    @Autowired
    private AdicionXReservaPasajeroRepository adicionXReservaPasajeroRepository;

    @Transactional
    @Override
    public Reserva crearReserva(ReservaDTO reservaDTO) {
        if (reservaDTO == null || reservaDTO.getPasajeros() == null || reservaDTO.getPasajeros().isEmpty()) {
            throw new IllegalArgumentException("La reserva debe incluir al menos un pasajero.");
        }

        // Crear la reserva
        Reserva reserva = new Reserva();
        reserva.setNumeroReserva(reservaDTO.getNumeroReserva());
        reserva.setFechaReserva(reservaDTO.getFechaReserva());
        reserva.setNumeroPasajeros(reservaDTO.getNumeroPasajeros());
        reserva.setIdVueloIda(reservaDTO.getIdVueloIda());
        reserva.setIdVueloVuelta(reservaDTO.getIdVueloVuelta());
        Reserva reservaGuardada = reservaRepository.save(reserva);

        // Guardar los pasajeros y sus relaciones con accesibilidades y adiciones
        for (PasajeroDTO pasajeroDTO : reservaDTO.getPasajeros()) {
            Pasajero pasajeroGuardado = crearPasajeroService.crearPasajero(pasajeroDTO);

            // Crear la relación ReservaPasajero
            ReservaPasajero reservaPasajero = new ReservaPasajero();
            reservaPasajero.setReserva(reservaGuardada);
            reservaPasajero.setPasajero(pasajeroGuardado);
           // reservaPasajero.setAccesibilidad(pasajeroDTO.getAccesibilidad());
            reservaPasajero.setEquipajeAdicional(pasajeroDTO.getEquipajeAdicional());
            //reservaPasajero.setAdiciones(pasajeroDTO.getAdiciones());
            reservaPasajero.setAsientoElegido(pasajeroDTO.getAsientoElegido());
            reservaPasajeroRepository.save(reservaPasajero);

            // Establecer accesibilidades en el objeto ReservaPasajero como true o false
            reservaPasajero.setAccesibilidad(!pasajeroDTO.getAccesibilidadesIds().isEmpty());
            reservaPasajero.setAdiciones(!pasajeroDTO.getAdicionesIds().isEmpty());

            // Guardar las accesibilidades del pasajero
            // Agregar accesibilidades si se proporcionaron IDs
            for (Long idAccesibilidad : pasajeroDTO.getAccesibilidadesIds()) {
                Accesibilidad accesibilidad = accesibilidadRepository.findById(idAccesibilidad)
                        .orElseThrow(() -> new IllegalArgumentException("Accesibilidad no encontrada: " + idAccesibilidad));

                AccesibilidadXReservaPasajero accesibilidadXReserva = new AccesibilidadXReservaPasajero();
                accesibilidadXReserva.setAccesibilidad(accesibilidad);
                accesibilidadXReserva.setReservaPasajero(reservaPasajero);
                accesibilidadXReservaPasajeroRepository.save(accesibilidadXReserva);
            }

            // Agregar adiciones si se proporcionaron IDs
            for (Long idAdicion : pasajeroDTO.getAdicionesIds()) {
                Adicion adicion = adicionRepository.findById(idAdicion)
                        .orElseThrow(() -> new IllegalArgumentException("Adición no encontrada: " + idAdicion));

                AdicionXReservaPasajero adicionXReserva = new AdicionXReservaPasajero();
                adicionXReserva.setAdicion(adicion);
                adicionXReserva.setReservaPasajero(reservaPasajero);
                adicionXReserva.setCostoExtra(adicion.getCostoExtra()); // Costo extra asociado a la adición
                adicionXReservaPasajeroRepository.save(adicionXReserva);
            }
        }

        return reservaGuardada;
    }
}
