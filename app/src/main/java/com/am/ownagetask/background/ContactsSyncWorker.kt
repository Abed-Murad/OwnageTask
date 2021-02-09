package com.am.ownagetask.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.am.ownagetask.repository.ContactsRepository
import javax.inject.Inject

class ContactsSyncWorker @Inject constructor(
    var contactsRepository: ContactsRepository,
    appContext: Context,
    workerParams: WorkerParameters
) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("www", "work started!")
        contactsRepository.fetchContactsFromContactsProvider()
        return Result.success()
    }
}