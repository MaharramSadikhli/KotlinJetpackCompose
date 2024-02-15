package com.example.kotlincryptoapijetpackcompose.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.kotlincryptoapijetpackcompose.model.CryptoModel
import com.example.kotlincryptoapijetpackcompose.service.CryptoApi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL_API = "https://raw.githubusercontent.com/"

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoScreen()
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

    call.enqueue(object: retrofit2.Callback<List<CryptoModel>>{
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: retrofit2.Response<List<CryptoModel>>
        ) {
            if(response.isSuccessful){
                response.body()?.let {
                    cryptoModels.addAll(it)
                    println(cryptoModels.get(index = 1).currency)
                }
            }

            }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoScreen()
}

