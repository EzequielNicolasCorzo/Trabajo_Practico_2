package ar.edu.unlar.programacion3.proyecto.model;

import java.util.ArrayList;

public class CuentaBancaria {
    private final String numeroCuenta; // [cite: 17, 57]
    private double saldo;
    private String titular;
    private boolean activa;
    private ArrayList<String> historialTransacciones;

    public enum TipoTransaccion {
        DEPOSITO, EXTRACCION, TRANSFERENCIA, CONSULTA // [cite: 23]
    }

    public CuentaBancaria(String numeroCuenta, double saldoInicial, String titular) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldoInicial;
        this.titular = titular;
        this.activa = true;
        this.historialTransacciones = new ArrayList<>();
    }

    // Getters
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public String getTitular() { return titular; }
    public boolean isActiva() { return activa; }
    public ArrayList<String> getHistorial() { return historialTransacciones; }

    // Setters
    public void setSaldo(double saldo) { this.saldo = saldo; }
    public void setActiva(boolean activa) { this.activa = activa; }
}