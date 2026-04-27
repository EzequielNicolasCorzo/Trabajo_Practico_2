package ar.edu.unlar.tp2.ui;

import ar.edu.unlar.tp2.service.CajeroService;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CajeroUI {

    private final CajeroService service;
    private final Scanner scanner;

    public CajeroUI(CajeroService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        System.out.println("==============================");
        System.out.println("    CAJERO AUTOMATICO");
        System.out.println("==============================");

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    autenticar();
                    break;
                case 2:
                    if (service.getCuentaActual() != null) {
                        menuOperaciones();
                    } else {
                        System.out.println("Debe autenticarse primero.");
                    }
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        }
        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println("1. Iniciar sesion");
        System.out.println("2. Operar");
        System.out.println("0. Salir");
        System.out.println("------------------------------");
        System.out.print("Seleccione una opcion: ");
    }

    private void menuOperaciones() {
        boolean volver = false;
        while (!volver) {
            mostrarMenuOperaciones();
            int opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    realizarDeposito();
                    break;
                case 2:
                    realizarExtraccion();
                    break;
                case 3:
                    realizarTransferencia();
                    break;
                case 4:
                    service.consultarSaldo();
                    break;
                case 5:
                    service.mostrarHistorial();
                    break;
                case 6:
                    service.cerrarSesion();
                    volver = true;
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion invalida. Intente nuevamente.");
                    break;
            }
        }
    }

    private void mostrarMenuOperaciones() {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println("Titular: " + service.getCuentaActual().getTitular());
        System.out.println("Cuenta:  " + service.getCuentaActual().getNumeroCuenta());
        System.out.println("------------------------------");
        System.out.println("1. Depositar");
        System.out.println("2. Extraer");
        System.out.println("3. Transferir");
        System.out.println("4. Consultar saldo");
        System.out.println("5. Ver historial");
        System.out.println("6. Cerrar sesion");
        System.out.println("0. Salir");
        System.out.println("------------------------------");
        System.out.print("Seleccione una opcion: ");
    }

    private void autenticar() {
        System.out.print("Numero de cuenta: ");
        String numeroCuenta = scanner.next();
        System.out.print("PIN: ");
        String pin = scanner.next();
        service.autenticar(numeroCuenta, pin);
    }

    private void realizarDeposito() {
        System.out.print("Monto a depositar: $");
        double monto = leerMonto();
        if (monto > 0) {
            service.depositar(monto);
        }
    }

    private void realizarExtraccion() {
        System.out.print("Monto a extraer: $");
        double monto = leerMonto();
        if (monto > 0) {
            service.extraer(monto);
        }
    }

    private void realizarTransferencia() {
        System.out.print("Numero de cuenta destino: ");
        String destino = scanner.next();
        System.out.print("Monto a transferir: $");
        double monto = leerMonto();
        if (monto > 0) {
            service.transferir(destino, monto);
        }
    }

    private int leerOpcion() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un numero valido.");
            scanner.nextLine();
            return -1;
        }
    }

    private double leerMonto() {
        try {
            double monto = scanner.nextDouble();
            if (monto <= 0) {
                System.out.println("El monto debe ser mayor a cero.");
                return -1;
            }
            return monto;
        } catch (InputMismatchException e) {
            System.out.println("Ingrese un monto valido.");
            scanner.nextLine();
            return -1;
        }
    }
}