package ar.edu.unlar.tp2.exception;

public class LimiteExtraccionExcedidoException extends Exception {

    private final double montoSolicitado;
    private final double limitePermitido;

    public LimiteExtraccionExcedidoException(double montoSolicitado, double limitePermitido) {
        super(String.format(
            "Límite de extracción excedido. Solicitado: $%,.2f | Límite por operación: $%,.2f",
            montoSolicitado, limitePermitido
        ));
        this.montoSolicitado = montoSolicitado;
        this.limitePermitido = limitePermitido;
    }

    public double getMontoSolicitado() { return montoSolicitado; }
    public double getLimitePermitido() { return limitePermitido; }
}
