package tpi.g11.alquileres.repositorios;

import tpi.g11.alquileres.models.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository <Tarifa, Long>{
    @Query("Select coalesce(max(tarifaId), 0) From Tarifa")
    Long getMaxId();
}
