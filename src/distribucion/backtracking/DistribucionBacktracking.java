package distribucion.backtracking;

import modelo.Camion;
import modelo.Paquete;

import java.util.ArrayList;
import java.util.List;

public class DistribucionBacktracking {
    private SolucionBacktracking solucion;
    private double menorPesoSinAsignar;
    private int estados;

    //Esctructuras para guardar los datos
    private List<Camion> camiones;
    private List<Paquete> paquetes;

    public DistribucionBacktracking(List<Camion> camiones, List<Paquete> paquetes) {
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.solucion = new SolucionBacktracking();
        this.menorPesoSinAsignar = this.getPesoTotal();
        this.estados = 0;
    }

    public SolucionBacktracking getDistribucionFinal() {
        this.solucion = new SolucionBacktracking();
        this.menorPesoSinAsignar = this.getPesoTotal(); //se inicializa con la carga total de todos los paquetes
        this.estados = 0;

        List<CargaDeCamion> cargaParcial = new ArrayList<>();
        //inicializo la cargaParcial con los camiones en el orden de la lista, para luego solo modificar los paquetes que entrgan
        for (Camion c : this.camiones) {
            cargaParcial.add(new CargaDeCamion(c));
        }

        double pesoSinAsignarActual = menorPesoSinAsignar; //en un principio valen lo mismo, luego se van modificando

        buscarSolucion(0, cargaParcial, pesoSinAsignarActual);
        this.solucion.setEstadosGenerados(this.estados); //actualizo la solucion con el total de estados generados

        return solucion;
    }

    private void buscarSolucion(int indicePaquete, List<CargaDeCamion> cargaParcial, double pesoSinAsignarActual) {
        this.estados++;
        if (indicePaquete >= paquetes.size()) {
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
        /*
            Poda 1:
            Si ya se encontró una solución con peso no asignado igual a 0,
            no existe una solución mejor
         */
        if (menorPesoSinAsignar == 0) {
            return;
        }
        /*
            Poda 2:
            Si aun asignando todos los paquetes que faltan procesar no logro mejorar
            la mejor solución encontrada hasta el momento, la rama se descarta
         */
        double pesoPaquetesFaltantes = 0;
        //calculo el peso total de los paquetes que faltan procesar
        for(int p = indicePaquete; p < paquetes.size(); p++){
            pesoPaquetesFaltantes +=  paquetes.get(p).getPeso();
        }
        double mejorPosible = pesoSinAsignarActual - pesoPaquetesFaltantes;

        if(mejorPosible >= menorPesoSinAsignar)
            return;


        Paquete actual = paquetes.get(indicePaquete);

        for (int c = 0; c < cargaParcial.size(); c++) {
            CargaDeCamion cargaActual = cargaParcial.get(c);
            if (this.puedeAsignarse(actual, cargaActual)) {

                //considerando el paquete
                cargaActual.asignarPaquete(actual);
                pesoSinAsignarActual = pesoSinAsignarActual - actual.getPeso();

                buscarSolucion(indicePaquete + 1, cargaParcial, pesoSinAsignarActual);

                cargaActual.quitarPaquete(actual);
                pesoSinAsignarActual = pesoSinAsignarActual + actual.getPeso();
            }
        }


        //sin considerar el paquete
        buscarSolucion(indicePaquete + 1, cargaParcial, pesoSinAsignarActual);

    }

    public boolean puedeAsignarse(Paquete actual, CargaDeCamion carga) {
        //me fijo si el camion tiene espacio
        if (!carga.tieneCapacidadPara(actual)) return false;

        //me fijo si el paquete es refrigerado y si el camion lo permite transportar
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
    public double getCapacidadLibreTotal(List<CargaDeCamion> cargas) {
        double libre = 0;
        for (CargaDeCamion c : cargas) {
            libre += c.getCapacidadDisponible();
        }
        return libre;
    }
}


