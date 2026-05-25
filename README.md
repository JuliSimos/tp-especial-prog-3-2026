# tp-especial-prog-3-2026
## Estructura del Proyecto

```text
src/
├── distribucion/
│   ├── DistribucionBacktracking.java
│   └── DistribucionGreedy.java
├── modelo/
│   ├── Camion.java
│   └── Paquete.java
├── persistencia/
│   ├── archivos/
│   │   ├── Camiones.csv
│   │   └── Paquetes.csv
│   ├── LectorCSV.java
│   ├── LectorDeCamiones.java
│   ├── LectorDePaquetes.java
└── serviciosdebusqueda/
    ├── Servicios.java
    └── TestServicios.java
```

## Servicios de busqueda

**Servicio 1**

**Requerimiento:** Dado un código de paquete (`String`), retornar toda la información del paquete asociado o `null` si no existe.

- **Decisión de diseño:** Al realizar la búsqueda mediante un identificador de tipo `String`, se descarta el uso de arreglos indexados por enteros. En su lugar, se optó por implementar un **`HashMap<String, Paquete>`**, donde la clave es el código del paquete.
- **Justificación:** Esto permite que la recuperación de cualquier paquete se realice mediante la función de hash en un tiempo promedio constante **$O(1)$**, optimizando al máximo las consultas.

**Servicio 2**

**Requerimiento:** Dado un booleano, retornar el listado de paquetes que contienen alimentos (`true`) o que no los contienen (`false`).

- **Decisión de diseño:** Para evitar recorrer toda la estructura principal en cada consulta (lo que costaría tiempo lineal $O(M)$ por cada llamada), se decidió **precalcular los datos en el constructor**. Se incorporaron dos estructuras auxiliares de tipo `List<Paquete>` (`paquetesConAlimentos` y `paquetesSinAlimentos`) que guardan las referencias a los objetos originales al momento de leer el archivo CSV.
- **Justificación:** Con esta estrategia de procesamiento previo, el método reduce su complejidad a tiempo constante **$O(1)$**, ya que ante una consulta solo evalúa el booleano y retorna la referencia a la lista correspondiente de manera instantánea.

**Servicio 3**

**Requerimiento:** Dados dos valores enteros (mínimo y máximo), retornar todos los paquetes cuyo nivel de urgencia se encuentre dentro de ese rango (inclusive).

- **Decisión de diseño:** Para resolver consultas por rango de manera eficiente, se incorporó una estructura auxiliar de tipo `TreeMap<Integer, List<Paquete>>`, donde cada clave representa un nivel de urgencia y el valor asociado contiene una lista con todos los paquetes de dicho nivel. Esta estructura se construye una única vez durante la inicialización del sistema, al momento de cargar los datos desde el archivo CSV.
- **Justificación:** El TreeMap mantiene sus claves ordenadas automáticamente, lo que permite obtener de forma eficiente únicamente los niveles de urgencia comprendidos entre los valores mínimo y máximo mediante el método `subMap()`. De esta manera se evita recorrer la totalidad de los paquetes en cada consulta. La obtención del submapa tiene complejidad **$O(log N)$** y la construcción del resultado requiere recorrer únicamente los **K** paquetes recuperados, logrando una complejidad total de **$O(log N + K)$**, donde **N** es la cantidad de niveles de urgencia almacenados y **K** la cantidad de paquetes retornados.
