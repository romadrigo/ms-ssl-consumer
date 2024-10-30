package com.rrodrigo.service;

import com.rrodrigo.model.Alumno;
import com.rrodrigo.model.dto.AlumnoDTO;

public interface AlumnoService {

    AlumnoDTO getAlumnoByDni(String dni);

    void saveEvaluacion(AlumnoDTO alumnoDTO);
}
