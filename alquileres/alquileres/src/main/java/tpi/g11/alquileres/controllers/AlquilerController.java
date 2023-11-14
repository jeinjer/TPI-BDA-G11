package tpi.g11.alquileres.controllers;

import tpi.g11.alquileres.models.Alquiler;
import tpi.g11.alquileres.services.AlquilerServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/alquileres")
public class AlquilerController {

    private final AlquilerServiceImpl alquilerService;

    public AlquilerController(AlquilerServiceImpl estacionService) {
        this.alquilerService = estacionService;
    }

    @GetMapping
    public ResponseEntity<List<Alquiler>> getAllAlquileres() {

        try{
        List<Alquiler> alquileres = alquilerService.findAll();
        if (alquileres != null) return ResponseEntity.ok(alquileres);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }}

    @GetMapping("/finalizar-alquiler/{id}")
    public ResponseEntity<Optional<Alquiler>> finalizarAlquiler(@PathVariable Long id) {
        try{
            Optional<Alquiler> alquileres = alquilerService.finalizarAlquiler(id);
            if (alquileres != null) return ResponseEntity.ok(alquileres);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}}

    @GetMapping("/filtrado-por-estado-activo")
    public ResponseEntity<List<Alquiler>> getAlquileresFiltradosPorEstadoActivo () {
        try{
            List<Alquiler> alquileres = alquilerService.listadoFiltrado();
            if (alquileres != null) return ResponseEntity.ok(alquileres);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();}}


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Alquiler>> getAlquilerById(@PathVariable Long id) {

        try {
            Optional<Alquiler> alquiler = alquilerService.findById(id);
            if (alquiler != null) return ResponseEntity.ok(alquiler);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }}

    /*
    @GetMapping("/{id}/{moneda}")
    public ResponseEntity<Alquiler> finalizarAlquiler(@PathVariable Long id,
                                                         @PathVariable(required = false) String moneda) {
        try {
            Alquiler alquiler = alquilerService.finalizarAlquiler(id, moneda);
            if (alquiler != null) return ResponseEntity.ok(alquiler);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}
    */
    @PostMapping
    public ResponseEntity<Alquiler> createAlquiler(@RequestBody Alquiler alquiler) {
        try{
            Alquiler createdAlquiler = alquilerService.save(alquiler);
            if (createdAlquiler != null) return ResponseEntity.ok(createdAlquiler);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alquiler> updateAlquiler(@PathVariable Long id, @RequestBody Alquiler alquiler) {
        try{
        Alquiler updatedAlquiler = alquilerService.update(id, alquiler);
        if (updatedAlquiler != null) return ResponseEntity.ok(updatedAlquiler);
        else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlquiler(@PathVariable Long id) {
        try {
            alquilerService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

