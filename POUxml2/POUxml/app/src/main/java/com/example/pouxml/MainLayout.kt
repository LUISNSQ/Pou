package com.example.pouxml

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainLayout(nav: NavController, vm: PouViewModel, titulo: String, esquerda: String, direita: String, content: @Composable BoxScope.() -> Unit){
    val estado = vm.estado.value

    Box(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier.fillMaxWidth()){
            StatusBars(estado) // Status

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { nav.navigate(esquerda) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                }
                Text(titulo, style = MaterialTheme.typography.titleLarge)
                IconButton(onClick = { nav.navigate(direita) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null)
                }
            }
        }

        // é o pou e o botao do meio
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = { nav.navigate("closet") }) {
                Image(painter = painterResource(R.drawable.icon_closet),
                      contentDescription = "Closet", modifier = Modifier.size(60.dp))
            }
            // É para o botao do meio
            Spacer(modifier = Modifier.width(100.dp))

            IconButton(onClick = { nav.navigate("shop") }) {
                Image(painter = painterResource(R.drawable.icon_shop),
                    contentDescription = "Shop", modifier = Modifier.size(60.dp))
            }






        }



    }


}
