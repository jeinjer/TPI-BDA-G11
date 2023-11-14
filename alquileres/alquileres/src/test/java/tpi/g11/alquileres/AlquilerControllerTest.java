package tpi.g11.alquileres;

import tpi.g11.alquileres.models.Alquiler;
import tpi.g11.alquileres.services.AlquilerServiceImpl;
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

public class AlquilerControllerTest {
    private AlquilerServiceImpl alquilerService;

    @BeforeEach
    void setup(){
        alquilerService = Mockito.mock(AlquilerServiceImpl.class);
    }

    @Test
    void findByStatusOK() throws IOException {
        // Arrange
        List<Alquiler> alquileres = new ArrayList<>();

        Mockito.when(alquilerService.findAll())
                .thenReturn(alquileres);

        HttpUriRequest request = new HttpGet("http://localhost:8083/api/alquileres/");

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
    @Test
    void findStatusNotFound() throws IOException {
        // Arrange
        Mockito.when(alquilerService.findAll())
                .thenThrow(NoSuchElementException.class);

        HttpUriRequest request = new HttpGet("http://localhost:8083/api/alquileres/");

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(404, response.getStatusLine().getStatusCode());
    }

    @Test
    void listadoPorFiltro() throws IOException {
        // Arrange
        List<Alquiler> alquileres = new ArrayList<>();
        Mockito.when(alquilerService.listadoFiltrado())
                .thenReturn(alquileres);

        HttpUriRequest request = new HttpGet("http://localhost:8083/api/alquileres/filtrado-por-estado-activo");

        // Act
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        // Assert
        assertEquals(200, response.getStatusLine().getStatusCode());
    }
    }

