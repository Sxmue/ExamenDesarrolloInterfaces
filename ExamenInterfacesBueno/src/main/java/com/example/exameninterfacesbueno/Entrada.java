package com.example.exameninterfacesbueno;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class Entrada {
    private String matricula;
    private String modelo;
    private Cliente cliente;
    private String tipoTarifa;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    private Integer costeTotal;

    public Entrada(String matricula, String modelo, Cliente cliente, String tipoTarifa, LocalDate fechaEntrada, LocalDate fechaSalida, Integer costeTotal) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.cliente = cliente;
        this.tipoTarifa = tipoTarifa;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.costeTotal = costeTotal;
    }
}
