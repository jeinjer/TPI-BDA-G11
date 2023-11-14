package tpi.g11.alquileres.controllers;

import tpi.g11.alquileres.models.Tarifa;
import tpi.g11.alquileres.services.TarifaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    private final TarifaServiceImpl tarifaService;

    public TarifaController(TarifaServiceImpl tarifaService) {
        this.tarifaService = tarifaService;
    }

    @GetMapping
    public ResponseEntity<List<Tarifa>> getAllTarifas() {

        try{
            List<Tarifa> tarifas = tarifaService.findAll();
            if (tarifas != null) return ResponseEntity.ok(tarifas);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        } catch (IllegalArgumentException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}


    @GetMapping("/{id}")
    public ResponseEntity<Optional<Tarifa>> getTarifaById(@PathVariable Long id) {

        try {
            Optional<Tarifa> tarifa = tarifaService.findById(id);
            if (tarifa != null) return ResponseEntity.ok(tarifa);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }}

    @PostMapping
    public ResponseEntity<Tarifa> createTarifa(@RequestBody Tarifa tarifa) {
        try{
            Tarifa createdTarifa = tarifaService.save(tarifa);
            if (createdTarifa != null) return ResponseEntity.ok(createdTarifa);
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tarifa> updateTarifa(@PathVariable Long id, @RequestBody Tarifa tarifa) {
        try{
            Tarifa updatedTarifa = tarifaService.update(id, tarifa);
            if (updatedTarifa != null) return ResponseEntity.ok(updatedTarifa);
            else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTarifa(@PathVariable Long id) {
        try {
            tarifaService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

