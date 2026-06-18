package persistencia;

import modelo.Camion;

import java.util.HashMap;
import java.util.Map;

public class TestCamiones {
    public static void main(String[] args) {
        HashMap<String, Camion> camiones = new LectorDeCamiones().cargar("Camiones.csv");

        System.out.printf("%-6s %-12s %-14s %-10s%n", "ID", "PATENTE", "REFRIGERADO", "CARGA MÁXIMA");
        System.out.println("-----------------------------------------------------------");

        for (Map.Entry<String, Camion> e : camiones.entrySet()) {
            Camion c = e.getValue();

            System.out.printf("%-6d %-12s %-14s %-10.1f kg%n",
                    c.getIdCamion(),
                    c.getPatente(),
                    (c.isRefrigerado() ? "Sí" : "No"),
                    c.getCargaMaxima()
            );
        }
    }
}
