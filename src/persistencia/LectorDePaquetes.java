package persistencia;

import modelo.Paquete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LectorDePaquetes {
    private static final String RUTA_ARCHIVOS = "src/persistencia/archivos/";
    /**Controla que no haya códigos repetidos en el archivo con putIfAbsent y comprobando cantidadEsperada con cantidadCargados
 */
    public HashMap<String, Paquete> cargar(String nombreArchivo) {
        String rutaCompleta = RUTA_ARCHIVOS + nombreArchivo;

        LectorCSV lector = new LectorCSV();
        List<String[]> datos = lector.leerArchivo(rutaCompleta);

        if(datos.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        int cantidadEsperada = Integer.parseInt(datos.get(0)[0]);

        HashMap<String, Paquete> paquetesMap = new HashMap<>();

        for (int i = 1; i < datos.size(); i++) {

            String[] fila = datos.get(i);

            int id = Integer.parseInt(fila[0]);
            String codigo = fila[1];
            double peso = Double.parseDouble(fila[2]);
            boolean contieneAlimentos = Integer.parseInt(fila[3]) == 1;
            int nivelUrgencia = Integer.parseInt(fila[4]);

            paquetesMap.putIfAbsent(codigo, new Paquete(id, codigo, peso, contieneAlimentos, nivelUrgencia));
        }

        if (paquetesMap.size() != cantidadEsperada) {
            throw new RuntimeException(
                    "Cantidad de paquetes inválida. Esperados: "
                            + cantidadEsperada +
                            ", leídos: "
                            + paquetesMap.size()
            );
        }

        return paquetesMap;
    }
}
