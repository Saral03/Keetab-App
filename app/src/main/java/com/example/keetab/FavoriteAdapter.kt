package com.example.keetab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import database.BookEntity

class FavoriteAdapter(val context: Context,val booklist:List<BookEntity>):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(view:View):RecyclerView.ViewHolder(view){
        val img_cover:RelativeLayout=view.findViewById(R.id.img_cover)
        val img_fav:ImageView=view.findViewById(R.id.img_fav)
        val bookname_fav:TextView=view.findViewById(R.id.bookname_fav)
        val bookauthor_fav:TextView=view.findViewById(R.id.bookauthor_fav)
        val bookprice_fav:TextView=view.findViewById(R.id.bookprice_fav)
        val rating_fav:TextView=view.findViewById(R.id.rating_fav)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_favorite_single_row,parent,false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val book=booklist[position]
        holder.bookname_fav.text=book.bookName
        holder.bookauthor_fav.text=book.bookAuthor
        holder.bookprice_fav.text=book.bookPrice
        holder.rating_fav.text=book.bookRating
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.img_fav)

    }

    override fun getItemCount(): Int {
        return booklist.size
    }
}