package com.rrodrigo.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDTO implements Serializable {

    private static final long serialVersionUID = 2445247993956960711L;

    private String dni;
	private String nombres;
	private Integer[] notas;
	private Double promedio;
}
