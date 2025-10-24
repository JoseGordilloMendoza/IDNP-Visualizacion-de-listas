# IDNP-Visualizacion-de-listas
iNTEGRANTES:
- Gordillo Mendoza, Jose Alonzo
- Phocco Tapia, Alejandro

## Ruta de archivo principal: lazycolumn/app/src/main/java/com/example/lazycolumn/MainActivity.kt

## 1. Uso de mutableStateOf para el estado de la lista
var cursos by remember {
    mutableStateOf(
        (1..100).map { i ->
            Curso(i, "Nombre $i", "Descripción $i")
        }
    )
}

La lista cursos se guarda como un estado (mutableStateOf), lo que permite que Compose observe los cambios en su valor y recomponga la interfaz automáticamente cuando cambie.

## 2. Reemplazo del elemento modificado creando una nueva lista
cursos = cursos.toMutableList().apply {
    this[index] = newCurso
}
Compose solo detecta cambios cuando la referencia del estado cambia.
Por ello, se crea una nueva lista a partir de la existente y se reemplaza el elemento modificado.

Esto provoca que cursos apunte a un nuevo objeto en memoria, lo que hace que Compose detecte el cambio y vuelva a renderizar la LazyColumn inmediatamente, sin necesidad de hacer scroll.

## 3. Copia inmutable del objeto modificado
val newCurso = oldCurso.copy(nombre = nombreCurso)

En lugar de modificar directamente el objeto, se crea una nueva instancia del curso usando la función copy().
Esto respeta el principio de inmutabilidad, clave para el correcto funcionamiento del sistema de composición de Jetpack Compose.

## 4. Confirmación visual y registro en consola
Toast.makeText(context, "Curso modificado", Toast.LENGTH_SHORT).show()
Log.d("ComposeTest", "Modificado: ${newCurso.id} -> ${newCurso.nombre}")

Se muestra un Toast en pantalla para confirmar la modificación y se imprime en el Logcat para verificar el cambio instantáneo, sin necesidad de desplazarse en la lista.

## 5. Uso de key en LazyColumn
items(cursos, key = { it.id }) { curso -> ... }

El parámetro key permite a Compose identificar de forma estable cada ítem dentro de la lista.
Esto evita re-renderizados innecesarios y ayuda a mantener la eficiencia al actualizar solo los elementos modificados.

### Resultado obtenido

- Los cambios realizados en los campos de texto se reflejan al instante en la LazyColumn.
- Compose recompone automáticamente solo los ítems afectados, manteniendo un rendimiento óptimo.
- Se puede verificar visualmente el cambio y por consola (Logcat) sin necesidad de interactuar con la lista.
