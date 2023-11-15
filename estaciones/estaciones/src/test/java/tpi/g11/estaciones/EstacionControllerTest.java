package tpi.g11.estaciones;

import org.apache.http.client.methods.HttpPost;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import tpi.g11.estaciones.models.Estacion;
import tpi.g11.estaciones.services.EstacionServiceImpl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EstacionControllerTest {
    private EstacionServiceImpl estacionService;
    private RestTemplate restTemplate;

    @BeforeEach
    void setup(){
        estacionService = Mockito.mock(EstacionServiceImpl.class);
        restTemplate = new RestTemplate();
    }

    @Test
    void findByStatusOK() throws IOException {
        // Arrange
        List<Estacion> estaciones = new ArrayList<>();

        Mockito.when(estacionService.findAll())
                .thenReturn(estaciones);

        HttpUriRequest request = new HttpGet("http://localhost:8084/api/estaciones/" + 1);

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    void findStatusNotFound() throws IOException {
        // Arrange
        Mockito.when(estacionService.findAll())
                .thenThrow(NoSuchElementException.class);

        HttpUriRequest request = new HttpGet("http://localhost:8084/api/estaciones/");

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    void encontrarEstacionMasCercana() throws IOException {
        // Arrange
        double latitud = Mockito.anyDouble();
        double longitud = Mockito.anyDouble();
        Estacion estacion = new Estacion();

        Mockito.when(estacionService.estacionMasCercana(latitud, longitud))
                .thenReturn(estacion);

        HttpUriRequest request = new HttpGet("http://localhost:8084/api/estaciones/" + latitud + "/" + longitud);

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    }

