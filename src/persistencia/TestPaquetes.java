package persistencia;

import modelo.Paquete;

import java.util.List;

public class TestPaquetes {
    public static void main(String[] args) {
        List<Paquete> paquetes = new LectorDePaquetes().cargar("Paquetes.csv");

        for(Paquete p : paquetes){
            System.out.println(p);
        }
    }
}
