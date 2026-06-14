package distribucion.Greedy;

import modelo.Camion;
import modelo.Paquete;
import persistencia.LectorDeCamiones;
import persistencia.LectorDePaquetes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Test_DistribucionGreedy {

    public static void main(String[] args) {

        LectorDeCamiones lectorCamiones =
                new LectorDeCamiones();

        LectorDePaquetes lectorPaquetes =
                new LectorDePaquetes();

        HashMap<String, Camion> camionesMap =
                lectorCamiones.cargar("Camiones.csv");

        HashMap<String, Paquete> paquetesMap =
                lectorPaquetes.cargar("Paquetes.csv");

        List<Camion> camiones =
                new ArrayList<>(camionesMap.values());

        List<Paquete> paquetes =
                new ArrayList<>(paquetesMap.values());

        DistribucionGreedy greedy =
                new DistribucionGreedy(
                        camiones,
                        paquetes);

        SolucionGreedy solucion =
                greedy.getDistribucionFinal();

        System.out.println("===== GREEDY =====");
        System.out.println(solucion);
    }
}