package tpi.g11.estaciones.services;

import tpi.g11.estaciones.models.Estacion;
import tpi.g11.estaciones.repositorios.EstacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstacionServiceImpl implements EstacionService {

    private final EstacionRepository estacionRepository;


    public EstacionServiceImpl(EstacionRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    @Override
    public List<Estacion> findAll() {
        List<Estacion> estaciones = estacionRepository.findAll();
        return estaciones;
    }

    @Override
    public Optional<Estacion> findById(Long id) {
        Optional<Estacion> estacion = estacionRepository.findById(id);
        return estacion;
    }

    @Override
    public Estacion save(Estacion estacion) {
        Long maxId = estacionRepository.getMaxId();
        estacion.setEstacionId(maxId + 1);
        Estacion savedEstacion = estacionRepository.save(estacion);
        return savedEstacion;
    }

    @Override
    public void deleteById(Long id) {
        estacionRepository.deleteById(id);
    }

    @Override
    public Estacion update(Long id, Estacion estacion) {
        Optional<Estacion> existingEstacion = estacionRepository.findById(id);
        if (existingEstacion.isPresent()) {
            estacion.setEstacionId(id);
            Estacion updatedEstacion = estacionRepository.save(estacion);
            return updatedEstacion;
        } else {
            return null;
        }
    }

    public double calcularDistancia(double lat1, double lat2, double lon1, double lon2) {

        // Radio de la Tierra en kilómetros
        final double R = 6371.0;

        // Convertir latitudes y longitudes de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencias de latitud y longitud
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Fórmula de Haversine
        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Distancia en kilómetros
        double distancia = R * c;

        return distancia;
    }


    public Estacion estacionMasCercana(double latitud, double longitud){
        List<Estacion> estaciones = this.findAll();
        Estacion estacionMasCercana = null;
        double distanciaMasCercana = Double.MAX_VALUE;

        for (Estacion estacion : estaciones) {
            double distancia = calcularDistancia(estacion.getLatitud(), latitud, estacion.getLongitud(), longitud);

            if (distancia < distanciaMasCercana){
                distanciaMasCercana = distancia;
                estacionMasCercana = estacion;
        }}

        return estacionMasCercana;
    }
}
