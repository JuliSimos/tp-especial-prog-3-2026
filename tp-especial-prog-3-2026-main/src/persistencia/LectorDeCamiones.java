package persistencia;

import modelo.Camion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LectorDeCamiones {
    private static final String RUTA_ARCHIVOS = "src/persistencia/archivos/";

    /**Controla que no haya códigos repetidos en el archivo con putIfAbsent y comprobando cantidadEsperada con cantidadCargados
     */
    public HashMap<String, Camion> cargar(String nombreArchivo) {
        String rutaCompleta = RUTA_ARCHIVOS + nombreArchivo;

        LectorCSV lector = new LectorCSV();
        List<String[]> datos = lector.leerArchivo(rutaCompleta);

        if(datos.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        int cantidadEsperada = Integer.parseInt(datos.get(0)[0]);

        HashMap<String, Camion> camionesMap = new HashMap<>();

        for (int i = 1; i < datos.size(); i++) {

            String[] fila = datos.get(i);

            int id = Integer.parseInt(fila[0]);
            String patente = fila[1];
            boolean refrigerado = Integer.parseInt(fila[2]) == 1;
            double cargaMaxima = Double.parseDouble(fila[3]);
            camionesMap.putIfAbsent(patente, new Camion(id, patente, refrigerado, cargaMaxima));
        // no se si colocar acá la actualizacion a las estructuras auxiliares para reducir tiempos de busqueda
        }

        if (camionesMap.size() != cantidadEsperada) {
            throw new RuntimeException(
                    "Cantidad de camiones inválida. Esperados: "
                            + cantidadEsperada +
                            ", leídos: "
                            + camionesMap.size()
            );
        }

        return camionesMap;
    }
}
