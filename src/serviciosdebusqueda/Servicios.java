package serviciosdebusqueda;

import modelo.Camion;
import modelo.Paquete;
import persistencia.LectorDeCamiones;
import persistencia.LectorDePaquetes;

import java.util.HashMap;
import java.util.List;

//Completar con las estructuras y métodos privados que se
//requieran.
public class Servicios {
    private HashMap<String,Paquete> paquetes;
    private HashMap<String,Camion> camiones;
    /**
     * Actualmente la complejidad es lineal O(N + M). Porque se realiza una única lectura secuencial de ambos archivos de texto,
     *  e insertar cada elemento en los HashMap principales tiene un costo promedio constante de O(1)
     */
    public Servicios(String pathCamiones, String pathPaquetes) {
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();
        LectorDeCamiones lectorCamiones = new LectorDeCamiones();

        this.paquetes = lectorPaquetes.cargar(pathPaquetes);
        this.camiones = lectorCamiones.cargar(pathCamiones);
    }


    /** Complejidad promedio O(1).
     * El HashMap usa una función hash para obtener el paquete por su clave, lo que toma tiempo constante en promedio.
     */
    public Paquete servicio1(String codigoPaquete) {
        return paquetes.get(codigoPaquete);
    }

    /*
     * Expresar la complejidad temporal del servicio 2.
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        return null;
    }


    /*
     * Expresar la complejidad temporal del servicio 3.
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        return null;
    }
}