package com.example.firebase

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.database.DatabaseReference

@Composable
fun ScreenFirebase(
    context: Context,
    databaseReference: DatabaseReference,
    navController: NavController
) {
    // Stan dla pól tekstowych wprowadzania danych użytkownika
    val name = remember { mutableStateOf(TextFieldValue()) }
    val index = remember { mutableStateOf(TextFieldValue()) }

    // Stan dla pola tekstowego wyświetlania odczytanych danych
    val displayData = remember { mutableStateOf("Brak danych do wyświetlenia") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = "Firebase", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = "Wpisz swoje imię") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = index.value,
            onValueChange = { index.value = it },
            placeholder = { Text(text = "Wpisz swój numer indeksu") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val student = Student(name.value.text, index.value.text)

                // Zapisz dane
                databaseReference.setValue(student)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Dane zapisane w Firebase", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Błąd zapisu danych do Firebase", Toast.LENGTH_LONG).show()
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Zapisz dane")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Odczytaj dane
                databaseReference.get()
                    .addOnSuccessListener { snapshot ->
                        // Pobierz imię i numer indeksu z bazy
                        val name = snapshot.child("studentName").getValue(String::class.java)
                        val index = snapshot.child("studentRollNumber").getValue(String::class.java)

                        // Zaktualizuj pole wyświetlania danych
                        displayData.value = "Imię: $name}\nNumer indeksu: $index"
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Błąd odczytu danych z Firebase", Toast.LENGTH_LONG).show()
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Odczytaj dane")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pole tekstowe do wyświetlenia odczytanych danych
        Text(
            text = displayData.value,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}
