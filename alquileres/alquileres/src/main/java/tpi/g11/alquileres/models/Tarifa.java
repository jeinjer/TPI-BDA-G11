package tpi.g11.alquileres.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TARIFAS")
@Data
@NoArgsConstructor

public class Tarifa {
    @Id
    @Column(name = "ID")
    private Long tarifaId;

    @Column(name = "TIPO_TARIFA")
    private int tipoTarifa;

    @Column(name = "DEFINICION")
    private String definicion;

    @Column(name = "DIA_SEMANA")
    private Integer diaSemana;

    @Column(name = "DIA_MES")
    private Integer diaMes;

    @Column(name = "MES")
    private Integer mes;

    @Column(name = "ANIO")
    private Integer anio;

    @Column(name = "MONTO_FIJO_ALQUILER")
    private float montoFijoAlquiler;

    @Column(name = "MONTO_MINUTO_FRACCION")
    private float montoMinutoFraccion;

    @Column(name = "MONTO_KM")
    private float montoKm;

    @Column(name = "MONTO_HORA")
    private float montoHora;
}
