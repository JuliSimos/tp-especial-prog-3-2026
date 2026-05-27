package distribucion.backtracking;

import modelo.Camion;
import modelo.Paquete;
import serviciosdebusqueda.Servicios;

import java.util.ArrayList;
import java.util.List;

public class Test_DistribucionBacktracking {
    public static void main(String[] args) {
        System.out.println("--------------------------Backtracking-------------------------------- \n");

        System.out.println("Caso 1: solucion completa \n");
        Servicios serviceCaso1 = new Servicios("Camiones.csv", "Paquetes.csv");

        DistribucionBacktracking dist = new DistribucionBacktracking(serviceCaso1.getCamiones(), serviceCaso1.getPaquetes());

        SolucionBacktracking solucion = dist.getDistribucionFinal();

        System.out.println(solucion);

        System.out.println("-------------------------------------------------------------");
        System.out.println("Caso 2: Capacidad de refrigeracion reducida \n");
        Servicios serviceCaso2 = new Servicios("camiones_capacidad_refrigerada_insuficiente.csv", "Paquetes.csv");

        DistribucionBacktracking dist2 = new DistribucionBacktracking(serviceCaso2.getCamiones(), serviceCaso2.getPaquetes());

        SolucionBacktracking solucion2 = dist2.getDistribucionFinal();

        System.out.println(solucion2);

        System.out.println("-------------------------------------------------------------");
        System.out.println("Caso 3: Capacidad de refrigeracion escasa \n");
        Servicios serviceCaso3 = new Servicios("camiones_restriccion_extrema.csv", "Paquetes.csv");


        DistribucionBacktracking dist3 = new DistribucionBacktracking(serviceCaso3.getCamiones(), serviceCaso3.getPaquetes());

        SolucionBacktracking solucion3 = dist3.getDistribucionFinal();

        System.out.println(solucion3);
    }
}