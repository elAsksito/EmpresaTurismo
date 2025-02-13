package com.cibertec.turismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.turismo.model.DestinoTuristico;

@Repository
public interface DestinoTuristicoRepository extends JpaRepository<DestinoTuristico, Long> {
}