package com.compose.adslib

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.adslib.facebook.FacebookAdList
import com.compose.adslib.google.GoogleAdList
import com.compose.adslib.ui.theme.AdsLibTheme
import com.compose.adslib.unity.UnityAdList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdsLibTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var adNetworks = listOf("Google", "Facebook", "Unity")

                    LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(10.dp)){
                        itemsIndexed(adNetworks){ _: Int, item: String ->

                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.Gray)
                                    .clickable {
                                        if(item.equals("Google")){
                                           startActivity(Intent(this@MainActivity, GoogleAdList::class.java))
                                        }else if (item.equals("Facebook")){
                                            startActivity(Intent(this@MainActivity, FacebookAdList::class.java))
                                        }else{
                                            startActivity(Intent(this@MainActivity, UnityAdList::class.java))
                                        }
                                    },
                                Alignment.Center
                            ) {
                                Text(text = item, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
                            }

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AdsLibTheme {
        Greeting("Android")
    }
}