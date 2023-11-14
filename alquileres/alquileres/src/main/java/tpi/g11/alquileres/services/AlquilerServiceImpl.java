package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Alquiler;
import tpi.g11.alquileres.repositorios.AlquilerRepository;
import org.springframework.stereotype.Service;
import tpi.g11.estaciones.services.EstacionServiceImpl;

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
    /*
    // Metodo sin probar
    public void iniciarAlquiler(Long estacionId){
        try{
        EstacionDTO estacionDto = estacionService.findById(estacionId);
        if (estacionDto != null){
            AlquilerDTO alquilerDto = new AlquilerDTO();
            alquilerDto.setEstacionRetiro(estacionDto);  // Estacion pasada como argumento
            save(alquilerDto);  // Finalmente la guardamos
            }
        } catch (RuntimeException ex){
            throw new IllegalArgumentException("Error con la estación o el alquiler");
        }
    }

    public double calcularCosto(AlquilerDTO alquilerDto, TarifaDTO tarifaDto){
        Duration duracion = Duration.between(alquilerDto.getFechaHoraRetiro(), alquilerDto.getFechaHoraDevolucion());
        long minutos = duracion.toMinutes();
        long horas = duracion.toHours();
        double costo;

        if (horas == 0){
            costo = tarifaDto.getMontoMinutoFraccion() * minutos;
        } else {
            if (minutos > 30) {
                costo = tarifaDto.getMontoHora() * (horas + 1);
            } else {
                costo = tarifaDto.getMontoHora() * horas;
            }
        }

        double distancia = estacionService.calcularDistancia(
                alquilerDto.getEstacionRetiro().getLatitud(),
                alquilerDto.getEstacionDevolucion().getLatitud(),
                alquilerDto.getEstacionRetiro().getLongitud(),
                alquilerDto.getEstacionDevolucion().getLongitud());

        costo = costo + (distancia * tarifaDto.getMontoKm());

        return costo;
    }

    public AlquilerDTO finalizarAlquiler(Long alquilerId, String moneda) {
        AlquilerDTO alquilerFinalizado = null;
        try {
            AlquilerDTO alquiler = this.findById(alquilerId);
            if (alquiler != null) {
                alquilerFinalizado = alquiler;
                alquilerFinalizado.setMonto(calcularCosto(alquiler, tarifaService.findById(1L)));
                alquilerFinalizado.setEstado(2);
            }
            return alquilerFinalizado;
        } catch (NoSuchElementException e) {
            // agregar excepcion
        }
        return alquilerFinalizado;
        /*
        if (moneda != null){
            alquilerFinalizado.setMonto(monedaService.calcularConversion(moneda));
        }
        return alquilerFinalizado;
    }
    */
    public List<Alquiler> listadoFiltrado(){
        List<Alquiler> alquileresFiltrados = null;
        try{
        List<Alquiler> alquileres = this.findAll();
        if (alquileres != null){
            alquileresFiltrados = alquileres.stream().filter(alquiler -> alquiler.getEstado() == 1).toList();
        }
        } catch (NoSuchElementException e){
            // agregar excepcion
        }
        return alquileresFiltrados;
    }
}
