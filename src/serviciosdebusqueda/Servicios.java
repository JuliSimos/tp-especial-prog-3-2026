package serviciosdebusqueda;

import modelo.Paquete;
import persistencia.LectorDePaquetes;

import java.util.*;

public class Servicios {

    private HashMap<String, Paquete> paquetesPorCodigo;

    // Estructuras auxiliares para resolver el servicio 2
    private List<Paquete> paquetesConAlimentos;
    private List<Paquete> paquetesSinAlimentos;

    // Estructura para resolver el servicio 3
    private List<List<Paquete>> paquetesPorUrgencia;

    /**
     * Complejidad temporal O(N).
     *
     * N representa la cantidad de paquetes
     *
     * Durante la inicialización se recorren todos los paquetes
     * una única vez para construir las estructuras auxiliares
     * utilizadas por los distintos servicios.
     */
    public Servicios(String pathPaquetes) {
        LectorDePaquetes lectorPaquetes = new LectorDePaquetes();

        this.paquetesPorCodigo = lectorPaquetes.cargar(pathPaquetes);

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
            return new ArrayList<>();
        }
        //Obtener la sublista de niveles por rango considerando que el rango final se incluye
        //SubList() no copia elementos, crea una vista sobre la lista original.
        List<List<Paquete>> lista = this.paquetesPorUrgencia.subList(urgenciaMinima, urgenciaMaxima + 1);

        List<Paquete> paquetes = new ArrayList<>();
        for (List<Paquete> listaPaquetes : lista) {
            paquetes.addAll(listaPaquetes);
        }
        return paquetes;
    }
}
