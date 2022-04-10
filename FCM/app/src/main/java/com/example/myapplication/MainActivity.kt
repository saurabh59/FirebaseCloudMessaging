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
    val serverKey = "key=" + "AAAAyPSzGV0:APA91bGJc25z-7M-7ZXx_44mFX06R19BAsG7JxtfWR7qZYKkJPFLKh51Lgw4F0CEjKhR0SSydL8VJ8T15DDYusOpp3xFCF2PGXkFIJCTZ1s24sTmzFd_8eTpyiaOvWGYheu_kGErr39F"
    val contentType = "application/json"
    //Obtained when user installs or re-installs the app, should be put into database
    private val userToken = "epvnkDRxQzKw_fsmxhZ-Fe:APA91bESDjPmv8GffCK0Urdmdaa6oeFtvczPZwXsyfZFXGqp6FR73f_Yxht4YuUbYwLw3pPi-f9Dh_D448NSL8f9pKFMDkUZtPVaz2iIOynvtJJ3ko53vb_EwjxjZMOnvSQxJfzF2Jp6"
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