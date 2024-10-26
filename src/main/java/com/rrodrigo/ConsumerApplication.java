package com.rrodrigo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rrodrigo.model.Alumno;
import com.rrodrigo.model.Evaluacion;
import com.rrodrigo.repository.AlumnoRepository;

@SpringBootApplication
public class ConsumerApplication implements CommandLineRunner{

	@Autowired
	private AlumnoRepository alumnoRepository;

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
		String notas = "[10, 10, 10, 10]";
		Integer[] notasi = new Integer[]{10,10,10,10};
		Double average = Arrays.stream(notasi)
                               .mapToInt(Integer::intValue) 
                               .average()                    
                               .orElse(0.0);              

        System.out.println("The average is: " + average);

		Evaluacion eval = Evaluacion.builder().notas(notas).promedio(average).build();
       //	alumnoRepository.save(Alumno.builder().dni("70537919").nombres("Romario Rodrigo").evaluacion(eval).build());
		//alumnoRepository.save(Alumno.builder().dni("70537918").nombres("Juan Rodrigo").evaluacion(eval).build());
    }


}
