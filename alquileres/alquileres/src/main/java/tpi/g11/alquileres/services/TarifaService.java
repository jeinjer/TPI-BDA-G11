package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Tarifa;

import java.util.List;
import java.util.Optional;

public interface TarifaService {
        List<Tarifa> findAll();

        Optional<Tarifa> findById(Long id);

        Tarifa save(Tarifa tarifa);

        void deleteById(Long id);

        Tarifa update(Long id, Tarifa tarifa);
}

