package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    //Api
    val baseURL = "https://fcm.googleapis.com/fcm/send"
    // From firebase
    val serverKey = "key=" + "yourServerKey"
    val contentType = "application/json"
    //Obtained when user installs or re-installs the app(Check the logs), should be put into database
    private val userToken = "genratedUserToken"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val titleET = findViewById<EditText>(R.id.title)
        val messageET = findViewById<EditText>(R.id.message)
        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            //Toast.makeText(this, body, Toast.LENGTH_SHORT).show()
            val message = JSONObject()
            /*val notification = JSONObject()
            notification.put("body", messageET.text.toString())*/
            //Creating the jsonObject to be sent
            val data = JSONObject()
            data.put("title", titleET.text.toString())
            data.put("message", messageET.text.toString())
            message.put("to", userToken)
            message.put("data", data)
            Log.i("message", messageET.text.toString())
            sendNotification(message)
        }
    }

    private fun sendNotification(notification: JSONObject) {
        val jsonRequest = object : JsonObjectRequest(
            Request.Method.POST, baseURL, notification,

            Response.Listener { _ ->
                try {
                    Log.i("FCS", "response")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.i("FCSError", e.toString())
                }
            },
            Response.ErrorListener { error ->
                Log.i("FCS", error.toString())
            }) {
            @Throws(AuthFailureError::class)
            override fun getBodyContentType(): String {
                return "application/json"
            }
            override fun getHeaders(): Map<String, String> {
                val apiHeader = HashMap<String, String>()
                apiHeader["Authorization"] = serverKey
                apiHeader["Content-Type"] = contentType
                return apiHeader
            }
        }
        //Putting the jsonRequestObject Into Queue
        val queue = Volley.newRequestQueue(this)
        queue.add(jsonRequest)
    }
}
