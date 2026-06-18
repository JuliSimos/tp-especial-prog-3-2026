package distribucion.backtracking;

import modelo.Camion;
import modelo.Paquete;
import persistencia.LectorDeCamiones;
import persistencia.LectorDePaquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test_DistribucionBacktracking {
    public static void main(String[] args) {

        LectorDeCamiones lectorCamiones = new LectorDeCamiones();
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();

        HashMap<String, Camion> camionesMap = lectorCamiones.cargar("Camiones.csv");
        HashMap<String, Paquete> paquetesMap = lectorPaquetes.cargar("Paquetes.csv");

        List<Camion> camiones = new ArrayList<>(camionesMap.values());
        List<Paquete> paquetes = new ArrayList<>(paquetesMap.values());

        System.out.println("--------------------------Solución con Backtracking-------------------------------- \n");

        DistribucionBacktracking dist = new DistribucionBacktracking(camiones, paquetes);

        SolucionBacktracking solucion = dist.getDistribucionFinal();

        System.out.println(solucion);
    }
}