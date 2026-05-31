# tp-especial-prog-3-2026
## Estructura del Proyecto

```text
src/
├── distribucion/
│   ├── backtracking/
│   │   ├── CargaDeCamion.java
│   │   ├── DistribucionBacktracking.java
│   │   ├── SolucionBacktracking.java
│   │   └── Test_DistribucionBacktracking.java
│   └── DistribucionGreedy.java
│
├── modelo/
│   ├── Camion.java
│   └── Paquete.java
│
├── persistencia/
│   ├── archivos/
│   │   ├── Camiones.csv
│   │   ├── camiones_capacidad_refrigerada_insuficiente.csv
│   │   ├── camiones_restriccion_extrema.csv
│   │   └── Paquetes.csv
│   ├── LectorCSV.java
│   ├── LectorDeCamiones.java
│   ├── LectorDePaquetes.java
│   ├── TestCamiones.java
│   └── TestPaquetes.java
│
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
- En proceso...


## Solución con Backtracking – Asignación de Paquetes
### Problema

Se busca asignar todos los paquetes disponibles a una lista de camiones, minimizando el peso total de los paquetes que no pueden ser asignados, respetando las restricciones de capacidad y refrigeración.

### Representación del problema

- `List<Paquete> paquetes`: conjunto de paquetes a asignar
- `List<Camion> camiones`: lista de camiones disponibles

### Idea de la solución

Se prueban todas las combinaciones posibles de asignación de paquetes a camiones.

Para cada paquete, el algoritmo decide:

- Asignarlo a un camión compatible
- No asignarlo

La solución se construye de manera recursiva mediante backtracking.

### Estructura de la solución

La solución final se representa mediante la clase `SolucionBacktracking`:

- `List<CargaDeCamion> camionesCargados`: estado final de asignación
- `double pesoNoAsignado`: peso total de paquetes no asignados
- `int estadosGenerados`: cantidad de estados explorados por el algoritmo

Cada `CargaDeCamion` representa un camión con su lista de paquetes asignados y su peso actual.

### Estado durante la ejecución

Durante la ejecución se utilizan las siguientes variables:

- `indicePaquete`: indica qué paquete se está intentando asignar
- `cargaParcial`: estado actual de asignación de paquetes a camiones
- `pesoSinAsignarActual`: peso acumulado de los paquetes que aún no fueron asignados en ese estado

### Restricciones

Una asignación es válida si:

- El camión tiene capacidad suficiente (`tieneCapacidadPara(paquete)`)
- Si el paquete contiene alimentos, el camión debe ser refrigerado

``` java
public boolean puedeAsignarse(Paquete actual, CargaDeCamion carga)
```


### Generación de candidatos

Para cada paquete se generan las siguientes opciones:

- Intentar asignarlo a cada camión compatible
- No asignarlo

*Un camión se considera compatible si tiene capacidad disponible y cumple con el requisito de refrigeración en caso de ser necesario.*

### Caso base

El algoritmo finaliza cuando no quedan más paquetes por procesar.
En este punto, se compara la solución actual con la mejor solución conocida y, si es mejor, se actualiza.

``` java
if (indicePaquete >= paquetes.size()) {
    if (pesoSinAsignarActual < menorPesoSinAsignar) {
        // se guarda la mejor solución encontrada
    }
    return;
}
```

### Podas

Se aplica una poda simple basada en la mejor solución encontrada hasta el momento:
``` java
if (menorPesoSinAsignar == 0)
    return;
```
- Justificaicon: es el mejor caso posible, todos los paquetes fueron asignados. Por lo que no es necesario seguir explorando otras ramas, dado que no puede mejorarse el resultado