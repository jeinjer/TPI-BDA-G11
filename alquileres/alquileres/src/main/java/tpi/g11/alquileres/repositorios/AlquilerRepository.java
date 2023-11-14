package tpi.g11.alquileres.repositorios;

import tpi.g11.alquileres.models.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AlquilerRepository extends JpaRepository <Alquiler, Long>{
    @Query("Select coalesce(max(alquilerId), 0) From Alquiler")
    Long getMaxId();

}
