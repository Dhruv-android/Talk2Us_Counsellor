package com.talk2us_Counsellor.ui.clientList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.talk2us_Counsellor.R

class ClientAdapter(val context: Context) : RecyclerView.Adapter<ClientAdapter.ClientHolder>() {
    private val list = ArrayList<String>()
    private lateinit var onClick: onClick
    fun updateList(client: String) {
        list.add(client)
        notifyDataSetChanged()
    }

    fun setOnClick(onClick: onClick){
        this.onClick=onClick
    }

    inner class ClientHolder(itemView: View,onClick: onClick) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.tv_id)
        init {
            textView.setOnClickListener {
                onClick.onClick(textView.text.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_chat_list, parent, false)
        return ClientHolder(v,onClick)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ClientHolder, position: Int) {
        holder.textView.text = list[position]
    }

}
interface onClick{
    fun onClick(str:String)
}