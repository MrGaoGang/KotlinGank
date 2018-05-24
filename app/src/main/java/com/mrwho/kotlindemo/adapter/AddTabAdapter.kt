package com.mrwho.kotlindemo.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.chen.kotlintext.callback.OnSwithChangeListener
import com.mrwho.kotlindemo.R
import com.mrwho.kotlindemo.utils.MainItem
import kotlinx.android.synthetic.main.add_item.view.*

/**
 * Created by mr.gao on 2018/5/24.
 * Package:    com.mrwho.kotlindemo.adapter
 * Create Date:2018/5/24
 * Project Name:KotlinDemo
 * Description:
 */

class AddTabAdapter(val items: ArrayList<MainItem>, val selectItems: ArrayList<Boolean>,val listener: OnSwithChangeListener) : RecyclerView.Adapter<AddTabAdapter.ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(items[position], selectItems[position])
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.add_item, parent, false)
        return ItemViewHolder(view)
    }




    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindData(data: MainItem, select: Boolean) {

            with(data) {
                itemView.itemTv.text = data.name
                itemView.switchClass.isChecked = select
                itemView.switchClass.setOnCheckedChangeListener {
                    compoundButton: CompoundButton?, isChecked: Boolean ->
                    if (isChecked) {
                        listener.onSwitchChecked(data.id)
                    } else {
                        listener.onSwitchDischecked(data.id)
                    }
                }
            }

        }

    }

}

