package persistencia;

import modelo.Camion;

import java.util.ArrayList;
import java.util.List;

public class LectorDeCamiones {
    private static final String RUTA_ARCHIVOS =
            "src/persistencia/archivos/";

    public List<Camion> cargar(String nombreArchivo) {
        String rutaCompleta = RUTA_ARCHIVOS + nombreArchivo;

        LectorCSV reader = new LectorCSV();
        List<String[]> datos = reader.leerArchivo(rutaCompleta);

        if(datos.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        int cantidadEsperada = Integer.parseInt(datos.get(0)[0]);

        List<Camion> camiones = new ArrayList<>();

        for (int i = 1; i < datos.size(); i++) {

            String[] fila = datos.get(i);

            int id = Integer.parseInt(fila[0]);
            String patente = fila[1];
            boolean refrigerado = Integer.parseInt(fila[2]) == 1;
            double cargaMaxima = Double.parseDouble(fila[3]);

            camiones.add(
                    new Camion(id, patente, refrigerado, cargaMaxima)
            );
        }

        if (camiones.size() != cantidadEsperada) {
            throw new RuntimeException(
                    "Cantidad de camiones inválida. Esperados: "
                            + cantidadEsperada +
                            ", leídos: "
                            + camiones.size()
            );
        }

        return camiones;
    }
}
