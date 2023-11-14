package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Alquiler;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface AlquilerService {
    public List<Alquiler> findAll();


    public Optional<Alquiler> findById(Long id);


    public Alquiler save(Alquiler alquiler);

    public void deleteById(Long id);

    public Alquiler update(Long id, Alquiler alquiler);
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
    public List<Alquiler> listadoFiltrado();
}
