package serviciosdebusqueda;

import modelo.Camion;
import modelo.Paquete;
import persistencia.LectorDeCamiones;
import persistencia.LectorDePaquetes;

import java.util.*;

// Completar con las estructuras y métodos privados que se requieran.
public class Servicios {

    private HashMap<String, Paquete> paquetesPorCodigo;
    private HashMap<String, Camion> camionesPorPatente; //No se si inicializar los camiones directamente en una lista y no en HashMap

    // Estructuras auxiliares para resolver el servicio 2
    private List<Paquete> paquetesConAlimentos;
    private List<Paquete> paquetesSinAlimentos;

    // Estructuar auxiliar para resolver el servicio 3
    private TreeMap<Integer, List<Paquete>> arbolDeUrgencias;


    /**
     * Complejidad temporal O(N log N + M).
     *
     * Donde:
     * N = cantidad de paquetes.
     * M = cantidad de camiones.
     *
     * 1) Lectura de archivos:
     * - Cargar los paquetes en el HashMap requiere O(N).
     * - Cargar los camiones en el HashMap requiere O(M).
     *
     * 2) Construcción de estructuras auxiliares:
     * - Recorrer todos los paquetes para separarlos en las listas
     *   paquetesConAlimentos y paquetesSinAlimentos requiere O(N).
     *
     * - Recorrer todos los paquetes para indexarlos por nivel de urgencia
     *   en el TreeMap requiere O(log N) por inserción en el peor caso,
     *   resultando en O(N log N).
     *
     * Sumando todos los costos:
     * O(N) + O(M) + O(N) + O(N log N)
     *
     * La complejidad final queda dominada por:
     * O(N log N + M).
     */
    public Servicios(String pathCamiones, String pathPaquetes) {
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();
        LectorDeCamiones lectorCamiones = new LectorDeCamiones();

        this.paquetesPorCodigo = lectorPaquetes.cargar(pathPaquetes);
        this.camionesPorPatente = lectorCamiones.cargar(pathCamiones);

        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();

        this.arbolDeUrgencias = new TreeMap<>();

        //Carga de listas auxiliares

        for (Paquete paquete : this.paquetesPorCodigo.values()) {
            if (paquete.isContieneAlimentos()) {
                this.paquetesConAlimentos.add(paquete);
            } else {
                this.paquetesSinAlimentos.add(paquete);
            }
        }

        for (Paquete paquete : this.paquetesPorCodigo.values()) {
            // Si no existe la lista para esa urgencia, la crea.
            arbolDeUrgencias.computeIfAbsent(paquete.getNivelUrgencia(), k -> new ArrayList<>()).add(paquete);
        }
    }


    /**
     * Complejidad promedio O(1).
     * El HashMap usa una función hash para obtener el paquete por su clave, lo que toma tiempo constante en promedio
     */
    public Paquete servicio1(String codigoPaquete) {
        return paquetesPorCodigo.get(codigoPaquete);
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

    //Metodos necesarios para resolver parte 2: distribucion de paquetes en camiones
    /**
     * Complejidad temporal O(N).
     * Se construye una nueva lista copiando los N paquetes almacenados
     * en el HashMap.
     */
    public List<Paquete> getPaquetes() {
        return new ArrayList<>(this.paquetesPorCodigo.values());
    }

    /**
     * Complejidad temporal O(M).
     * Se construye una nueva lista copiando los M camiones almacenados
     * en el HashMap.
     */
    public List<Camion> getCamiones() {
        return new ArrayList<>(this.camionesPorPatente.values());
    }
}
