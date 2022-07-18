package com.example.keetab

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import database.BookDatabase
import database.BookEntity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var fav_recycler:RecyclerView
lateinit var progress_layout:RelativeLayout
lateinit var progressbar_fav:ProgressBar
lateinit var layputManager:RecyclerView.LayoutManager
lateinit var recyclerAdapter:FavoriteAdapter
var dbBooklist= listOf<BookEntity>()
class FavFragment : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fav, container, false)
        fav_recycler=view.findViewById(R.id.fav_recycler)
        progress_layout=view.findViewById(R.id.progress_layout)
        progressbar_fav=view.findViewById(R.id.progressbar_fav)
        layputManager=GridLayoutManager(activity as Context,2)
        dbBooklist=RetreiveFav(activity as Context).execute().get()
        if (activity!=null){
            progress_layout.visibility=View.GONE
            recyclerAdapter= FavoriteAdapter(activity as Context, dbBooklist)
            fav_recycler.adapter= recyclerAdapter
            fav_recycler.layoutManager= layputManager
        }
        return view
    }
    class RetreiveFav(val context: Context):AsyncTask<Void,Void,List<BookEntity>>(){
        override fun doInBackground(vararg params: Void?): List<BookEntity> {
            val db= Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
            return db.bookDao().getallbooks()
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}