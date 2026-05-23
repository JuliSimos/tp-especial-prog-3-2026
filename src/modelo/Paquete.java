package modelo;

public class Paquete {
    private int idPaquete;
    private String codigo;
    private double peso; //en kg
    private boolean contieneAlimentos; // 1 si, 0 no
    private int nivelUrgencia; // del 1 al 100


    public Paquete(int id, String cod, double peso, boolean contieneAlimentos, int nivelUrgencia) {
        this.idPaquete = id;
        this.codigo = cod;
        this.peso = peso;
        this.contieneAlimentos = contieneAlimentos;
        this.nivelUrgencia = nivelUrgencia;
    }
    public String getCodigo() {
        return codigo;
    }

    public boolean getContieneAlimentos() {
        return contieneAlimentos;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public int getNivelUrgencia() {
        return nivelUrgencia;
    }

    public double getPeso() {
        return peso;
    }
    public String toString(){
        return "[ id: " + this.idPaquete + ", codigo: " + this.codigo + ", peso: " + this.peso + ", contieneAlimentos: " + this.contieneAlimentos + ", nivelUrgencia: " + this.nivelUrgencia + " ]";
    }
}