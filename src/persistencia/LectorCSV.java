package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LectorCSV {

    public List<String[]> leerArchivo(String ruta) {
        List<String[]> datos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {
                datos.add(linea.split(";"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return datos;
    }
}