package com.udea.reservaVuelos.model.DTOs;

import com.udea.reservaVuelos.model.entities.EstadoReserva;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class ReservaDTO {
    private String idVueloIda;
    private String idVueloVuelta;
    private String numeroReserva;
    private String fechaReserva;
    private Integer numeroPasajeros;
    private List<PasajeroDTO> pasajeros;
    private EstadoReserva estado;
    // Getters y Setters
    public String getIdVueloIda() {
        return idVueloIda;
    }

    public void setIdVueloIda(String idVueloIda) {
        this.idVueloIda = idVueloIda;
    }

    public String getIdVueloVuelta() {
        return idVueloVuelta;
    }

    public void setIdVueloVuelta(String idVueloVuelta) {
        this.idVueloVuelta = idVueloVuelta;
    }

    public String getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(String numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public String getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Integer getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(Integer numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public List<PasajeroDTO> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<PasajeroDTO> pasajeros) {
        this.pasajeros = pasajeros;
    }
    public EstadoReserva getEstado(){
        return estado;
    }

    public void setEstado(EstadoReserva estado){
        this.estado = estado;
    }
}
