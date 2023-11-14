package tpi.g11.alquileres.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tpi.g11.estaciones.models.Estacion;

import java.time.LocalDateTime;

@Entity
@Table(name = "ALQUILERES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alquiler {
    @Id
    @Column(name = "ID")
    private Long alquilerId;

    @Column(name = "ID_CLIENTE")
    private String idCliente;

    @Column(name = "ESTADO")
    private int estado;

    @Column(name = "FECHA_HORA_DEVOLUCION")
    private LocalDateTime fechaHoraDevolucion;

    @Column(name = "FECHA_HORA_RETIRO")
    private LocalDateTime fechaHoraRetiro;

    @Column(name = "MONTO")
    private double monto;
    
    @OneToOne
    @JoinColumn(name = "ESTACION_RETIRO")
    private Estacion estacionRetiro;

    @OneToOne
    @JoinColumn(name = "ESTACION_DEVOLUCION")
    private Estacion estacionDevolucion;

    @OneToOne
    @JoinColumn(name = "ID_TARIFA")
    private Tarifa tarifa;
}
