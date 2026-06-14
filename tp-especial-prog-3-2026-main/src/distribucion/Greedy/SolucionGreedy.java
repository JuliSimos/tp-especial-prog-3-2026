package distribucion.Greedy;

import distribucion.backtracking.CargaDeCamion;

import java.util.List;

public class SolucionGreedy {

    private List<CargaDeCamion> camionesCargados;
    private double pesoNoAsignado;
    private int candidatosConsiderados;

    public SolucionGreedy(List<CargaDeCamion> camionesCargados,
                          double pesoNoAsignado,
                          int candidatosConsiderados) {

        this.camionesCargados = camionesCargados;
        this.pesoNoAsignado = pesoNoAsignado;
        this.candidatosConsiderados = candidatosConsiderados;
    }

    public double getPesoNoAsignado() {
        return pesoNoAsignado;
    }

    public int getCandidatosConsiderados() {
        return candidatosConsiderados;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (CargaDeCamion carga : camionesCargados) {
            sb.append(carga).append("\n");
        }

        sb.append("Peso no asignado: ")
                .append(pesoNoAsignado)
                .append(" kg\n");

        sb.append("Candidatos considerados: ")
                .append(candidatosConsiderados)
                .append("\n");

        return sb.toString();
    }
}