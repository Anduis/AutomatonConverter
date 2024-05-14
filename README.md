[![es](https://img.shields.io/badge/lang-es-yellow.svg)](./README.md)
[![en](https://img.shields.io/badge/lang-en-red.svg)](./README.en.md)

## Introducción a los Lenguajes Regulares y la Teoría de Autómatas

Antes de adentrarnos en los detalles de este proyecto, tomemos un momento para explorar algunos conceptos fundamentales en la informática: los lenguajes regulares y la teoría de autómatas.

### ¿Qué son los Lenguajes Regulares?

En informática, un lenguaje regular es un lenguaje formal que puede ser descrito por una expresión regular. Estos lenguajes son fundamentales en diversas áreas de la informática, incluyendo el procesamiento de cadenas, la búsqueda de patrones y el diseño de compiladores. Los lenguajes regulares tienen muchas aplicaciones prácticas en tareas de procesamiento de texto, como buscar patrones específicos en archivos o validar la entrada del usuario.

### Teoría de Autómatas

La teoría de autómatas es el estudio de máquinas abstractas y modelos computacionales llamados autómatas, que se utilizan para reconocer patrones dentro de cadenas o secuencias de símbolos. Los autómatas pueden clasificarse en varios tipos según su potencia computacional, incluyendo autómatas finitos (AF), autómatas de pila (AP) y máquinas de Turing (MT).

### Autómatas Finitos (AF)

Los autómatas finitos, también conocidos como máquinas de estados finitos (MEF), son el tipo más simple de autómata. Consisten en un conjunto finito de estados, transiciones entre estos estados basadas en símbolos de entrada y estados inicial y de aceptación designados. Los autómatas finitos se utilizan para reconocer lenguajes regulares, lo que los convierte en herramientas esenciales en la informática teórica y aplicaciones prácticas.

---

#### Conversión de Expresión Regular a Autómata Finito No Determinista (AFND)
1. **Entrada**:
   - El usuario introduce una expresión regular a través de la consola.
   - El alfabeto (conjunto de caracteres) para la expresión regular está predefinido como "ab".

2. **Construcción de Thomson (Expresión Regular a AFND)**:
   - La expresión regular se procesa utilizando el algoritmo de construcción de Thomson, que convierte la expresión regular en un autómata finito no determinista (AFND).
   - Durante este proceso, la expresión regular se analiza y transforma en estados y transiciones en el AFND.

#### Conversión de Autómata Finito No Determinista (AFND) a Autómata Finito Determinista (AFD)
1. **Construcción de Thomson**:
   - El AFND obtenido a partir de la expresión regular se procesa más.

2. **Determinización (AFND a AFD)**:
   - El AFND obtenido se determiniza mediante un proceso que implica la creación de estados equivalentes del AFD.
   - Las transiciones épsilon se manejan durante este proceso de determinización.

#### Conversión de Autómata Finito Determinista (AFD) a Expresión Regular
1. **AFD a Expresión Regular**:
   - Después de obtener el AFD, el programa tiene como objetivo convertirlo nuevamente a una expresión regular.
   - Esto se logra mediante un proceso que involucra explorar las transiciones y estados del AFD para derivar la expresión regular equivalente.

### Ejecución del Programa
1. **Entrada**:
   - Ejecute el programa e ingrese una expresión regular a través de la consola.
   
2. **Salida**:
   - El programa muestra lo siguiente:
     - La expresión regular original.
     - El AFND correspondiente.
     - El AFD determinizado.
     - La expresión regular convertida obtenida del AFD.

### Descripción de Clases
1. **Clase Principal**:
   - Contiene el método principal para ejecutar el programa.
   - Toma la entrada del usuario para la expresión regular.
   - Instancia los objetos necesarios y llama a los métodos relevantes.

2. **Clase Thomson**:
   - Maneja el algoritmo de construcción de Thomson para convertir una expresión regular en un AFND.
   - Incluye métodos para procesar expresiones regulares y construir AFND.

3. **Clase Determinista**:
   - Realiza el proceso de determinización para convertir un AFND en un AFD.
   - Implementa métodos para la determinización y operaciones relacionadas con AFD.

### Uso
1. **Compilación y Ejecución**:
   - Compile y ejecute la clase `Main`.
   - Ingrese una expresión regular cuando se le solicite.
   
2. **Visualización de la Salida**:
   - El programa mostrará el AFND, el AFD y la expresión regular obtenida del AFD.