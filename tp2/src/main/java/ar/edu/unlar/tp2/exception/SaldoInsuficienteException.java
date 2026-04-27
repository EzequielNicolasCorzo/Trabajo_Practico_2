package ar.edu.unlar.tp2.exception;

public class SaldoInsuficienteException extends Exception {

    private final double saldoActual;
    private final double montoSolicitado;

    public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
        super(String.format(
            "Saldo insuficiente. Saldo actual: $%,.2f | Monto solicitado: $%,.2f",
            saldoActual, montoSolicitado
        ));
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
    }

    public double getSaldoActual() { return saldoActual; }
    public double getMontoSolicitado() { return montoSolicitado; }
}
