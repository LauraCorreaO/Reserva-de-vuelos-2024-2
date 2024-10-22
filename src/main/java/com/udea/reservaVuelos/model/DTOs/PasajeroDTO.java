package com.udea.reservaVuelos.model.DTOs;

import java.util.List;

public class PasajeroDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String tipoDocumento;
    private String numeroDocumento;
    private String correo;
    private String telefono;

    // Atributos adicionales para ReservaPasajero
    private Boolean accesibilidad; // Booleano para indicar si se seleccionó accesibilidad
    private Boolean equipajeAdicional; // Booleano para indicar si se ha solicitado equipaje adicional
    private Boolean adiciones; // Booleano para indicar si se han solicitado adiciones
    private Boolean asientoElegido; // Booleano para indicar si se ha elegido un asiento

    private List<Long> accesibilidadesIds; // Lista de IDs de accesibilidades

    private List<Long> adicionesIds;

    public Long getId() {
        return id; // Método que devuelve el ID del pasajero
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getAccesibilidad() {
        return accesibilidad;
    }

    public void setAccesibilidad(Boolean accesibilidad) {
        this.accesibilidad = accesibilidad;
    }

    public Boolean getEquipajeAdicional() {
        return equipajeAdicional;
    }

    public void setEquipajeAdicional(Boolean equipajeAdicional) {
        this.equipajeAdicional = equipajeAdicional;
    }

    public Boolean getAdiciones() {
        return adiciones;
    }

    public void setAdiciones(Boolean adiciones) {
        this.adiciones = adiciones;
    }

    public Boolean getAsientoElegido() {
        return asientoElegido;
    }

    public void setAsientoElegido(Boolean asientoElegido) {
        this.asientoElegido = asientoElegido;
    }
    public List<Long> getAccesibilidadesIds() {
        return accesibilidadesIds;
    }

    public void setAccesibilidadesIds(List<Long> accesibilidadesIds) {
        this.accesibilidadesIds = accesibilidadesIds;
    }

    public List<Long> getAdicionesIds() {
        return adicionesIds;
    }

    public void setAdicionesIds(List<Long> adicionesIds) {
        this.adicionesIds = adicionesIds;
    }

}
