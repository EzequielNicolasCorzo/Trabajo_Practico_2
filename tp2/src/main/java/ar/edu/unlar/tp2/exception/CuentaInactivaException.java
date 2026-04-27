package ar.edu.unlar.tp2.exception;

public class CuentaInactivaException extends Exception {

    private final String numeroCuenta;

    public CuentaInactivaException(String numeroCuenta) {
        super(String.format(
            "La cuenta %s se encuentra inactiva y no puede operar.",
            numeroCuenta
        ));
        this.numeroCuenta = numeroCuenta;
    }

    public String getNumeroCuenta() { return numeroCuenta; }
}
