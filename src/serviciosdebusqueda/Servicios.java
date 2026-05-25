package serviciosdebusqueda;

import modelo.Camion;
import modelo.Paquete;
import persistencia.LectorDeCamiones;
import persistencia.LectorDePaquetes;

import java.util.*;

//Completar con las estructuras y métodos privados que se requieran.
public class Servicios {

    private HashMap<String, Paquete> paquetes;
    private HashMap<String, Camion> camiones;

    // Estructuras auxiliares para resolver el servicio 2
    private List<Paquete> paquetesConAlimentos;
    private List<Paquete> paquetesSinAlimentos;

    //Estrcturas auxiliares para resolver parte 2
    private List<Camion> camionesConRefrigeracion;
    private List<Camion> camionesSinRefrigeracion;

    //Estructuar auxiliar para resolver el servicio 3
    private TreeMap<Integer, List<Paquete>> arbolDeUrgencias;


    /**
     * Complejidad temporal O(N log N + M).
     * Donde N es la cantidad de paquetes y M la cantidad de camiones.
     *
     * Camiones:
     * La lectura y carga de los M camiones requiere O(M).
     * Luego, se recorren una vez para clasificarlos en las listas
     * de camiones con y sin refrigeración, lo que requiere O(M).
     *
     * Paquetes:
     * La lectura y carga de los N paquetes requiere O(N).
     * Luego, se recorren para construir las estructuras auxiliares:
     * agregarlos a las listas toma O(1) por paquete,
     * mientras que insertarlos en el TreeMap toma O(log N) por operación.
     * En total, el procesamiento e indexación de paquetes requiere O(N log N).
     */
    public Servicios(String pathCamiones, String pathPaquetes) {
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();
        LectorDeCamiones lectorCamiones = new LectorDeCamiones();

        this.paquetes = lectorPaquetes.cargar(pathPaquetes);
        this.camiones = lectorCamiones.cargar(pathCamiones);

        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();

        this.camionesConRefrigeracion = new ArrayList<>();
        this.camionesSinRefrigeracion = new ArrayList<>();

        this.arbolDeUrgencias = new TreeMap<>();

        //Carga de listas auxiliares

        for (Paquete paquete : this.paquetes.values()) {
            if (paquete.isContieneAlimentos()) {
                this.paquetesConAlimentos.add(paquete);
            } else {
                this.paquetesSinAlimentos.add(paquete);
            }
        }

        for (Camion camion : this.camiones.values()) {
            if (camion.isRefrigerado()){
                this.camionesConRefrigeracion.add(camion);
            }else {
                this.camionesSinRefrigeracion.add(camion);
            }
        }

        for (Paquete paquete : this.paquetes.values()) {
            // Si no existe la lista para esa urgencia, la crea.
            arbolDeUrgencias.computeIfAbsent(paquete.getNivelUrgencia(), k -> new ArrayList<>()).add(paquete);
        }
    }


    /**
     * Complejidad promedio O(1).
     * El HashMap usa una función hash para obtener el paquete por su clave, lo que toma tiempo constante en promedio
     */
    public Paquete servicio1(String codigoPaquete) {
        return paquetes.get(codigoPaquete);
    }

    /**
     * Complejidad de tiempo constante O(1).
     * El método solo evalúa la condición
     * y retorna la referencia de la lista precalculada, sin recorrer los paquetes
     */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        if (contieneAlimentos) {
            return paquetesConAlimentos;
        } else {
            return paquetesSinAlimentos;
        }

    }


    /**
     * Complejidad temporal O(log N + K).
     * Donde N es la cantidad de niveles de urgencia almacenados y K la cantidad
     * de paquetes recuperados.
     * La obtención del submapa correspondiente al rango solicitado toma O(log N)
     * y la construcción del resultado requiere recorrer los K paquetes encontrados.
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> paquetes = new ArrayList<>();

        NavigableMap<Integer, List<Paquete>> subArbol = arbolDeUrgencias.subMap(urgenciaMinima, true, urgenciaMaxima, true);

        for (List<Paquete> listaPorUrgencia : subArbol.values()) {
            paquetes.addAll(listaPorUrgencia);
        }
        return paquetes;
    }
}
