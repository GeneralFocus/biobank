package com.capriquota.bloodbank.model
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.util.*
import com.capriquota.bloodbank.R

import android.widget.LinearLayout

/* class CustomAdapter(context: Context,arrayListDetails:ArrayList<Model>) : BaseAdapter(){

   /* private val layoutInflater: LayoutInflater
    private val arrayListDetails:ArrayList<Model>

    init {
        this.layoutInflater = LayoutInflater.from(context)
        this.arrayListDetails=arrayListDetails
    }

    override fun getCount(): Int {
        return arrayListDetails.size
    }

    override fun getItem(position: Int): Any {
        return arrayListDetails.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        val view: View?
        val listRowHolder: ListRowHolder
        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.row_item, parent, false)
            listRowHolder = ListRowHolder(view)
            view.tag = listRowHolder
        } else {
            view = convertView
            listRowHolder = view.tag as ListRowHolder
        }

        listRowHolder.tvName.text = arrayListDetails.get(position).name
        listRowHolder.tvEmail.text = arrayListDetails.get(position).email
        listRowHolder.tvId.text = arrayListDetails.get(position).id
        return view
    }
}

private class ListRowHolder(row: View?) {
    public val tvName: TextView
    public val tvEmail: TextView
    public val tvId: TextView
    public val linearLayout: LinearLayout

    init {
        /* this.tvId = row?.findViewById<TextView>(R.id.tvId) as TextView
        this.tvName = row?.findViewById<TextView>(R.id.tvName) as TextView
        this.tvEmail = row?.findViewById<TextView>(R.id.tvEmail) as TextView
        this.linearLayout = row?.findViewById<LinearLayout>(R.id.linearLayout) as LinearLayout
    }
} */