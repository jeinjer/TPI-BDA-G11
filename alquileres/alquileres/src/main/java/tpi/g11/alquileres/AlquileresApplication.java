package tpi.g11.alquileres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"tpi.g11.alquileres", "tpi.g11.estaciones"}) // Reemplaza estos paquetes con los correctos
@EntityScan(basePackages = {"tpi.g11.alquileres.models", "tpi.g11.estaciones.models"})
public class AlquileresApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlquileresApplication.class, args);
	}
}
