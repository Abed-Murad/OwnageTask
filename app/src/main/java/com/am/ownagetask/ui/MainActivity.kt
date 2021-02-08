package com.am.ownagetask.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.ownagetask.ContactsService
import com.am.ownagetask.R
import com.am.ownagetask.room.ContactsDatabase
import com.am.ownagetask.updateRoomContacts
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService()

        if (checkPermission(this, READ_CONTACTS_PERMISSION)) {
            // you have permission go ahead
            contentResolver.updateRoomContacts(this@MainActivity)
        } else {
            // you do not have permission go request runtime permissions
            requestPermission(
                this@MainActivity,
                arrayOf(READ_CONTACTS_PERMISSION),
                REQUEST_RUNTIME_PERMISSION
            )
        }

        initRecyclerView()

    }

    private fun checkPermission(context: Context?, Permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(
        thisActivity: Activity?,
        Permission: Array<String>,
        code: Int
    ) {
        if (ContextCompat.checkSelfPermission(
                thisActivity!!,
                Permission[0]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Permission[0])) {
            } else {
                ActivityCompat.requestPermissions(thisActivity, Permission, code)
            }
        }
    }


    private fun initRecyclerView() {
        val list = ContactsDatabase.getInstance(this).contactsDao().getAll()
        contactsRecyclerView.adapter = ContactsAdapter(list.map { ContactItem(it.id, it.name, it.phoneNumber) })
        contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        contactsRecyclerView.setHasFixedSize(true)
        contactsRecyclerView.visibility = View.VISIBLE
        contactsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                contactsRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        progressBar.visibility = View.GONE
    }


    private fun startService() {
        val myServiceIntent = Intent(this, ContactsService::class.java)
        ContextCompat.startForegroundService(this, myServiceIntent)
    }

    companion object {
        const val READ_CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS
        const val REQUEST_RUNTIME_PERMISSION = 1001

    }
}