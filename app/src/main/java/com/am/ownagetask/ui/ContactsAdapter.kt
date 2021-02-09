package com.am.ownagetask.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.am.ownagetask.R
import com.am.ownagetask.databinding.ItemContactBinding

class ContactsAdapter(var contactsList: List<ContactItem>) :
    RecyclerView.Adapter<ContactsAdapter.ContactHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding =
            DataBindingUtil.inflate<ItemContactBinding>(
                inflater,
                R.layout.item_contact,
                parent,
                false
            )
        return ContactHolder(binding)
    }

    override fun getItemCount() = contactsList.size


    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    inner class ContactHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contactItem: ContactItem) {
            binding.contact = contactItem
            binding.executePendingBindings()
        }

    }


}