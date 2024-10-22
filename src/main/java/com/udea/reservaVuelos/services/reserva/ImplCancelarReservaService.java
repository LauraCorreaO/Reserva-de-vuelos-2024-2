package com.udea.reservaVuelos.services.reserva;

import com.udea.reservaVuelos.model.entities.EstadoReserva;
import com.udea.reservaVuelos.model.entities.Reserva;
import com.udea.reservaVuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ImplCancelarReservaService implements CancelarReservaServiceInterface {
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva cancelarReserva(Long idReserva) throws Exception {
        Optional<Reserva> optionalReserva = reservaRepository.findById(idReserva);

        if (!optionalReserva.isPresent()) {
            throw new ChangeSetPersister.NotFoundException();
        }

        Reserva reserva = optionalReserva.get();

        // Cambiar el estado a CANCELADA
        reserva.setEstado(EstadoReserva.CANCELADA);
        reserva.setFechaCancelacion(LocalDateTime.now());

        reservaRepository.save(reserva);

        return reserva;
    }
}
