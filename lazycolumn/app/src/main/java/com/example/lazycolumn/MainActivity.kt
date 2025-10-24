package com.example.lazycolumn

// --- IMPORTS ---
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lazycolumn.ui.theme.LazycolumnTheme

// --- MODELO DE DATOS ---
data class Curso(
    val id: Int,
    val nombre: String,
    val descripcion: String
)

// --- ACTIVIDAD PRINCIPAL ---
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazycolumnTheme {
                ListSample4()
            }
        }
    }
}

@Composable
fun ListSample4(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Estados para los TextFields
    var nombreCurso by remember { mutableStateOf("") }
    var idCurso by remember { mutableStateOf("") }

    // Lista de cursos en estado (para que Compose pueda detectar los cambios)
    var cursos by remember {
        mutableStateOf(
            (1..100).map { i ->
                Curso(i, "Nombre $i", "Descripción $i")
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            label = { Text("Id") },
            value = idCurso,
            onValueChange = { idCurso = it },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            label = { Text("Nombre del curso") },
            value = nombreCurso,
            onValueChange = { nombreCurso = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            val index = idCurso.toIntOrNull()?.minus(1)
            if (index != null && index in cursos.indices) {
                val oldCurso = cursos[index]
                val newCurso = oldCurso.copy(nombre = nombreCurso)

                // Creamos una nueva lista para que Compose detecte el cambio
                cursos = cursos.toMutableList().apply {
                    this[index] = newCurso
                }

                // Mostrar confirmación
                Toast.makeText(context, "Curso modificado", Toast.LENGTH_SHORT).show()

                // Imprimir cambio en consola
                Log.d("ComposeTest", "Modificado: ${newCurso.id} -> ${newCurso.nombre}")

            } else {
                Toast.makeText(context, "Id inválido", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Modificar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            cursos.forEach {
                Log.d("ComposeTest", "Item: ${it.id} ${it.nombre}")
            }
        }) {
            Text("Ver Lista en Logcat")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(cursos, key = { it.id }) { curso ->
                Log.d("ComposeTest", "Renderizando Item ${curso.id}")
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .background(Color.Red)
                        .padding(8.dp),
                    text = "Item -> ${curso.id} | ${curso.nombre} | ${curso.descripcion}",
                    color = Color.White
                )
            }
        }
    }
}
