package com.udea.reservaVuelos.model.DTOs;

import com.udea.reservaVuelos.model.entities.EstadoReserva;

import java.util.List;

public class ModificarReservaDTO {
        private Integer numeroPasajeros;
        private String idVueloIda;
        private String idVueloVuelta;
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


