package ar.edu.unlar.tp2.service;

import ar.edu.unlar.tp2.model.CuentaBancaria;
import ar.edu.unlar.tp2.model.Transaccion;
import ar.edu.unlar.tp2.exception.CuentaInactivaException;
import ar.edu.unlar.tp2.exception.SaldoInsuficienteException;
import ar.edu.unlar.tp2.exception.LimiteExtraccionExcedidoException;
import ar.edu.unlar.tp2.exception.PinInvalidoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CajeroService {

    private final Map<String, CuentaBancaria> cuentas;
    private CuentaBancaria cuentaActual;

    public CajeroService() {
        this.cuentas = new HashMap<>();
        this.cuentaActual = null;
    }

    // --- Administración de cuentas ---

    public void registrarCuenta(CuentaBancaria cuenta) {
        cuentas.put(cuenta.getNumeroCuenta(), cuenta);
    }

    public CuentaBancaria buscarCuenta(String numeroCuenta) {
        return cuentas.get(numeroCuenta);
    }

    // --- Autenticación ---

    public boolean autenticar(String numeroCuenta, String pin) {
        CuentaBancaria cuenta = buscarCuenta(numeroCuenta);
        if (cuenta == null) {
            System.out.println("ERROR: Cuenta no encontrada.");
            return false;
        }
        try {
            cuenta.validarPin(pin);
            cuentaActual = cuenta;
            System.out.println("Bienvenido/a, " + cuenta.getTitular() + "!");
            return true;
        } catch (PinInvalidoException e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        } catch (CuentaInactivaException e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }

    public void cerrarSesion() {
        cuentaActual = null;
        System.out.println("Sesion cerrada correctamente.");
    }

    // --- Operaciones ---

    public void depositar(double monto) {
        verificarSesion();
        try {
            cuentaActual.depositar(monto);
            System.out.printf("Deposito exitoso. Nuevo saldo: $%,.2f%n", cuentaActual.getSaldo());
        } catch (CuentaInactivaException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void extraer(double monto) {
        verificarSesion();
        try {
            cuentaActual.extraer(monto);
            System.out.printf("Extraccion exitosa. Nuevo saldo: $%,.2f%n", cuentaActual.getSaldo());
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (LimiteExtraccionExcedidoException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (CuentaInactivaException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void transferir(String numeroCuentaDestino, double monto) {
        verificarSesion();
        CuentaBancaria destino = buscarCuenta(numeroCuentaDestino);
        if (destino == null) {
            System.out.println("ERROR: Cuenta destino no encontrada.");
            return;
        }
        try {
            cuentaActual.transferirA(destino, monto);
            System.out.printf("Transferencia exitosa a %s. Nuevo saldo: $%,.2f%n",
                    numeroCuentaDestino, cuentaActual.getSaldo());
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (LimiteExtraccionExcedidoException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (CuentaInactivaException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void consultarSaldo() {
        verificarSesion();
        try {
            double saldo = cuentaActual.consultarSaldo();
            System.out.printf("Saldo actual: $%,.2f%n", saldo);
        } catch (CuentaInactivaException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    public void mostrarHistorial() {
        verificarSesion();
        List<Transaccion> ultimas = cuentaActual.obtenerUltimasTransacciones(10);
        if (ultimas.isEmpty()) {
            System.out.println("No hay transacciones registradas.");
            return;
        }
        System.out.println("Ultimas transacciones:");
        System.out.println("-".repeat(60));
        for (Transaccion t : ultimas) {
            System.out.println(t);
        }
        System.out.println("-".repeat(60));
    }

    // --- Helper privado ---

    private void verificarSesion() {
        if (cuentaActual == null)
            throw new IllegalStateException("No hay ninguna cuenta autenticada.");
    }

    public CuentaBancaria getCuentaActual() {
        return cuentaActual;
    }
}
