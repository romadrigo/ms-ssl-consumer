package com.rrodrigo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rrodrigo.model.Alumno;

public interface AlumnoRepository extends JpaRepository<Alumno, UUID> {
    
    Optional<Alumno> findByDni(String dni);

}
