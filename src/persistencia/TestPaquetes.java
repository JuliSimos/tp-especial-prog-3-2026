package persistencia;

import modelo.Paquete;

import java.util.HashMap;
import java.util.Map;

public class TestPaquetes {
    public static void main(String[] args) {
        HashMap<String, Paquete> paquetes = new LectorDePaquetes().cargar("Paquetes.csv");

        System.out.printf("%-5s %-10s %-10s %-12s %-10s%n", "ID", "CODIGO", "URGENCIA", "ALIMENTOS", "PESO");
        System.out.println("------------------------------------------------------------");

        for (Map.Entry<String, Paquete> e : paquetes.entrySet()) {
            Paquete p = e.getValue();

            System.out.printf("%-5d %-10s %-10d %-12s %-10.1f kg%n",
                    p.getIdPaquete(),
                    p.getCodigo(),
                    p.getNivelUrgencia(),
                    (p.getContieneAlimentos() ? "Sí" : "No"),
                    p.getPeso()
            );
        }
    }
}
