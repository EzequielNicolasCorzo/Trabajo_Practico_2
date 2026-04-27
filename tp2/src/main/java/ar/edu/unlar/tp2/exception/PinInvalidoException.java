package ar.edu.unlar.tp2.exception;

public class PinInvalidoException extends Exception {

    private final int intentosRestantes;

    public PinInvalidoException(int intentosRestantes) {
        super(String.format(
            "PIN incorrecto. Intentos restantes: %d",
            intentosRestantes
        ));
        this.intentosRestantes = intentosRestantes;
    }

    public int getIntentosRestantes() { return intentosRestantes; }
}