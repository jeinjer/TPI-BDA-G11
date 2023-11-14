package tpi.g11.estaciones.services;

import tpi.g11.estaciones.models.Estacion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface EstacionService {
    public List<Estacion> findAll();
    public Optional<Estacion> findById(Long id);
    public Estacion save(Estacion estacion);
    public void deleteById(Long id);
    public Estacion update(Long id, Estacion estacion);
    public double calcularDistancia(double lat1, double lat2, double lon1, double lon2);
    public Estacion estacionMasCercana(double latitud, double longitud);
}
