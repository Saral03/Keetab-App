package com.example.keetab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import module.book

class DashoboardAdapter(val context: Context, val item:ArrayList<book>):RecyclerView.Adapter<DashoboardAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val showbooks:TextView=view.findViewById(R.id.listitem)
        val img:ImageView=view.findViewById(R.id.img)
        val author:TextView=view.findViewById(R.id.author)
        val price:TextView=view.findViewById(R.id.price)
        val rating_val:TextView=view.findViewById(R.id.rating_val)
        val mylayout:RelativeLayout=view.findViewById(R.id.mylayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_item_recycle,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book=item[position]
        holder.showbooks.text=book.bookname
        holder.img.setImageResource(book.bookimg)
        holder.author.text=book.bookauthor
        holder.price.text=book.bookcost
        holder.rating_val.text=book.bookrating
        holder.mylayout.setOnClickListener {
            Toast.makeText(context, "Clicked on ${holder.showbooks.text}", Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
       return item.size
    }
}