package tpi.g11.estaciones.controllers;

import tpi.g11.estaciones.models.Estacion;
import tpi.g11.estaciones.services.EstacionServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionController {

    private final EstacionServiceImpl estacionService;

    public EstacionController(EstacionServiceImpl estacionService) {
        this.estacionService = estacionService;
    }

    @GetMapping
    public ResponseEntity<List<Estacion>> getAllEstaciones() {

        try{
            List<Estacion> estaciones = estacionService.findAll();
            if (estaciones.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else return ResponseEntity.ok(estaciones);

        } catch (NoSuchElementException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Estacion>> getEstacionById(@PathVariable Long id) {

        try {
            Optional<Estacion> estacion = estacionService.findById(id);
            if (estacion != null) return ResponseEntity.ok(estacion);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}

    @PostMapping
    public ResponseEntity<Estacion> createEstacion(@RequestBody Estacion estacion) {
        try{
            Estacion createdEstacion = estacionService.save(estacion);
            if (createdEstacion != null) return ResponseEntity.ok(createdEstacion);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estacion> updateEstacion(@PathVariable Long id, @RequestBody Estacion estacion) {
        try{
            Estacion updatedEstacion = estacionService.update(id, estacion);
            if (updatedEstacion != null) return ResponseEntity.ok(updatedEstacion);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstacion(@PathVariable Long id) {
        try {
            estacionService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{latitud}/{longitud}")
    public ResponseEntity<Estacion> estacionMasCercana(@PathVariable Double latitud, @PathVariable Double longitud){
      try{
          Estacion estacion = estacionService.estacionMasCercana(latitud, longitud);
          if (estacion != null){
              return ResponseEntity.ok(estacion);
          } else return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
      } catch (NoSuchElementException ex){
          return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

      } catch (Exception e){
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
      }
    };

}

