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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.am.ownagetask.R
import com.am.ownagetask.background.ContactsService
import com.am.ownagetask.checkPermission
import com.am.ownagetask.databinding.ActivityMainBinding
import com.am.ownagetask.di.ViewModelFactory
import com.am.ownagetask.requestPermission
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mViewModel: MainActivityViewModel by viewModels { viewModelFactory }
    lateinit var mBinding: ActivityMainBinding
    var mService: ContactsService? = null
    var mIsBound: Boolean? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, iBinder: IBinder) {
            Log.d("ttt", "ServiceConnection: connected to service.")
            // We've bound to MyService, cast the IBinder and get MyBinder instance
            val binder = iBinder as ContactsService.MyBinder
            mService = binder.service
            mIsBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            Log.d("ttt", "ServiceConnection: disconnected from service.")
            mIsBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel.uiStatus.value = LOADING

        initUI()
        observeData()

    }

    override fun onResume() {
        super.onResume()
        mViewModel.uiStatus.value = LOADING
        if (checkPermission(this, READ_CONTACTS_PERMISSION)) {
            mViewModel.uiStatus.value = LOADING
            startService()
            mViewModel.fetchContactsFromContactsProvider()
        } else {
            // you do not have permission go request runtime permissions
            requestPermission(
                this@MainActivity,
                arrayOf(READ_CONTACTS_PERMISSION),
                REQUEST_RUNTIME_PERMISSION
            )
        }
    }


    private fun observeData() {
        mViewModel.getContacts().observe(this, Observer { contact ->
            mViewModel.uiStatus.value = SUCCESS
            if (contact != null && contact.isNotEmpty()) {
                contactsRecyclerView.adapter =
                    ContactsAdapter(contact.map { ContactItem(it.id, it.name, it.phoneNumber) })
            }
        })
        mViewModel.uiStatus.observe(this, Observer { status ->
            when (status) {
                SUCCESS -> {
                    mBinding.contactsRecyclerView.visibility = View.VISIBLE
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.permissionDeniedLayout.visibility = View.GONE
                }
                LOADING -> {
                    mBinding.contactsRecyclerView.visibility = View.GONE
                    mBinding.progressBar.visibility = View.VISIBLE
                    mBinding.permissionDeniedLayout.visibility = View.GONE
                }
                NO_PERMISSIONS -> {
                    mBinding.contactsRecyclerView.visibility = View.GONE
                    mBinding.progressBar.visibility = View.GONE
                    mBinding.permissionDeniedLayout.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun initUI() {
        mBinding.contactsRecyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.contactsRecyclerView.setHasFixedSize(true)
        mBinding.contactsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                contactsRecyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )
        mBinding.grantPermissionButton.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)

        }

    }

    override fun onRequestPermissionsResult(
        permsRequestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (permsRequestCode) {
            REQUEST_RUNTIME_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // you have permission go ahead
                    mViewModel.fetchContactsFromContactsProvider()
                    startService()
                } else {
                    mViewModel.uiStatus.value = NO_PERMISSIONS
                }
            }
        }
    }


    private fun startService() {
        val myServiceIntent = Intent(this, ContactsService::class.java)
        bindService(myServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }

    companion object {
        const val READ_CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS
        const val REQUEST_RUNTIME_PERMISSION = 1001
        const val LOADING = "loading"
        const val NO_PERMISSIONS = "no_permissions"
        const val SUCCESS = "sucess"
    }
}