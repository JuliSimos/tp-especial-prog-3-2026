package modelo;

public class Camion {
    private int idCamion;
    private String patente;
    private boolean refrigerado; //1 si, 0 no
    private double cargaMaxima; //en KG

    public Camion(int id, String patente, boolean refrigerado, double cargaMaxima) {
        this.idCamion = id;
        this.patente = patente;
        this.refrigerado = refrigerado;
        this.cargaMaxima = cargaMaxima;
    }

    public double getCargaMaxima() {
        return cargaMaxima;
    }


    public int getIdCamion() {
        return this.idCamion;
    }


    public String getPatente() {
        return patente;
    }


    public boolean isRefrigerado() {
        return refrigerado;
    }

    public String toString(){
        return "[ id: " + this.idCamion + ", patente: " + this.patente + ", refrigerado: " + this.refrigerado +  ",  cargaMaxima: " + this.cargaMaxima + "]";
    }

}
