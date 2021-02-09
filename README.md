# Ownage Android Task 

## Notes 

 *  100% `Kotlin` 
 * `Room` Used for Local storage. 
 * `Dagger2` Used for Dependency injection
 * `MVVM` Architecture + Architecture components(`LiveData, ViewModel,WorkManger...) 
 *  A mix of `Foreground Services' and 'PeriodicWorkRequests` are used to keep the data synced. 
    - if the app is open, a `Foreground Service` will be running and listing to `ContactsContract.Contacts.CONTENT_URI` for any changes in the contacts
    - if the app is closed a `PeriodicWorker` is scheduled to run every 15 min(can be changed) to update the app database, this will reduce battery usage compared to running a service all the time.  
