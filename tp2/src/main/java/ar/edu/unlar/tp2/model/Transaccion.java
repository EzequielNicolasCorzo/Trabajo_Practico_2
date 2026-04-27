package ar.edu.unlar.tp2.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaccion {
    public enum Tipo {
        DEPÓSITO, EXTRACCIÓN, TRANSFERENCIA, CONSULTA
    }

    private final Tipo tipo;
    private final double monto;
    private final LocalDateTime fechaHora;
    private final String descripcion;

    public Transaccion(Tipo tipo, double monto, String descripcion) {
        this.tipo = tipo;
        this.monto = monto;
        this.fechaHora = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    // Getters
    public Tipo getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getDescripcion() { return descripcion; }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[%s] %s: $%,.2f | %s",
                fechaHora.format(fmt), tipo, monto, descripcion);
    }
}
