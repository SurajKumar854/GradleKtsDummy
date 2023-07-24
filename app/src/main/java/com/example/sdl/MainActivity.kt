package com.example.sdl

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.example.sdl.ui.theme.SdlTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Driver


class MainActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SdlTheme {
                // A surface container using the 'background' color from the theme


                val client =
                    ApolloClient.Builder().serverUrl("http://90.90.90.82:9001/graphql").build()
                val driverList = remember {
                    mutableStateListOf<DriverQuery.GetDriver?>()
                }

                val ko = rememberCoroutineScope()
                ko.launch {
                    val items = client.query(DriverQuery()).execute()
                    for (i in items.data?.getDriver!!) {
                        driverList.add(i)
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {

                    LaunchedEffect(key1 = Unit) {

                        while (true) {
                            ko.launch {
                                val items = client.query(DriverQuery()).execute()
                                driverList.clear()
                                for (i in items.data?.getDriver!!) {
                                    driverList.add(i)
                                }
                            }
                            delay(500)
                        }

                    }

                Button(onClick = {
                    ko.launch {
                        val items = client.query(DriverQuery()).execute()
                        driverList.clear()
                        for (i in items.data?.getDriver!!) {
                            driverList.add(i)
                        }
                    }
                }) {
                    Text(text = "Refresh")
                }
                LazyColumn() {


                    items(driverList.size) { index ->

                        Column() {
                            Text(text = driverList?.get(index)?.name!!)
                            Text(text = driverList?.get(index)?.age.toString()!!)
                            Text(text = driverList?.get(index)?.mobile.toString()!!)
                            Spacer(modifier = Modifier.padding(8.dp))
                        }

                    }


                }

            }
        }

    }
}}





