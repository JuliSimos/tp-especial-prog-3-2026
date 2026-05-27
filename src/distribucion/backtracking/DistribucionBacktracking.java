package distribucion.backtracking;

import modelo.Camion;
import modelo.Paquete;

import java.util.ArrayList;
import java.util.List;

public class DistribucionBacktracking {
    private SolucionBacktracking solucion;
    private double menorPesoSinAsignar;
    private int estados;

    //Esctructura para guardar los datos
    private List<Camion> camiones;
    private List<Paquete> paquetes;

    /**
     * Complejidad temporal: O(N),
     * donde N es la cantidad de paquetes.
     * Se inicializan los atributos de la clase y se calcula
     * el peso total de los paquetes recorriendo la lista una vez.
     */
    public DistribucionBacktracking(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.solucion = new SolucionBacktracking();
        this.menorPesoSinAsignar = this.getPesoTotal();
        this.estados = 0;
    }

    /**
     * Complejidad temporal: O((M + 1)^N)
     *
     * Donde N es la cantidad de paquetes y M la cantidad de camiones.
     *
     * El algoritmo genera todas las combinaciones posibles de asignación
     * de paquetes. Para cada paquete puede elegirse uno de los M camiones
     * o dejarlo sin asignar.
     *
     * Por este motivo, la cantidad de configuraciones posibles es del
     * orden de (M + 1)^N, resultando en una complejidad exponencial.
     */
    public SolucionBacktracking getDistribucionFinal() {
        this.solucion = new SolucionBacktracking();
        this.menorPesoSinAsignar = this.getPesoTotal();
        this.estados = 0;

        List<CargaDeCamion> cargaParcial = new ArrayList<>();
        //inicializo la cargaParcial con los camiones en el orden de la lista, para luego solo modificar los paquetes que entrgan
        for (Camion c : this.camiones) {
            cargaParcial.add(new CargaDeCamion(c));
        }

        double pesoSinAsignarActual = menorPesoSinAsignar; //en un principio valen lo mismo, luego se van modificando

        List<CargaDeCamion> cargaVacia = new ArrayList<>();
        for (Camion c: camiones){
            cargaVacia.add(new CargaDeCamion(c, new ArrayList<>()));
        }
        this.solucion = new SolucionBacktracking(cargaVacia, pesoSinAsignarActual);


        buscarSolucion(0, cargaParcial, pesoSinAsignarActual);
        this.solucion.setEstadosGenerados(this.estados); //actualizo la solucion con el tota de estados generados

        return solucion;
    }

    private void buscarSolucion(int indexPaquete, List<CargaDeCamion> cargaParcial, double pesoSinAsignarActual) {
        this.estados++;
        if (indexPaquete >= paquetes.size()) {
            if (pesoSinAsignarActual < menorPesoSinAsignar) {

                List<CargaDeCamion> nuevaSolucion = new ArrayList<>();

                for (CargaDeCamion c : cargaParcial) {
                    List<Paquete> paquetes = c.getPaquetes();
                    nuevaSolucion.add(new CargaDeCamion(c.getCamion(), paquetes));
                }

                this.solucion = new SolucionBacktracking(nuevaSolucion, pesoSinAsignarActual);
                menorPesoSinAsignar = pesoSinAsignarActual;
            }

            return;
        }

        if (menorPesoSinAsignar == 0)
            return;

        Paquete actual = paquetes.get(indexPaquete);

        for (int c = 0; c < cargaParcial.size(); c++) {
            CargaDeCamion cargaActual = cargaParcial.get(c);
            if (this.puedeAsignarse(actual, cargaActual)) {

                //considerando el paquete
                cargaActual.asignarPaquete(actual);
                pesoSinAsignarActual = pesoSinAsignarActual - actual.getPeso();

                buscarSolucion(indexPaquete + 1, cargaParcial, pesoSinAsignarActual);

                cargaActual.quitarPaquete(actual);
                pesoSinAsignarActual = pesoSinAsignarActual + actual.getPeso();
            }
        }
        //sin considerar el paquete
        buscarSolucion(indexPaquete + 1, cargaParcial, pesoSinAsignarActual);

    }

    public boolean puedeAsignarse(Paquete actual, CargaDeCamion carga) {
        //me fijo si el camion tiene espacio
        //me fijo si el paquete es refrigerado y si el camion lo permite transportar
        if (!carga.tieneCapacidadPara(actual)) return false;

        if (actual.isContieneAlimentos())
            return carga.getCamion().isRefrigerado();

        return true;
    }

    public double getPesoTotal() {
        double suma = 0;
        for (Paquete p : paquetes) {
            suma += p.getPeso();
        }
        return suma;
    }
}


