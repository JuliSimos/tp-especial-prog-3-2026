package distribucion.backtracking;

import java.util.ArrayList;
import java.util.List;

public class SolucionBacktracking {
    private List<CargaDeCamion> camionesCargados;
    private double pesoNoAsignado;
    private int estadosGenerados;


    public SolucionBacktracking(){
        camionesCargados = new ArrayList<>();
        this.pesoNoAsignado = 0;
        this.estadosGenerados = 0;
    }

    public SolucionBacktracking(List<CargaDeCamion> camionesCargados, double pesoNoAsignado){
        this.camionesCargados = camionesCargados;
        this.pesoNoAsignado = pesoNoAsignado;
        this.estadosGenerados = 0;
    }

    public double getPesoNoAsignado(){
        return this.pesoNoAsignado;
    }

    public int getEstadosGenerados(){
        return this.estadosGenerados;
    }

    public void setEstadosGenerados(int e){
        this.estadosGenerados = e;
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

        sb.append("Estados generados: ")
                .append(estadosGenerados)
                .append("\n");

        return sb.toString();
    }

}
