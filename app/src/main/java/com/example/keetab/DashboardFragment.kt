package com.example.keetab

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import module.book
import org.json.JSONException
import util.Internet
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
lateinit var recycle:RecyclerView
lateinit var layoutManger:RecyclerView.LayoutManager
lateinit var recyclerAdapter: DashoboardAdapter
lateinit var progresslayout:RelativeLayout
    val bookInfoList = arrayListOf<book>()
lateinit var bar_pro:ProgressBar
var ratingcomparator=Comparator<book>{Book1,Book2 ->
    Book1.bookrating.compareTo(Book2.bookrating,true)
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        setHasOptionsMenu(true)
        recycle= view.findViewById(R.id.recycle)
        progresslayout=view.findViewById(R.id.progresslayout)
        bar_pro=view.findViewById(R.id.bar_pro)
        progresslayout.visibility=View.VISIBLE
        layoutManger=LinearLayoutManager(activity)
//        val bookInfoList = arrayListOf<book>(
//            book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
//            book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
//            book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
//            book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
//            book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
//            book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
//            book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
//            book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
//            book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
//            book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
//        )

        val queue=Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"
        if (Internet().checkConnectivity(activity as Context)){
            val jsonObjectRequest=object :JsonObjectRequest(Request.Method.GET,url,null,
                Response.Listener {
                    //here we will handle response
                    try {
                        progresslayout.visibility=View.GONE
                        val success=it.getBoolean("success")
                       // val bookInfoList = arrayListOf<book>()
                        if (success){
                            val data=it.getJSONArray("data")
                            for (i in 0 until data.length()){
                                val bookJsonObject=data.getJSONObject(i)
                                val bookObj=book(bookJsonObject.getString("book_id"),bookJsonObject.getString("name"),bookJsonObject.getString("author"),
                                    bookJsonObject.getString("rating"),bookJsonObject.getString("price"),bookJsonObject.getString("image"))
                                bookInfoList.add(bookObj)
                                recyclerAdapter= DashoboardAdapter(activity as Context,bookInfoList)
                                recycle.adapter=recyclerAdapter
                                recycle.layoutManager=layoutManger
                            }
                        }
                        else{
                            Toast.makeText(activity as Context, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                    catch (e:JSONException){
                        Toast.makeText(activity as Context, "Json error occured", Toast.LENGTH_SHORT).show()
                    }
                    
                },Response.ErrorListener {
                    /*here we will handle the error*/
                    Toast.makeText(activity as Context, "Volley error occured", Toast.LENGTH_SHORT).show()
                })
            {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers=HashMap<String,String>()
                    headers["content-type"]="application/json"
                    headers["token"]="9c7c036c352378"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)
        }
        else{
            val dialog=AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet not Found")
            dialog.setPositiveButton("Open Settings"){text,listner->
                val settings=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settings)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listner->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()

        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_dashboard,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item?.itemId
        if (id==R.id.sort){
            Collections.sort(bookInfoList,ratingcomparator)
            bookInfoList.reverse()
        }
        recyclerAdapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}