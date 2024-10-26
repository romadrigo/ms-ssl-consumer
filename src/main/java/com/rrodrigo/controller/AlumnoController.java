package com.rrodrigo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rrodrigo.service.AlumnoService;
import com.rrodrigo.model.dto.AlumnoDTO;

@RestController
@RequestMapping("/api/v1/")
public class AlumnoController {

    private static final Logger logger = LoggerFactory.getLogger(AlumnoController.class);

    @Autowired
    private AlumnoService service;

    @GetMapping(value="alumnos/{dni}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlumnoDTO> get(@PathVariable String dni){
        try {
            ResponseEntity<AlumnoDTO> alumno =  new ResponseEntity<>(service.getAlumnoByDni(dni), HttpStatus.OK);
            return alumno;
        } catch (Exception e) {
            logger.error("Error: ",e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
