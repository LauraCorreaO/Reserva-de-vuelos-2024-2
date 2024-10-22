package com.udea.reservaVuelos.services.reserva;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.reservaVuelos.model.DTOs.PasajeroDTO;
import com.udea.reservaVuelos.model.DTOs.ReservaDTO;
import com.udea.reservaVuelos.model.entities.*;
import com.udea.reservaVuelos.repository.*;
import com.udea.reservaVuelos.resolvers.ReservaResolver;
import com.udea.reservaVuelos.services.pasajero.CrearPasajeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.reservaVuelos.handlers.GlobalExceptionHandler.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImplModificarReservaService implements ModificarReservaServiceInterface{
    private static final Logger logger = LoggerFactory.getLogger(ImplModificarReservaService.class);
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private PasajeroRepository pasajeroRepository;
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
    private ObjectMapper objectMapper = new ObjectMapper();
    public Reserva modificarReserva(Long idReserva, ReservaDTO reservaDTO) throws Exception {
        try{
            String json = objectMapper.writeValueAsString(reservaDTO);
            this.logger.info("Info inicial: {}" ,json);
            if (reservaDTO == null) {
                // Lanzar una excepción si la reserva no se encuentra
                throw new NotFoundException("Reserva no encontrada con el número: " + idReserva);
            }

            // Buscar la reserva existente
            Reserva reservaExistente = reservaRepository.findById(idReserva)
                    .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada: " + idReserva));

            String json2 = objectMapper.writeValueAsString(reservaExistente);
            this.logger.info("Reserva existente {}" , json2);

            // Actualizar los campos de la reserva
            reservaExistente.setNumeroPasajeros(reservaDTO.getNumeroPasajeros());
            reservaExistente.setIdVueloIda(reservaDTO.getIdVueloIda());
            reservaExistente.setIdVueloVuelta(reservaDTO.getIdVueloVuelta());
            reservaExistente.setEstado(reservaDTO.getEstado());
            reservaRepository.save(reservaExistente);

            List<ReservaPasajero> pasajerosExistentes = reservaPasajeroRepository.findByReserva_IdReserva(reservaExistente.getIdReserva());

            // Crear un conjunto para almacenar IDs de pasajeros existentes
            Set<Long> idsPasajerosExistentes = pasajerosExistentes.stream()
                    .map(p -> p.getPasajero().getIdPasajero())
                    .collect(Collectors.toSet());

            String json3 = objectMapper.writeValueAsString(idsPasajerosExistentes);
            this.logger.info("id de pasajeros existentes: {}" , json3);

            // Crear un conjunto para almacenar los correos de los pasajeros nuevos
            Set<String> correosPasajerosNuevos = reservaDTO.getPasajeros().stream()
                    .map(PasajeroDTO::getCorreo)
                    .collect(Collectors.toSet());

            String json4 = objectMapper.writeValueAsString(correosPasajerosNuevos);
            this.logger.info("correos pasajeros: {}" , json4);
            for (PasajeroDTO pasajeroDTO : reservaDTO.getPasajeros()) {
                Optional<Pasajero> optionalPasajero = pasajeroRepository.findByNumeroDocumento(pasajeroDTO.getNumeroDocumento());
                String jsonP = objectMapper.writeValueAsString(optionalPasajero);
                this.logger.info("variable optionalPasajero {}" , jsonP);
                if (optionalPasajero.isPresent()) {
                    // Actualizar el pasajero existente
                    String jsonPdto = objectMapper.writeValueAsString(pasajeroDTO);
                    this.logger.info("pasajeroDTO {}" , jsonPdto);
                    actualizarPasajero(idReserva, pasajeroDTO);
                } else {
                    // Crear un nuevo pasajero
                    crearPasajero(reservaExistente, pasajeroDTO);
                }
            }
            this.logger.info("id de pasajeros nuevos {}" , json4);
            // Actualizar o agregar pasajeros
        /* for (PasajeroDTO pasajeroDTO : reservaDTO.getPasajeros()) {
            this.logger.info( "estando dentro del for,  pasajero a comparar pasajeroDTO",String.valueOf(pasajeroDTO));
            if (idsPasajerosExistentes.contains(pasajeroDTO.getId())) {
                // Actualizar los datos del pasajero existente
                actualizarPasajero(idReserva, pasajeroDTO);
            } else {
                // Crear un nuevo pasajero
                crearPasajero(reservaExistente, pasajeroDTO);
            }
        }*/

           /* for (PasajeroDTO pasajeroDTO : reservaDTO.getPasajeros()) {
                String json5 = objectMapper.writeValueAsString(pasajeroDTO);
                this.logger.info("estando dentro del for,  pasajero a comparar pasajeroDTO {}", json5);

                // Verifica si el pasajero ya existe
                Optional<Pasajero> optionalPasajero = pasajeroRepository.findByCorreo(pasajeroDTO.getCorreo());
                if (optionalPasajero.isPresent()) {
                    // Si ya existe, crea un PasajeroDTO a partir del Pasajero encontrado
                    Pasajero pasajeroExistente = optionalPasajero.get();

                    // Crear un nuevo PasajeroDTO
                    PasajeroDTO pasajeroDTOExistente = new PasajeroDTO();
                    pasajeroDTOExistente.setId(pasajeroExistente.getIdPasajero());
                    pasajeroDTOExistente.setNombre(pasajeroExistente.getNombre());
                    pasajeroDTOExistente.setApellido(pasajeroExistente.getApellido());
                    pasajeroDTOExistente.setCorreo(pasajeroExistente.getCorreo());
                    pasajeroDTOExistente.setNumeroDocumento(pasajeroExistente.getNumeroDocumento());
                    pasajeroDTOExistente.setTelefono(pasajeroExistente.getTelefono());
                    pasajeroDTOExistente.setTipoDocumento(pasajeroExistente.getTipoDocumento());

                    // Actualiza el pasajero existente en la reserva
                    actualizarPasajero(idReserva, pasajeroDTOExistente);
                } else {
                    // Crear un nuevo pasajero
                    crearPasajero(reservaExistente, pasajeroDTO);
                }
            }*/

            // Eliminar pasajeros que ya no están en la reserva
            eliminarPasajeros(pasajerosExistentes, correosPasajerosNuevos);

            return reservaExistente;
        } catch (Exception e){
            logger.error("Error al modificar la reserva: ", e);
            throw new RuntimeException("No se pudo modificar la reserva. Intente nuevamente.");
        }

    }

    private void actualizarPasajero(Long idReserva, PasajeroDTO pasajeroDTO) throws Exception {
        String jsonR = objectMapper.writeValueAsString(pasajeroDTO);
        this.logger.info("hola, entre {}", idReserva, jsonR);
        // Buscar el pasajero por ID
        Optional<Pasajero> optionalPasajero = pasajeroRepository.findById(pasajeroDTO.getId());
        String jsonoptionalPasajero = objectMapper.writeValueAsString(optionalPasajero);
        this.logger.info("hola, entre {}", idReserva, jsonoptionalPasajero);

        if (!optionalPasajero.isPresent()) {
            throw new NotFoundException("Pasajero no encontrado con ID: " + pasajeroDTO.getId());
        }
        String jsonP = objectMapper.writeValueAsString(optionalPasajero);
        this.logger.info("variable optionalPasajero dentro de actualizar metodo {}" , jsonP);

        pasajeroRepository.updatePasajero(
                pasajeroDTO.getNombre(),
                pasajeroDTO.getApellido(),
                pasajeroDTO.getCorreo(),
                pasajeroDTO.getTelefono()
        );

        ReservaPasajero reservaPasajero = reservaPasajeroRepository.findJustOneByReserva_IdReservaAndPasajero_IdPasajero(idReserva, pasajeroDTO.getId());
        String jsonReservaPasajero = objectMapper.writeValueAsString(optionalPasajero);
        this.logger.info("variable reservaPasajero {}" , jsonReservaPasajero);

        if (reservaPasajero == null) {
            throw new NotFoundException("ReservaPasajero no encontrado con ID de Reserva: " + idReserva + " y ID de Pasajero: " + pasajeroDTO.getId());
        }


        actualizarAccesibilidades(reservaPasajero, pasajeroDTO);
        actualizarAdiciones(reservaPasajero, pasajeroDTO);

        // Actualizar los campos específicos de ReservaPasajero si es necesario
        reservaPasajero.setAccesibilidad(pasajeroDTO.getAccesibilidad());
        reservaPasajero.setEquipajeAdicional(pasajeroDTO.getEquipajeAdicional());
        reservaPasajero.setAdiciones(pasajeroDTO.getAdiciones());
        reservaPasajero.setAsientoElegido(pasajeroDTO.getAsientoElegido());

        // Guardar cambios en ReservaPasajero
        reservaPasajeroRepository.save(reservaPasajero);
    }

    private void crearPasajero(Reserva reservaExistente, PasajeroDTO pasajeroDTO) {
        // Lógica para crear un nuevo pasajero
        Pasajero pasajeroGuardado = crearPasajeroService.crearPasajero(pasajeroDTO); // Crear pasajero

        ReservaPasajero reservaPasajero = new ReservaPasajero();
        reservaPasajero.setReserva(reservaExistente);
        reservaPasajero.setPasajero(pasajeroGuardado);
        reservaPasajero.setAccesibilidad(pasajeroDTO.getAccesibilidad());
        reservaPasajero.setEquipajeAdicional(pasajeroDTO.getEquipajeAdicional());
        reservaPasajero.setAdiciones(pasajeroDTO.getAdiciones());
        reservaPasajero.setAsientoElegido(pasajeroDTO.getAsientoElegido());

        reservaPasajeroRepository.save(reservaPasajero);
    }

    private void actualizarAccesibilidades(ReservaPasajero reservaPasajero, PasajeroDTO pasajeroDTO) {
        // Comprobar si hay accesibilidades que actualizar
        if (pasajeroDTO.getAccesibilidadesIds() != null && !pasajeroDTO.getAccesibilidadesIds().isEmpty()) {
            // Eliminar accesibilidades actuales asociadas a la reserva del pasajero
            accesibilidadXReservaPasajeroRepository.deleteByReservaPasajero_IdReservaPasajero(reservaPasajero.getIdReservaPasajero());

            // Agregar las nuevas accesibilidades desde PasajeroDTO
            pasajeroDTO.getAccesibilidadesIds().forEach(accesibilidadId -> {
                Accesibilidad accesibilidad = accesibilidadRepository.findById(accesibilidadId)
                        .orElseThrow(() -> new RuntimeException("Accesibilidad no encontrada con id: " + accesibilidadId));

                // Crear la nueva relación AccesibilidadXReservaPasajero
                AccesibilidadXReservaPasajero nuevaAccesibilidad = new AccesibilidadXReservaPasajero();
                nuevaAccesibilidad.setAccesibilidad(accesibilidad);
                nuevaAccesibilidad.setReservaPasajero(reservaPasajero);

                // Guardar la nueva relación en la base de datos
                accesibilidadXReservaPasajeroRepository.save(nuevaAccesibilidad);
            });
        }
    }

    private void actualizarAdiciones(ReservaPasajero reservaPasajero, PasajeroDTO pasajeroDTO) {
        // Comprobar si hay adiciones que actualizar
        if (pasajeroDTO.getAdicionesIds() != null && !pasajeroDTO.getAdicionesIds().isEmpty()) {
            // Eliminar adiciones actuales asociadas a la reserva del pasajero
            adicionXReservaPasajeroRepository.deleteByReservaPasajero_IdReservaPasajero(reservaPasajero.getIdReservaPasajero());

            // Agregar las nuevas adiciones desde PasajeroDTO
            pasajeroDTO.getAdicionesIds().forEach(adicionId -> {
                Adicion adicion = adicionRepository.findById(adicionId)
                        .orElseThrow(() -> new RuntimeException("Adición no encontrada con id: " + adicionId));

                // Crear la nueva relación AdicionXReservaPasajero
                AdicionXReservaPasajero nuevaAdicion = new AdicionXReservaPasajero();
                nuevaAdicion.setAdicion(adicion);
                nuevaAdicion.setReservaPasajero(reservaPasajero);

                // Aquí puedes agregar lógica adicional, por ejemplo, si tienes un costo extra asociado a la adición
                // nuevaAdicion.setCostoExtra(...);

                // Guardar la nueva relación en la base de datos
                adicionXReservaPasajeroRepository.save(nuevaAdicion);
            });
        }
    }


    private void eliminarPasajeros(List<ReservaPasajero> pasajerosExistentes, Set<String> correosPasajerosNuevos) {
        // Eliminar pasajeros que ya no están asociados a la reserva
        pasajerosExistentes.stream()
                .filter(p -> !correosPasajerosNuevos.contains(p.getPasajero().getCorreo()))
                .forEach(reservaPasajeroRepository::delete);
    }
}




