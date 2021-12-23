package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.exercise10.R

class adapter_data(val context: Context, val data: ArrayList<m_data>): BaseAdapter() {

    lateinit var tvnama: TextView
    lateinit var tvnim: TextView

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var converView = convertView
        converView = LayoutInflater.from(context).inflate(R.layout.list_data, parent, false)


        tvnama = converView.findViewById(R.id.tvnama)
        tvnim = converView.findViewById(R.id.tvnim)

        tvnama.text = data[position].nama
        tvnim.text = data[position].nim


        return converView

    }



}