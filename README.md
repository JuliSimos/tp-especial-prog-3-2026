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
│   │   ├── DistribucionGreedy.java
│   │   ├── SolucionGreedy.java
│   │   └── Test_DistribucionGreedy.java
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

**Requerimiento:** Dados dos valores enteros que representan un nivel de urgencia `mínimo` y `máximo`, retornar todos los paquetes cuyo nivel de urgencia se encuentre dentro de ese rango (inclusive).

- **Decisión de diseño:** Dado que los niveles de urgencia están acotados entre 1 y 100, se implementó una estructura auxiliar de tipo `List<List<Paquete>>`, donde cada posición representa un nivel de urgencia y almacena los paquetes asociados a dicho nivel. Esta estructura se construye una única vez durante la carga de datos.
- **Justificación:** Esta organización evita recorrer todos los paquetes ante cada consulta. Mediante `subList()` se obtiene una vista de las listas correspondientes al rango solicitado y luego se recopilan únicamente los paquetes pertenecientes a esos niveles. La complejidad queda determinada por la cantidad de paquetes retornados, resultando en un costo temporal de **$O(P)$**, donde **P** es la cantidad de paquetes que forman parte del resultado.


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
- `pesoSinAsignarActual`: peso total de los paquetes que permanecen sin asignar en el estado actual

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

#### Poda 1: Solución óptima encontrada

Si en algún momento se obtiene una solución con peso no asignado igual a 0, la búsqueda puede finalizar.
``` java
if(menorPesoSinAsignar == 0)
    return;
```
- **Justificación**: un peso no asignado de 0 representa el mejor resultado posible, ya que todos los paquetes fueron asignados. No existe una solución mejor.

#### Poda 2: Imposibilidad de mejorar la mejor solución actual
Antes de seguir explorando una rama, se calcula el peso total de los paquetes que todavía faltan procesar.
``` java
double pesoPaquetesFaltantes = 0;

for(int p = indicePaquete; p < paquetes.size(); p++){
    pesoPaquetesFaltantes += paquetes.get(p).getPeso(); 
}

double mejorPosible = pesoSinAsignarActual - pesoPaquetesFaltantes;

if(mejorPosible >= menorPesoSinAsignar) 
    return;
```
- **Justificación**: Se calcula cuál sería el menor peso sin asignar que podría obtenerse si todos los paquetes restantes fueran asignados. Si ese resultado no mejora la mejor solución encontrada hasta el momento, la rama se poda porque no puede producir una solución mejor.
___


## Solución con Greedy – Asignación de Paquetes

### Problema

Se busca asignar paquetes a los camiones disponibles minimizando el peso total de los paquetes que no pueden ser asignados, respetando las restricciones de capacidad y refrigeración.

### Idea de la solución

Se aplica una estrategia Greedy donde, en cada iteración, se selecciona el paquete de mayor peso que aún no fue procesado.

Luego se busca un camión donde sea factible asignarlo respetando las restricciones del problema. Si existe un camión válido, el paquete se asigna; en caso contrario, queda sin asignar.

Las decisiones tomadas son irrevocables y no se reconsideran posteriormente.

### Función de selección

Se selecciona el paquete de mayor peso disponible.

**Justificación:** los paquetes más pesados son los más difíciles de ubicar y son los que más impactan en el peso total no asignado. Por ello se priorizan primero.

### Restricciones

Un paquete puede asignarse a un camión únicamente si:

* El camión posee capacidad suficiente.
* Si el paquete contiene alimentos, el camión debe ser refrigerado.

La validación se realiza mediante el método:

```java
private boolean puedeAsignarse(Paquete paquete,
                               CargaDeCamion carga)
```

### Función objetivo

Minimizar el peso total de los paquetes no asignados.

### Métrica utilizada

Se contabiliza la cantidad de candidatos considerados durante la ejecución del algoritmo.

Esta métrica representa la cantidad de verificaciones realizadas para determinar si un paquete puede ser asignado a un camión respetando las restricciones del problema.


### Observaciones

En la ejecución realizada quedaron paquetes sin asignar porque requerían transporte refrigerado y los camiones que aún tenían capacidad disponible no eran refrigerados.

Esto muestra que la estrategia Greedy obtiene una solución válida, aunque no siempre la mejor posible.
