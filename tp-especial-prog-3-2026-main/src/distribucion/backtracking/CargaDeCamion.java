package distribucion.backtracking;

import modelo.Camion;
import modelo.Paquete;

import java.util.ArrayList;
import java.util.List;

public class CargaDeCamion {
    private Camion camion;
    private List<Paquete> paquetes;
    private double pesoActual;


    public CargaDeCamion(Camion camion) {
        this.camion = camion;
        this.paquetes = new ArrayList<>();
        this.pesoActual = 0;
    }
    public CargaDeCamion(Camion camion, List<Paquete> paquetes) {
        this.camion = camion;
        this.paquetes = new ArrayList<>(paquetes);
        for(Paquete p: paquetes){
            this.pesoActual += p.getPeso();
        }
    }

    public Camion getCamion() {
        return camion;
    }

    public List<Paquete> getPaquetes() {
        return new ArrayList<>(paquetes);
    }
//No restrige el ingreso del paquete aunque podria, por ahora se frena x afuera
    public void asignarPaquete(Paquete p) {
        this.paquetes.add(p);
        this.pesoActual += p.getPeso();
    }
    public  void quitarPaquete(Paquete p){
        if(paquetes.remove(p)){
            this.pesoActual -= p.getPeso();
        }
    }

    public double getCapacidadDisponible(){
        return  this.camion.getCargaMaxima() - this.pesoActual;
    }

    public boolean tieneCapacidadPara(Paquete paquete){
        return paquete.getPeso() <= getCapacidadDisponible();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Camion: ")
                .append(camion.getPatente())
                .append(" | Capacidad: ")
                .append(camion.getCargaMaxima())
                .append(" kg | Refrigerado: ")
                .append(camion.isRefrigerado())
                .append("\n\n");

        if (paquetes.isEmpty()) {
            sb.append("   (sin paquetes asignados)\n");
        } else {
            for (Paquete p : paquetes) {
                sb.append("   - ")
                        .append(p.getCodigo())
                        .append(" (")
                        .append(p.getPeso())
                        .append(" kg)")
                        .append(" - alimentos: ")
                        .append(p.isContieneAlimentos())
                        .append("\n");
            }
        }

        return sb.toString();
    }
}

