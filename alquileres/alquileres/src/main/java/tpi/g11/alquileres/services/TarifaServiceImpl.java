package tpi.g11.alquileres.services;

import tpi.g11.alquileres.models.Tarifa;
import tpi.g11.alquileres.repositorios.TarifaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TarifaServiceImpl implements TarifaService{

    private final TarifaRepository tarifaRepository;

    public TarifaServiceImpl(TarifaRepository tarifaRepository) {
        this.tarifaRepository = tarifaRepository;
    }

    public List<Tarifa> findAll() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        return tarifas;
    }

    public Optional<Tarifa> findById(Long id) {
        Optional<Tarifa> tarifa = tarifaRepository.findById(id);
        return tarifa;
    }

    public Tarifa save(Tarifa tarifa) {
        Long maxId = tarifaRepository.getMaxId();
        tarifa.setTarifaId(maxId + 1);
        Tarifa savedTarifa = tarifaRepository.save(tarifa);
        return savedTarifa;
    }

    public void deleteById(Long id) {
        tarifaRepository.deleteById(id);
    }

    public Tarifa update(Long id, Tarifa tarifa) {
        Optional<Tarifa> existingTarifa = tarifaRepository.findById(id);
        if (existingTarifa.isPresent()) {
            tarifa.setTarifaId(id);
            Tarifa updatedTarifa = tarifaRepository.save(tarifa);
            return updatedTarifa;
        } else {
            return null;
        }
    }

}
