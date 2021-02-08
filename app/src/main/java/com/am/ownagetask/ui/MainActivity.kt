package com.am.ownagetask.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.ownagetask.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val list = listOf(
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "nbed Murad", "1111"),
            ContactItem("1", "abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "1111"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "sbed Murad", "0598403027"),
            ContactItem("1", "kbed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "12312"),
            ContactItem("1", "wbed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "12312"),
            ContactItem("1", "xbed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "34534"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "2353"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "0598403027"),
            ContactItem("1", "Abed Murad", "0598403027"),
        )
        contactsRecyclerView.adapter = ContactsAdapter(list)
        contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        contactsRecyclerView.setHasFixedSize(true)
        contactsRecyclerView.visibility = View.VISIBLE
        contactsRecyclerView.addItemDecoration(DividerItemDecoration(contactsRecyclerView.context, DividerItemDecoration.VERTICAL))

        progressBar.visibility = View.GONE

    }
}