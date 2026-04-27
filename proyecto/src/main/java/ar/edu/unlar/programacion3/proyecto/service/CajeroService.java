package ar.edu.unlar.programacion3.proyecto.service;

import ar.edu.unlar.programacion3.proyecto.model.CuentaBancaria;
import ar.edu.unlar.programacion3.proyecto.exception.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CajeroService {
    private static final double MAX_EXTRACCION = 10000.0; // [cite: 29]

    public void depositar(CuentaBancaria cuenta, double monto) throws CuentaInactivaException {
        validarActiva(cuenta);
        if (monto > 0) {
            cuenta.setSaldo(cuenta.getSaldo() + monto);
            registrarAuditoria(cuenta, CuentaBancaria.TipoTransaccion.DEPOSITO, monto);
        }
    }

    public void extraer(CuentaBancaria cuenta, double monto) 
            throws SaldoInsuficienteException, LimiteExtraccionExcedidoException, CuentaInactivaException {
        validarActiva(cuenta);
        if (monto > MAX_EXTRACCION) throw new LimiteExtraccionExcedidoException("Supera los $10.000 permitidos");
        if (monto > cuenta.getSaldo()) throw new SaldoInsuficienteException("No tiene saldo suficiente");
        
        cuenta.setSaldo(cuenta.getSaldo() - monto);
        registrarAuditoria(cuenta, CuentaBancaria.TipoTransaccion.EXTRACCION, monto);
    }

    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto) 
            throws Exception {
        this.extraer(origen, monto); // Si falla, lanza excepción y no sigue
        this.depositar(destino, monto);
        registrarAuditoria(origen, CuentaBancaria.TipoTransaccion.TRANSFERENCIA, monto);
    }

    private void validarActiva(CuentaBancaria c) throws CuentaInactivaException {
        if (!c.isActiva()) throw new CuentaInactivaException("La cuenta está desactivada");
    }

    private void registrarAuditoria(CuentaBancaria c, CuentaBancaria.TipoTransaccion tipo, double monto) {
        StringBuilder sb = new StringBuilder(); // [cite: 58]
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        sb.append("[").append(fecha).append("] ")
          .append(tipo).append(": $").append(String.format("%.2f", monto))
          .append(" | Saldo: $").append(String.format("%.2f", c.getSaldo()));
        
        c.getHistorial().add(sb.toString()); // [cite: 54]
        if (c.getHistorial().size() > 10) c.getHistorial().remove(0); // [cite: 40]
    }
}