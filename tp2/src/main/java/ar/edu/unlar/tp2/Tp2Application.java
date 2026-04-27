package ar.edu.unlar.tp2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ar.edu.unlar.tp2.model.CuentaBancaria;
import ar.edu.unlar.tp2.service.CajeroService;
import ar.edu.unlar.tp2.ui.CajeroUI;

@SpringBootApplication
public class Tp2Application {

	public static void main(String[] args) {
		SpringApplication.run(Tp2Application.class, args);
		 // --- Crear el servicio ---
        CajeroService service = new CajeroService();

        // --- Crear las 3 cuentas ---
        CuentaBancaria cuenta1 = new CuentaBancaria("001-123456", "Juan Perez", 50000.00, "1234");
        CuentaBancaria cuenta2 = new CuentaBancaria("001-654321", "Maria Gomez", 15000.00, "5678");
        CuentaBancaria cuenta3 = new CuentaBancaria("001-111222", "Carlos Lopez", 8000.00, "9999");

        // --- Registrar cuentas en el sistema ---
        service.registrarCuenta(cuenta1);
        service.registrarCuenta(cuenta2);
        service.registrarCuenta(cuenta3);

        // --- Simular dia de operaciones ---
        System.out.println("=".repeat(60));
        System.out.println("   SIMULACION DE DIA DE OPERACIONES");
        System.out.println("=".repeat(60));

        // Sesion 1 - Juan Perez
        System.out.println("\n--- Sesion: Juan Perez ---");
        service.autenticar("001-123456", "1234");
        service.consultarSaldo();                          // transaccion 1
        service.depositar(5000.00);                        // transaccion 2
        service.extraer(2000.00);                          // transaccion 3
        service.transferir("001-654321", 3000.00);         // transaccion 4
        service.extraer(500.00);                           // transaccion 5
        service.mostrarHistorial();
        service.cerrarSesion();

        // Sesion 2 - Maria Gomez
        System.out.println("\n--- Sesion: Maria Gomez ---");
        service.autenticar("001-654321", "5678");
        service.consultarSaldo();                          // transaccion 6
        service.depositar(1000.00);                        // transaccion 7
        service.transferir("001-111222", 2000.00);         // transaccion 8
        service.extraer(500.00);                           // transaccion 9
        service.mostrarHistorial();
        service.cerrarSesion();

        // Sesion 3 - Carlos Lopez
        System.out.println("\n--- Sesion: Carlos Lopez ---");
        service.autenticar("001-111222", "9999");
        service.consultarSaldo();                          // transaccion 10
        service.depositar(200.00);                         // transaccion 11
        service.extraer(1000.00);                          // transaccion 12

        // --- Manejo de excepciones ---

        // Excepcion 1: Saldo insuficiente
        System.out.println("\n-- Intento de extraccion con saldo insuficiente --");
        service.extraer(50000.00);                         // transaccion 13 - SaldoInsuficienteException

        // Excepcion 2: Limite de extraccion excedido
        System.out.println("\n-- Intento de extraccion por encima del limite --");
        service.extraer(15000.00);                         // transaccion 14 - LimiteExtraccionExcedidoException

        service.depositar(300.00);                         // transaccion 15
        service.mostrarHistorial();
        service.cerrarSesion();

        // --- Excepcion 3: PIN invalido ---
        System.out.println("\n-- Intento de acceso con PIN incorrecto --");
        service.autenticar("001-123456", "0000");          // PinInvalidoException

        System.out.println("\n=".repeat(60));
        System.out.println("   FIN DE LA SIMULACION");
        System.out.println("=".repeat(60));

        // --- Iniciar cajero interactivo ---
        System.out.println("\nIniciando modo interactivo...\n");
        CajeroUI ui = new CajeroUI(service);
        ui.iniciar();
	}

}
