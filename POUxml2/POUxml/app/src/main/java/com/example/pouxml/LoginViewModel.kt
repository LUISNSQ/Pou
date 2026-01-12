package com.example.pouxml

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    var email = mutableStateOf("")
    var password = mutableStateOf("")
    var user = mutableStateOf("")

    fun login(onSucessed: () -> Unit, onError: (String) -> Unit) {
        if (email.value.isEmpty() || password.value.isEmpty()) return

        auth.signInWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener { onSucessed() }
            .addOnFailureListener { onError("Erro. Verifique os dados.") }
    }

    fun register(onSucesso: () -> Unit, onError: (String) -> Unit){
        if (email.value.isEmpty() || password.value.isEmpty() || user.value.isEmpty()) return

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener { resultado ->
                val uid = resultado.user?.uid ?: return@addOnSuccessListener

                val perfil = hashMapOf(
                    "uid" to uid,
                    "nomeUser" to user.value,
                    "roupaEquipada" to null,
                    "acessorioEquipado" to null
                )

                db.collection("jogadores").document(uid).set(perfil)
                    .addOnSuccessListener { onSucesso() }


            }
            .addOnFailureListener { onError("Erro no registo: ${it.message}") }
    }

}