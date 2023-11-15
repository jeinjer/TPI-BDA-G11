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

    Alquiler iniciarAlquiler(Long estacionId);

    double calcularCosto(Alquiler alquiler, Tarifa tarifa);

    Optional<Alquiler> finalizarAlquiler(Long alquilerId, String moneda);

    List<Alquiler> listadoFiltrado();
}
