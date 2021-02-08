package com.am.ownagetask.background

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.am.ownagetask.updateRoomContacts

class HourlyWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        Log.d("www", "work started!")
        applicationContext.contentResolver.updateRoomContacts(applicationContext)
        return Result.success()
    }
}