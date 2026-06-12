package com.inventario.inventario;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.inventario.inventario.model.Inventario;
import com.inventario.inventario.repository.InventarioRepository;

import java.util.List;

@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(InventarioRepository repository) {
		return args -> {
			if (repository.count() == 0) {
				repository.saveAll(List.of(
						new Inventario(null, 1L, 2, "Bodega A"),
						new Inventario(null, 2L, 1, "Bodega B"),
						new Inventario(null, 3L, 4, "Bodega C"),
						new Inventario(null, 4L, 1, "Bodega D"),
						new Inventario(null, 5L, 3, "Bodega E"),
						new Inventario(null, 6L, 2, "Bodega F"),
						new Inventario(null, 7L, 1, "Bodega G"),
						new Inventario(null, 8L, 5, "Bodega H"),
						new Inventario(null, 9L, 2, "Bodega I"),
						new Inventario(null, 10L, 1, "Bodega J")));
				System.out.println("Datos iniciales insertados correctamente en la base de datos.");
			}
		};
	}
}
