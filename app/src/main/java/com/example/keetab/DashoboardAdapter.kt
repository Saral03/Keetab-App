package com.example.keetab

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
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
       // holder.img.setImageResource(book.bookimg)
        Picasso.get().load(book.bookimg).error(R.drawable.default_book_cover).into(holder.img)
        holder.author.text=book.bookauthor
        holder.price.text=book.bookprice
        holder.rating_val.text=book.bookrating
        holder.mylayout.setOnClickListener {
            val intent=Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookid)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return item.size
    }
}