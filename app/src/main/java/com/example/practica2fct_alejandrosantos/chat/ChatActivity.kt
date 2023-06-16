package com.example.practica2fct_alejandrosantos.chat

import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.practica2fct_alejandrosantos.chat.Constants.OPEN_PAGE
import com.example.practica2fct_alejandrosantos.chat.Constants.RECIVE_ID
import com.example.practica2fct_alejandrosantos.chat.Constants.SEND_ID



import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica2fct_alejandrosantos.R
import com.example.practica2fct_alejandrosantos.ui.NotificacionAveriasActivity
import com.example.practica2fct_alejandrosantos.chat.Constants.AGENTE
import com.example.practica2fct_alejandrosantos.chat.Constants.AVERIAS
import com.example.practica2fct_alejandrosantos.chat.Constants.PREGUNTAS
import com.example.practica2fct_alejandrosantos.chat.Constants.SALIR
import com.example.practicasoporte.Chat.MessagingAdapter
import com.example.practica2fct_alejandrosantos.ui.PreguntasFrecuentesActivity
import kotlinx.coroutines.*

class ChatActivity : AppCompatActivity() {
    private val TAG = "ChatActivity"


    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Pedro", "Juan", "Valentina", "Isabella")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerView()

        clickEvents()


        customBotMessage("Hola!, Esta usted usando el chat virtual de Iberdrola.")


        customBotMessage("Por favor, eliga la opción que mas se adecue a su consulta: " +
                "\n 1º Consultar política de facturación " +
                "\n 2º Informar de una avería" +
                "\n 3º Sitio web de Iberdrola SL" +
                "\n 5º Chatear con un agente" +
                "\n 4º Salir" )
    }

    private fun clickEvents() {
        var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
        var et_message = findViewById<EditText>(R.id.et_message)
        var btn_send = findViewById<Button>(R.id.btn_send)

        //Enviar la consulta
        btn_send.setOnClickListener {
            sendMessage()
        }

        //
        et_message.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    rv_messages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //Si hay mensajes y se reabre el chat va hasta el final para ver el ultimo mensaje
        GlobalScope.launch {
            var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
            delay(100)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
        var et_message = findViewById<EditText>(R.id.et_message)
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Añadimos el mensaje a la lista local
            messagesList.add(Message(message, SEND_ID, timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main) {
                // Cogemos la respuesta
                val response = BotResponse.basicResponses(message)

                //La añaddimos a la lista local
                messagesList.add(Message(response, RECIVE_ID, timeStamp))

                //insertamos la respuesta en el adaptador
                adapter.insertMessage(Message(response, RECIVE_ID, timeStamp))

                var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
                //Sube la vista
                rv_messages.scrollToPosition(adapter.itemCount - 1)


                when (response) {
                    //inicia la pagina de iberdrola
                    OPEN_PAGE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        delay(4000)
                        site.data = Uri.parse("https://www.iberdrola.es/")
                        startActivity(site)

                    }
                    //Opcion 1 elegida
                    PREGUNTAS -> {
                        delay(3000)
                        val intent = Intent(this@ChatActivity, PreguntasFrecuentesActivity::class.java)
                        startActivity(intent)

                    }
                    //Opcion 2 elegida
                    AVERIAS -> {
                        delay(3000)
                        val intent = Intent(this@ChatActivity, NotificacionAveriasActivity::class.java)
                        startActivity(intent)

                    }
                    //Opcion 3 elegida
                    AGENTE -> {
                        delay(3000)
                        val random = (0..3).random()
                        customBotMessage("Hola! Soy ${botList[random]}, ¿Cómo puedo ayudarte?")
                    }
                    //Opcion 4 elegida
                    SALIR -> {
                        delay(4000)
                        finish()
                    }
                }
            }
        }
    }
    // Esto fuerza un mensaje al bot
    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                var rv_messages = findViewById<RecyclerView>(R.id.rv_messages)
                messagesList.add(Message(message, RECIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECIVE_ID, timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}