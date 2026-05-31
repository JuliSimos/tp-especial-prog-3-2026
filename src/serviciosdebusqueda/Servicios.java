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

    // Estructura para resolver el servicio 3
    private List<List<Paquete>> paquetesPorUrgencia;

    /**
     * Complejidad temporal O(N + M).
     *
     * Donde:
     * N = cantidad de paquetes.
     * M = cantidad de camiones.
     *
     * - La carga de paquetes requiere O(N).
     * - La carga de camiones requiere O(M).
     * - La construcción de las estructuras auxiliares
     *   requiere recorrer todos los paquetes una vez: O(N).
     *
     * La complejidad final queda dominada por:
     * O(N + M).
     */
    public Servicios(String pathCamiones, String pathPaquetes) {
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();
        LectorDeCamiones lectorCamiones = new LectorDeCamiones();

        this.paquetesPorCodigo = lectorPaquetes.cargar(pathPaquetes);
        this.camionesPorPatente = lectorCamiones.cargar(pathCamiones);

        this.paquetesConAlimentos = new ArrayList<>();
        this.paquetesSinAlimentos = new ArrayList<>();

        this.paquetesPorUrgencia = new ArrayList<>(101);
        //Carga de listas auxiliares


        // Inicializo la estructura auxiliar para agrupar paquetes por nivel de urgencia
        for(int i = 0; i <= 100; i++){
            this.paquetesPorUrgencia.add(new ArrayList<>());
        }

        for (Paquete paquete : this.paquetesPorCodigo.values()) {
            if (paquete.isContieneAlimentos()) {
                this.paquetesConAlimentos.add(paquete);
            } else {
                this.paquetesSinAlimentos.add(paquete);
            }
        // Aprovecho el recorrido para ir agregando los paquetes por su nivel de urgencia
            int nivel = paquete.getNivelUrgencia();
            this.paquetesPorUrgencia.get(nivel).add(paquete);
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
     * Complejidad temporal O(P).
     *
     * Donde P es la cantidad de paquetes retornados.
     *
     * Se recorren los paquetes pertenecientes al rango de
     * urgencias solicitado para construir la lista resultado.
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        if (urgenciaMinima < 1 || urgenciaMaxima > 100 || urgenciaMinima > urgenciaMaxima) {
            //Obtener la sublista de niveles por rango considerando que el rango final se incluye
            //SubList() no copia elementos, crea una vista sobre la lista original.
            List<List<Paquete>> lista = this.paquetesPorUrgencia.subList(urgenciaMinima, urgenciaMaxima + 1);

            List<Paquete> paquetes = new ArrayList<>();
            for (List<Paquete> listaPaquetes : lista) {
                paquetes.addAll(listaPaquetes);
            }
            return paquetes;
        }
        return new ArrayList<>();
    }

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
