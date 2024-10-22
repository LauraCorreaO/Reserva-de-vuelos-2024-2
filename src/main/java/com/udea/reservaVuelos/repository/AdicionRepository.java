package com.udea.reservaVuelos.repository;

import com.udea.reservaVuelos.model.entities.Adicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdicionRepository extends JpaRepository<Adicion, Long> {}
