package com.am.ownagetask.ui

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.ownagetask.*
import com.am.ownagetask.background.ContactsService
import com.chibatching.kotpref.livedata.asLiveData
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mViewModel: MainActivityViewModel by viewModels { viewModelFactory }

    var mService: ContactsService? = null

    private val mServiceConnection = object : ServiceConnection {
        var isServiceConnected = false

        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d("ttt", "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder = iBinder as ContactsService.MyBinder
            mService = binder.service
            isServiceConnected = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d("ttt", "ServiceConnection: disconnected from service.")
            isServiceConnected = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        GlobalValues.asLiveData(GlobalValues::uiStatus)
            .observe(this, Observer<String> {
                when (it) {
                    CONST.LOADING -> {
                        contactsRecyclerView.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                        permissionDeniedLayout.visibility = View.GONE
                        if (mViewModel.getPermissionsStatus() == CONST.NO_PERMISSIONS_SETTINGS) {
                            contactsRecyclerView.visibility = View.GONE
                            progressBar.visibility = View.GONE
                            permissionDeniedLayout.visibility = View.VISIBLE
                        }
                    }
                    CONST.SUCCESS -> {
                        contactsRecyclerView.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        permissionDeniedLayout.visibility = View.GONE
                    }

                }
            })


        initRecyclerView()

        if (checkPermission(this, READ_CONTACTS_PERMISSION)) {
            mViewModel.updatePermissionsStatus(CONST.PERMISSIONS_GRANTED)
            populateRecyclerView()
            startService()
        } else {
            // you do not have permission go request runtime permissions
            requestPermission(
                this@MainActivity,
                arrayOf(READ_CONTACTS_PERMISSION),
                REQUEST_CODE
            )
        }

        initGrantPermissionBtnListener()


    }

    override fun onResume() {
        super.onResume()
        if (checkPermission(this, READ_CONTACTS_PERMISSION)) {
            mViewModel.fetchContactsFromContactsProvider()
            populateRecyclerView()
            if (!mServiceConnection.isServiceConnected) {
                startService()
            }
        }
    }

    private fun populateRecyclerView() {
        mViewModel.getContacts().observe(this, Observer { contact ->
            if (contact != null && contact.isNotEmpty()) {
                contactsRecyclerView.adapter =
                    ContactsAdapter(contact.map { ContactItem(it.id, it.name, it.phoneNumber) })
                mViewModel.updateUIStatus(CONST.SUCCESS)
            }
        })
    }

    private fun initGrantPermissionBtnListener() {
        grantPermissionButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)

        }
    }

    private fun initRecyclerView() {
        contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        contactsRecyclerView.setHasFixedSize(true)
        contactsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                contactsRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun startService() {
        val myServiceIntent = Intent(this, ContactsService::class.java)
        bindService(myServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onRequestPermissionsResult(
        permsRequestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (permsRequestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                    mViewModel.fetchContactsFromContactsProvider()
                    mViewModel.updatePermissionsStatus(CONST.PERMISSIONS_GRANTED)
                    startService()
                } else {
                    mViewModel.updateUIStatus(CONST.LOADING)
                    mViewModel.updatePermissionsStatus(CONST.NO_PERMISSIONS_SETTINGS)
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        unbindService(mServiceConnection)
    }

    companion object {
        const val READ_CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS
        const val REQUEST_CODE = 1001
    }

}



