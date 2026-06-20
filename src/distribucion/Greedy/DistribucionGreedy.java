package distribucion.Greedy;

import distribucion.backtracking.CargaDeCamion;
import modelo.Camion;
import modelo.Paquete;

import java.util.ArrayList;
import java.util.List;

public class DistribucionGreedy {

    private List<Camion> camiones;
    private List<Paquete> paquetes;

    public DistribucionGreedy(List<Camion> camiones,
                              List<Paquete> paquetes) {

        this.camiones = camiones;
        this.paquetes = paquetes;
    }

    /*
     * Estrategia Greedy:
     *
     * Los candidatos son los paquetes pendientes de asignación.
     * En cada paso se selecciona el paquete de mayor peso.
     *
     * Luego se busca el primer camión donde sea factible
     * asignarlo respetando las restricciones.
     *
     * La decisión es irrevocable.
     */
    public SolucionGreedy getDistribucionFinal() {

        List<Paquete> candidatos =
                new ArrayList<>(this.paquetes);

        List<CargaDeCamion> cargas =
                new ArrayList<>();

        for (Camion c : camiones) {
            cargas.add(new CargaDeCamion(c));
        }

        double pesoNoAsignado = 0;
        int candidatosConsiderados = 0;

        while (!candidatos.isEmpty()) {

            Paquete actual = seleccionar(candidatos);

            candidatos.remove(actual);

            boolean asignado = false;

            int i = 0;

            while (i < cargas.size() && !asignado) {

                candidatosConsiderados++;

                if (puedeAsignarse(actual,
                        cargas.get(i))) {

                    cargas.get(i)
                            .asignarPaquete(actual);

                    asignado = true;
                }

                i++;
            }

            if (!asignado) {
                pesoNoAsignado += actual.getPeso();
            }
        }

        return new SolucionGreedy(
                cargas,
                pesoNoAsignado,
                candidatosConsiderados);
    }

    /*
     * Selecciona el paquete de mayor peso.
     */

    /*¿Por qué elegir el paquete más pesado?
    Porque el objetivo es minimizar el peso no asignado.
    Los paquetes más pesados son los más difíciles de ubicar y
    son los que más impactan en el peso total sin asignar,
    por lo que se priorizan primero.
    */

    private Paquete seleccionar(

            List<Paquete> candidatos) {

        Paquete mejor =
                candidatos.get(0);

        for (int i = 1;
             i < candidatos.size();
             i++) {

            if (candidatos.get(i).getPeso()
                    > mejor.getPeso()) {

                mejor = candidatos.get(i);
            }
        }

        return mejor;
    }

    /*
     * Verifica las restricciones.
     */
    private boolean puedeAsignarse(
            Paquete paquete,
            CargaDeCamion carga) {

        if (!carga.tieneCapacidadPara(paquete))
            return false;

        if (paquete.isContieneAlimentos()) {

            return carga.getCamion()
                    .isRefrigerado();
        }

        return true;
    }
}