package tpi.g11.estaciones.repositorios;

import tpi.g11.estaciones.models.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionRepository extends JpaRepository <Estacion, Long>{
    @Query("Select coalesce(max(estacionId), 0) From Estacion ")
    Long getMaxId();
}
