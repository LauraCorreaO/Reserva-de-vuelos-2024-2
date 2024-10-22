package com.udea.reservaVuelos.services.pasajero;

import com.udea.reservaVuelos.model.DTOs.PasajeroDTO;
import com.udea.reservaVuelos.model.entities.Pasajero;
import com.udea.reservaVuelos.repository.PasajeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImpCrearPasajeroService implements CrearPasajeroService {
    @Autowired
    private PasajeroRepository pasajeroRepository;
    @Transactional
    @Override
    public Pasajero crearPasajero(PasajeroDTO pasajeroDTO) {
        // Validar el DTO
        if (pasajeroDTO == null) {
            throw new IllegalArgumentException("El DTO del pasajero no puede ser nulo.");
        }

        //TODO: VALIDACIÓN: Logica de que vaya y compare la cedula, el correo y el numero del pasajero,
        // en caso de que la cedula no exista quiere decir que el usuario es nuevo entonces si se inserta, si la cedula
        // ya existe y el correo y numero de telefono son iguales a los que ya habia registrados se devuelve el pasajero
        // que esta guardado en la base de datos, si la cedula es la misma pero el correo o el telefono cambio entonces
        // se actualiza el registro que se tiene

        // Crear el objeto Pasajero
        Pasajero pasajero = new Pasajero();
        pasajero.setNombre(pasajeroDTO.getNombre());
        pasajero.setApellido(pasajeroDTO.getApellido());
        pasajero.setTipoDocumento(pasajeroDTO.getTipoDocumento());
        pasajero.setNumeroDocumento(pasajeroDTO.getNumeroDocumento());
        pasajero.setCorreo(pasajeroDTO.getCorreo());
        pasajero.setTelefono(pasajeroDTO.getTelefono());

        // Guardar el pasajero en la base de datos
        return pasajeroRepository.save(pasajero);
    }

    @Override
    public List<Pasajero> crearPasajeros(List<PasajeroDTO> pasajerosDTO) {
        if (pasajerosDTO == null || pasajerosDTO.isEmpty()) {
            throw new IllegalArgumentException("La lista de pasajeros no puede ser nula o vacía.");
        }

        List<Pasajero> pasajeros = new ArrayList<>();
        for (PasajeroDTO dto : pasajerosDTO) {
            Pasajero pasajero = new Pasajero();
            pasajero.setNombre(dto.getNombre());
            pasajero.setApellido(dto.getApellido());
            pasajero.setTipoDocumento(dto.getTipoDocumento());
            pasajero.setNumeroDocumento(dto.getNumeroDocumento());
            pasajero.setCorreo(dto.getCorreo());
            pasajero.setTelefono(dto.getTelefono());

            // Guardar el pasajero en la base de datos
            pasajeros.add(pasajeroRepository.save(pasajero));
        }

        return pasajeros;
    }
}
