package com.rrodrigo.service.impl;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rrodrigo.model.Alumno;
import com.rrodrigo.model.Evaluacion;
import com.rrodrigo.model.dto.AlumnoDTO;
import com.rrodrigo.repository.AlumnoRepository;
import com.rrodrigo.service.AlumnoService;

@Service
public class AlumnoServiceImpl implements AlumnoService {

    private static final Logger log = LoggerFactory.getLogger(AlumnoServiceImpl.class);

    @Autowired
    AlumnoRepository repository;

    @Override
    public AlumnoDTO getAlumnoByDni(String dni) {
        Optional<Alumno> alumno = repository.findByDni(dni);

        if (alumno.isPresent()) {
            return AlumnoDTO.builder().dni(alumno.get().getDni())
                    .nombres(alumno.get().getNombres())
                    .notas(ConvertStringtoArrray(alumno.get().getEvaluacion().getNotas()))
                    .promedio(alumno.get().getEvaluacion().getPromedio())
                    .build();
        }

        return null;
    }

    @Override
    public void saveEvaluacion(AlumnoDTO payload) {
        Optional<Alumno> alumnoBD = repository.findByDni(payload.getDni());
        Evaluacion eval = Evaluacion.builder().notas(ConvertArrayToString(payload.getNotas())).promedio(calculateAverage(payload.getNotas())).build();
        Alumno newAlumno = Alumno.builder().dni(payload.getDni()).nombres(payload.getNombres()).evaluacion(eval).build();
        if (alumnoBD.isPresent()) {
            //actualizar alumno existente
            log.debug("Alumno existente en la bd id [{}]", alumnoBD.get().getId());
            newAlumno.setId(alumnoBD.get().getId());
        }
        repository.save(newAlumno);

        log.info("alumno[{}] procesada correctamente", payload.getDni());
    }

    private String ConvertArrayToString(Integer[] input) {
        return Arrays.stream(input)
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }

    private Integer[] ConvertStringtoArrray(String input) {
        String[] stringArray = input.split(", ");
        Integer[] intArray = new Integer[stringArray.length];
        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i]);
        }
        return intArray;
    }

    private Double calculateAverage(Integer[] arrayNotas) {
        return Arrays.stream(arrayNotas)
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
    }
}
