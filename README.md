# Ownage Android Task 

## Notes 

 *  100% `Kotlin` 
 * `Room` Used for Local storage. 
 * `Dagger2` Used for Dependency injection
 * `MVVM` Architecture + Architecture components(`LiveData, ViewModel,WorkManger...) 
 *  A mix of `Foreground Services' and 'PeriodicWorkRequests` are used to keep the data synced. 
    - if the app is open, a `Foreground Service` will be running and listing to `ContactsContract.Contacts.CONTENT_URI` for any changes in the contacts
    - if the app is closed a `PeriodicWorker` is scheduled to run every 15 min(can be changed) to update the app database, this will reduce battery usage compared to running a service all the time.  


## Apk

[Download APK](/Extra/app-debug.apk)

## ScreenShots

### Light Mode
![screener1](extra/light_main.jpg?raw=true?raw=true)
![screener1](extra/light_empty.jpg?raw=true?raw=true)

### Dark Mode
![screener1](extra/dark_main.jpg?raw=true?raw=true)
![screener1](extra/dark_empty.jpg?raw=true?raw=true)

