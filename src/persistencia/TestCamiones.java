package persistencia;

import modelo.Camion;

import java.util.List;

public class TestCamiones {
    public static void main(String[] args) {
        List<Camion> camiones = new LectorDeCamiones().cargar("Camiones.csv");

        for(Camion c : camiones){
            System.out.println(c);
        }
    }
}
