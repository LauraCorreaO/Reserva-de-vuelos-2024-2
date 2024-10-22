package com.udea.reservaVuelos.repository;

import com.udea.reservaVuelos.model.entities.Accesibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccesibilidadRepository extends JpaRepository<Accesibilidad, Long> {}

