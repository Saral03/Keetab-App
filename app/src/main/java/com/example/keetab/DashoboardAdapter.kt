package com.example.keetab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DashoboardAdapter(val context: Context, val item:ArrayList<String>):RecyclerView.Adapter<DashoboardAdapter.DashboardViewHolder>() {
    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textView:TextView=view.findViewById(R.id.listitem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.view_item_recycle,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val text=item[position]
        holder.textView.text=text
    }

    override fun getItemCount(): Int {
       return item.size
    }
}