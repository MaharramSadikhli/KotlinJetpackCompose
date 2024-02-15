package com.example.kotlincryptoapijetpackcompose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlincryptoapijetpackcompose.model.CryptoModel
import com.example.kotlincryptoapijetpackcompose.service.CryptoApi
import com.example.kotlincryptoapijetpackcompose.ui.theme.KotlinCryptoApiJetpackComposeTheme
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL_API = "https://raw.githubusercontent.com/"

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinCryptoApiJetpackComposeTheme {
                CryptoScreen()
            }
        }
    }


}

@Composable
fun CryptoScreen() {

    var cryptoModels = remember {
        mutableStateListOf<CryptoModel>()
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL_API)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(CryptoApi::class.java)

    val call = service.getData()

    call.enqueue(object : retrofit2.Callback<List<CryptoModel>> {
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: retrofit2.Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    cryptoModels.addAll(it)
                }
            }

        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })


    Scaffold(
        topBar = { TopBar()},
        content = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(it)
            ) {
            }
        }
        )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Crypto App",
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                fontFamily = FontFamily.Monospace
                )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinCryptoApiJetpackComposeTheme {
        CryptoScreen()
    }
}

