package com.example.keetab

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import util.Internet

lateinit var textbook_desc:TextView
lateinit var bookauthor_desc:TextView
lateinit var bookprice_desc:TextView
lateinit var rating_desc:TextView
lateinit var img_desc:ImageView
lateinit var descaboutbook:TextView
lateinit var progressLayout_desc:RelativeLayout
lateinit var progressbar_desc:ProgressBar
lateinit var favbut_desc:Button
lateinit var toolbar_desc:Toolbar
var bookid:String?="100"
var t="100"
class DescriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        toolbar_desc=findViewById(R.id.toolbar_desc)
        textbook_desc=findViewById(R.id.textbook_desc)
        bookauthor_desc=findViewById(R.id.bookauthor_desc)
        bookprice_desc=findViewById(R.id.bookprice_desc)
        rating_desc=findViewById(R.id.rating_desc)
        img_desc=findViewById(R.id.img_desc)
        descaboutbook=findViewById(R.id.descaboutbook)
        progressLayout_desc=findViewById(R.id.progressLayout_desc)
        progressLayout_desc.visibility= View.VISIBLE
        progressbar_desc=findViewById(R.id.progressbar_desc)
        favbut_desc=findViewById(R.id.favbut_desc)
        progressbar_desc.visibility=View.VISIBLE
        setSupportActionBar(toolbar_desc)
        supportActionBar?.title="Book Details"
        if (intent!=null){
            bookid=intent.getStringExtra("book_id")
        }else{
            finish()
            Toast.makeText(this, "Some error occured", Toast.LENGTH_SHORT).show()
        }
        if (bookid == t){
            Toast.makeText(this, "Some error ocuured", Toast.LENGTH_SHORT).show()
        }
        val queue= Volley.newRequestQueue(this@DescriptionActivity)
        val url="http://13.235.250.119/v1/book/get_book/"
        val jsonParams=JSONObject()
        jsonParams.put("book_id", bookid)
        if (Internet().checkConnectivity(this@DescriptionActivity)){
            val jsonRequest=object :JsonObjectRequest(Request.Method.POST,url,jsonParams,
                Response.Listener {
                try {
                    val success=it.getBoolean("success")
                    if (success){
                        val bookJSONObject=it.getJSONObject("book_data")
                        progressLayout_desc.visibility=View.GONE
                        textbook_desc.text=bookJSONObject.getString("name")
                        bookauthor_desc.text=bookJSONObject.getString("author")
                        bookprice_desc.text=bookJSONObject.getString("price")
                        rating_desc.text=bookJSONObject.getString("rating")
                        descaboutbook.text=bookJSONObject.getString("description")
                        Picasso.get().load(bookJSONObject.getString("image")).error(R.drawable.default_book_cover).into(img_desc)

                    }
                    else{
                        Toast.makeText(this@DescriptionActivity, "Some error ocuured not success", Toast.LENGTH_SHORT).show()
                    }
                }
                catch (e:Exception){
                    Toast.makeText(this@DescriptionActivity, "Some error ocuured catch", Toast.LENGTH_SHORT).show()
                }
            },Response.ErrorListener {
                    Toast.makeText(this@DescriptionActivity, "Volley error occured", Toast.LENGTH_SHORT).show()
            }){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["Content-type"]="application/json"
                    headers["token"]="9c7c036c352378"
                    return headers
                }
            }
            queue.add(jsonRequest)
        }
        else{
            val dialog= AlertDialog.Builder(this@DescriptionActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet not Found")
            dialog.setPositiveButton("Open Settings"){text,listner->
                val settings= Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settings)
                this@DescriptionActivity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listner->
                ActivityCompat.finishAffinity(this@DescriptionActivity)

            }
            dialog.create()
            dialog.show()
        }
    }
}