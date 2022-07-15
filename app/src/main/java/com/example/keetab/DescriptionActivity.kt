package com.example.keetab

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import database.BookDatabase
import database.BookEntity
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
                        val bookurl=bookJSONObject.getString("image")
                        Picasso.get().load(bookJSONObject.getString("image")).error(R.drawable.default_book_cover).into(img_desc)
                        textbook_desc.text=bookJSONObject.getString("name")
                        bookauthor_desc.text=bookJSONObject.getString("author")
                        bookprice_desc.text=bookJSONObject.getString("price")
                        rating_desc.text=bookJSONObject.getString("rating")
                        descaboutbook.text=bookJSONObject.getString("description")

                        val bookEntity=BookEntity(
                            bookid?.toInt() as Int,
                            textbook_desc.text.toString(),
                            bookauthor_desc.text.toString(),
                            bookprice_desc.text.toString(),
                            rating_desc.text.toString(),
                            descaboutbook.text.toString(),
                            bookurl
                        )
                        val checkfav=DBAsynctask(applicationContext,bookEntity,1).execute()
                        val isFav= checkfav.get()
                        if (isFav){
                            favbut_desc.text="Remove from Favourites"
                            val favcolor=ContextCompat.getColor(applicationContext,R.color.remove_favourite)
                            favbut_desc.setBackgroundColor(favcolor)
                        }else{
                            favbut_desc.text="Add to Favourites"
                            val nofav=ContextCompat.getColor(applicationContext,R.color.favorite)
                            favbut_desc.setBackgroundColor(nofav)
                        }
                        favbut_desc.setOnClickListener {
                            if(!DBAsynctask(applicationContext,bookEntity,1).execute().get()){
                                val async=DBAsynctask(applicationContext,bookEntity,2).execute()
                                val result=async.get()
                                if (result){
                                    Toast.makeText(this, "Book added to favorites", Toast.LENGTH_SHORT).show()
                                    favbut_desc.text="Remove from Favourites"
                                    val favcolor=ContextCompat.getColor(applicationContext,R.color.remove_favourite)
                                    favbut_desc.setBackgroundColor(favcolor)
                                }else{
                                    Toast.makeText(this, "Some error occured!", Toast.LENGTH_SHORT).show()
                                }
                            }else{
                                val async=DBAsynctask(applicationContext,bookEntity,3).execute()
                                val result=async.get()
                                if (result){
                                    Toast.makeText(this, "Book removed from favourites", Toast.LENGTH_SHORT).show()
                                    favbut_desc.text="Add to Favourites"
                                    val nofav=ContextCompat.getColor(applicationContext,R.color.favorite)
                                    favbut_desc.setBackgroundColor(nofav)
                                }else{
                                    Toast.makeText(this, "Some error occured!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
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
    class DBAsynctask(val context: Context,val bookEntity: BookEntity,val Mode:Int):AsyncTask<Void,Void,Boolean>(){

        val get_database= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg params: Void?): Boolean {
            when(Mode){
            1->{
                val checkbook:BookEntity?=get_database.bookDao().getbookid(bookEntity.book_id.toString())
                get_database.close()
                return checkbook!=null
            }
            2->{
                get_database.bookDao().insertbook(bookEntity)
                get_database.close()
                return true
            }
            3->{
                get_database.bookDao().deletebook(bookEntity)
                get_database.close()
                return true
            }

            }
            return false
        }

    }
}