package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Alquiler;
import tpi.g11.alquileres.models.Tarifa;

import java.util.List;
import java.util.Optional;

public interface AlquilerService {
    List<Alquiler> findAll();

    Optional<Alquiler> findById(Long id);

    Alquiler save(Alquiler alquiler);

    void deleteById(Long id);

    Alquiler update(Long id, Alquiler alquiler);

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
    */

    double calcularCosto(Alquiler alquiler, Tarifa tarifa);

    Optional<Alquiler> finalizarAlquiler(Long alquilerId, String moneda);

    List<Alquiler> listadoFiltrado();
}
