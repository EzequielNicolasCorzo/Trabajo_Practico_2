package ar.edu.unlar.tp2.model;
import java.util.ArrayList;
import java.util.List;
import ar.edu.unlar.tp2.exception.SaldoInsuficienteException;
import ar.edu.unlar.tp2.exception.LimiteExtraccionExcedidoException;
import ar.edu.unlar.tp2.exception.CuentaInactivaException;
import ar.edu.unlar.tp2.exception.PinInvalidoException;

public class CuentaBancaria {

    private final String numeroCuenta;   // inmutable
    private double saldo;
    private final String titular;
    private boolean activa;
    private final ArrayList<Transaccion> historialTransacciones;
    private final String pin;
    private int intentosFallidos;
    private static final int MAX_INTENTOS = 3;
    

    private static final double LIMITE_EXTRACCION = 10000.0;

    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial, String pin) {
    this.numeroCuenta = numeroCuenta;
    this.titular = titular;
    this.saldo = saldoInicial;
    this.activa = true;
    this.historialTransacciones = new ArrayList<>();
    this.pin = pin;
    this.intentosFallidos = 0;
}

    // --- Operaciones ---

    public void validarPin(String pinIngresado) throws PinInvalidoException, CuentaInactivaException {
    validarCuentaActiva();
    if (!this.pin.equals(pinIngresado)) {
        intentosFallidos++;
        if (intentosFallidos >= MAX_INTENTOS) {
            this.activa = false;
        }
        throw new PinInvalidoException(MAX_INTENTOS - intentosFallidos);
    }
    intentosFallidos = 0;
    }

    public void depositar(double monto) throws Exception {
        validarCuentaActiva();
        if (monto <= 0)
            throw new Exception("El monto del depósito debe ser positivo.");

        saldo += monto;
        registrarTransaccion(Transaccion.Tipo.DEPÓSITO, monto,
                "Depósito exitoso | Saldo: $" + String.format("%,.2f", saldo));
    }

    public void extraer(double monto) throws Exception {
        validarCuentaActiva();
        if (monto <= 0)
            throw new Exception("El monto debe ser positivo.");
        if (monto > LIMITE_EXTRACCION)
            throw new LimiteExtraccionExcedidoException(monto, LIMITE_EXTRACCION);
        if (monto > saldo)
            throw new SaldoInsuficienteException(saldo, monto);

        saldo -= monto;
        registrarTransaccion(Transaccion.Tipo.EXTRACCIÓN, monto,
                "Extracción exitosa | Saldo: $" + String.format("%,.2f", saldo));
    }

    public void transferirA(CuentaBancaria destino, double monto) throws Exception {
        validarCuentaActiva();
        // Se descuenta primero; si falla, no se acredita (transacción atómica simple)
        this.extraer(monto);
        destino.depositar(monto);
        registrarTransaccion(Transaccion.Tipo.TRANSFERENCIA, monto,
                "Transferencia a " + destino.getNumeroCuenta() + " | Saldo: $" + String.format("%,.2f", saldo));
    }

    public double consultarSaldo() throws Exception {
        validarCuentaActiva();
        registrarTransaccion(Transaccion.Tipo.CONSULTA, 0,
                "Consulta de saldo | Saldo: $" + String.format("%,.2f", saldo));
        return saldo;
    }

    public List<Transaccion> obtenerUltimasTransacciones(int cantidad) {
        int size = historialTransacciones.size();
        int desde = Math.max(0, size - cantidad);
        return historialTransacciones.subList(desde, size);
    }

    // --- Helpers privados ---

    private void validarCuentaActiva() throws CuentaInactivaException {
    if (!activa)
        throw new CuentaInactivaException(numeroCuenta);
    }   

    private void registrarTransaccion(Transaccion.Tipo tipo, double monto, String descripcion) {
        historialTransacciones.add(new Transaccion(tipo, monto, descripcion));
    }

    // --- Getters ---
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public void desactivar() { this.activa = false; }
}
