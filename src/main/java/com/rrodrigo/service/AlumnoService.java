package com.rrodrigo.service;

import com.rrodrigo.model.dto.AlumnoDTO;

public interface AlumnoService {

    AlumnoDTO getAlumnoByDni(String dni);
}
