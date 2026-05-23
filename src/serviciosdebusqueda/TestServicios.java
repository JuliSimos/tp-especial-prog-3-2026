package serviciosdebusqueda;

public class TestServicios {
    public static void main(String[] args) {
        Servicios servicios = new Servicios("Camiones.csv", "Paquetes.csv");

        System.out.println("--- SERVICIO 1 ---");
        System.out.println("Existe P001: " + servicios.servicio1("P001"));
        System.out.println("No existe P999: " + servicios.servicio1("P999"));

        System.out.println("\n--- SERVICIO 2 ---");
        System.out.println("Paquetes con alimentos: " + servicios.servicio2(true));
        System.out.println("Paquetes sin alimentos: " + servicios.servicio2(false));

        System.out.println("\n--- SERVICIO 3 ---");
    }
}
