package com.udea.reservaVuelos.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva; // PK idReserva

    @Column(name = "numero_reserva", nullable = false, unique = true)
    private String numeroReserva; // numeroReserva

    @Column(name = "fecha_reserva", nullable = false)
    private String fechaReserva; // fechaReserva

    @Column(name = "numero_pasajeros", nullable = false)
    private int numeroPasajeros; // numeroPasajeros

    @Column(name = "id_vuelo_ida")
    private String idVueloIda; // FK idVueloIda

    @Column(name = "id_vuelo_vuelta")
    private String idVueloVuelta; // FK idVueloVuelta
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoReserva estado; // Estado de la reserva

    @Column(name = "fecha_Cancelacion")
    private LocalDateTime fechaCancelacion;
    // Constructor
    public Reserva() {
    }

    public Reserva(Long idReserva, String numeroReserva, String fechaReserva, int numeroPasajeros, String idVueloIda, String idVueloVuelta, EstadoReserva estado, LocalDateTime fechaCancelacion) {
        this.idReserva = idReserva;
        this.numeroReserva = numeroReserva;
        this.fechaReserva = fechaReserva;
        this.numeroPasajeros = numeroPasajeros;
        this.idVueloIda = idVueloIda;
        this.idVueloVuelta = idVueloVuelta;
        this.estado = estado;
        this.fechaCancelacion = fechaCancelacion;
    }

    // Getters y Setters
    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
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

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

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

    public EstadoReserva getEstado() {return estado;}

    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    public LocalDateTime getFechaCancelacion() {return fechaCancelacion;}

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }
}
