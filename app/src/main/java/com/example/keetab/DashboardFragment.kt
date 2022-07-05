package com.example.keetab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import module.book

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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        recycle= view.findViewById(R.id.recycle)
        layoutManger=LinearLayoutManager(activity)
        val books= arrayListOf("P.S. I love You","The great Gatesby","Anna Karenina","Madame Bovary","War and Peace","Lolita","Middlemarch","The adventures of Hucckleberry Finn","Mobby-Dick","The lord of rings")
        val bookInfoList = arrayListOf<book>(
            book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
            book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
            book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
            book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
            book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
            book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
            book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
            book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
            book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
            book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
        )
        recyclerAdapter= DashoboardAdapter(activity as Context,bookInfoList)
        recycle.adapter=recyclerAdapter
        recycle.layoutManager=layoutManger
        recycle.addItemDecoration(DividerItemDecoration(recycle.context,(layoutManger as LinearLayoutManager).orientation))
        return view
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