package com.rrodrigo.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrodrigo.model.Alumno;
import com.rrodrigo.model.dto.AlumnoDTO;
import com.rrodrigo.repository.AlumnoRepository;
import com.rrodrigo.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    @Autowired
    AlumnoRepository repository;

    @Override
    public AlumnoDTO getAlumnoByDni(String dni) {
        Optional<Alumno> alumno = repository.findByDni(dni);

        if (alumno.isPresent()) 
            return AlumnoDTO.builder().dni(alumno.get().getDni())
            .nombres(alumno.get().getNombres())
            .notas(Arrays.asList(alumno.get().getEvaluacion().getNotas()).toArray(new Integer[0]))
            .promedio(alumno.get().getEvaluacion().getPromedio())
            .build();
        
        return null;
    }

}
