package persistencia;

import modelo.Paquete;

import java.util.ArrayList;
import java.util.List;

public class LectorDePaquetes {
    private static final String RUTA_ARCHIVOS =
            "src/persistencia/archivos/";

    public List<Paquete> cargar(String nombreArchivo) {
        String rutaCompleta = RUTA_ARCHIVOS + nombreArchivo;

        LectorCSV reader = new LectorCSV();
        List<String[]> datos = reader.leerArchivo(rutaCompleta);

        if(datos.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }

        int cantidadEsperada = Integer.parseInt(datos.get(0)[0]);

        List<Paquete> paquetes = new ArrayList<>();

        for (int i = 1; i < datos.size(); i++) {

            String[] fila = datos.get(i);

            int id = Integer.parseInt(fila[0]);
            String codigo = fila[1];
            double peso = Double.parseDouble(fila[2]);
            boolean contieneAlimentos = Integer.parseInt(fila[3]) == 1;
            int nivelUrgencia = Integer.parseInt(fila[4]);

            paquetes.add(
                    new Paquete(id, codigo, peso, contieneAlimentos, nivelUrgencia)
            );
        }

        if (paquetes.size() != cantidadEsperada) {
            throw new RuntimeException(
                    "Cantidad de camiones inválida. Esperados: "
                            + cantidadEsperada +
                            ", leídos: "
                            + paquetes.size()
            );
        }

        return paquetes;
    }
}
