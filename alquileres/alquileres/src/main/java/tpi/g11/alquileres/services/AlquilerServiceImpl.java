package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Alquiler;
import tpi.g11.alquileres.models.Tarifa;
import tpi.g11.alquileres.repositorios.AlquilerRepository;
import org.springframework.stereotype.Service;
import tpi.g11.estaciones.models.Estacion;
import tpi.g11.estaciones.services.EstacionServiceImpl;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AlquilerServiceImpl implements AlquilerService{
    private final AlquilerRepository alquilerRepository;
    private final EstacionServiceImpl estacionService;
    private final TarifaServiceImpl tarifaService;
    private final MonedaService monedaService;
    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, EstacionServiceImpl estacionService, TarifaServiceImpl tarifaService, MonedaService monedaService) {
        this.alquilerRepository = alquilerRepository;
        this.estacionService = estacionService;
        this.tarifaService = tarifaService;
        this.monedaService = monedaService;
    }

    public List<Alquiler> findAll() {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        return alquileres;
    }

    public Optional<Alquiler> findById(Long id) {
        Optional<Alquiler> alquiler = alquilerRepository.findById(id);
        return alquiler;
    }

    public Alquiler save(Alquiler alquiler) {
        Long maxId = alquilerRepository.getMaxId();
        alquiler.setAlquilerId(maxId + 1);
        Alquiler savedAlquiler = alquilerRepository.save(alquiler);
        return savedAlquiler;
    }

    public void deleteById(Long id) {
        alquilerRepository.deleteById(id);
    }

    public Alquiler update(Long id, Alquiler alquiler) {
        Optional<Alquiler> existingAlquiler = alquilerRepository.findById(id);
        if (existingAlquiler.isPresent()) {
            alquiler.setAlquilerId(id);
            Alquiler updatedAlquiler = alquilerRepository.save(alquiler);
            return updatedAlquiler;
        } else {
            return null;
        }
    }

    @Override
    public Alquiler iniciarAlquiler(Long estacionId){
        try{
        Optional<Estacion> estacion = estacionService.findById(estacionId);
        if (estacion.isPresent()) {
            Alquiler alquiler = new Alquiler();
            alquiler.setEstacionRetiro(estacion.get());
            alquiler.setEstado(1);
            save(alquiler);
            return alquiler;
            }
        } catch (RuntimeException ex){
            throw new IllegalArgumentException("Error con la estación o el alquiler");
        } return null;
    }


    public double calcularCosto(Alquiler alquiler, Tarifa tarifa){
        Duration duracion = Duration.between(alquiler.getFechaHoraRetiro(), alquiler.getFechaHoraDevolucion());
        long minutos = duracion.toMinutes();
        long horas = duracion.toHours();
        double costo;

        if (horas == 0){
            costo = tarifa.getMontoMinutoFraccion() * minutos;
        } else {
            if (minutos > 30) {
                costo = tarifa.getMontoHora() * (horas + 1);
            } else {
                costo = tarifa.getMontoHora() * horas;
            }
        }

        double distancia = estacionService.calcularDistancia(
                alquiler.getEstacionRetiro().getLatitud(),
                alquiler.getEstacionDevolucion().getLatitud(),
                alquiler.getEstacionRetiro().getLongitud(),
                alquiler.getEstacionDevolucion().getLongitud());

        costo = costo + (distancia * tarifa.getMontoKm());

        return costo;
    }

    @Override
    public Optional<Alquiler> finalizarAlquiler(Long alquilerId, String moneda) {
        try {
            Optional<Alquiler> alquiler = this.findById(alquilerId);
            if (alquiler.isPresent()) {
                Optional<Tarifa> tarifa = tarifaService.findById(alquiler.get().getTarifa().getTarifaId());
                if (tarifa.isPresent()) {
                    Alquiler alquilerFinalizado = alquiler.get();
                    alquilerFinalizado.setMonto(calcularCosto(alquilerFinalizado, tarifa.get()));
                    alquilerFinalizado.setEstado(2);

                    if (moneda != null) {
                        alquilerFinalizado.setMonto(monedaService.calcularConversion(moneda, alquilerFinalizado.getMonto()));
                    }

                    return Optional.of(alquilerFinalizado);
                }
            }
        } catch (NoSuchElementException e) {
            return null;
        }
        return Optional.empty();
    }

    public List<Alquiler> listadoFiltrado(){
        List<Alquiler> alquileresFiltrados = null;
        try{
        List<Alquiler> alquileres = this.findAll();
        if (alquileres != null){
            alquileresFiltrados = alquileres.stream().filter(alquiler -> alquiler.getEstado() == 1).toList();
        }
        } catch (NoSuchElementException e){
        }
        return alquileresFiltrados;
    }
}

