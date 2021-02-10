# Ownage Android Task 

## Notes 

 *  100% `Kotlin` 
 * `Room` Used for Local storage. 
 * `Dagger2` Used for Dependency injection
 * `MVVM` Architecture + Architecture components(`LiveData, ViewModel,WorkManger...) 
 *  A mix of `Foreground Services' and 'PeriodicWorkRequests` are used to keep the data synced. 
    - if the app is open, a `Foreground Service` will be running and listing to `ContactsContract.Contacts.CONTENT_URI` for any changes in the contacts
    - if the app is closed a `PeriodicWorker` is scheduled to run every 15 min(can be changed) to update the app database, this will reduce battery usage compared to running a service all the time.  


## Project Files

- background
    - `ContactsObserver` Receives callbacks for changes to the device contacts content.
    - `ContactsService` Background service to host `ContactsObserver`.
    - `ContactsSyncWorker` Runs every 15 min, fetches Contacts from Contacts Provider, and updates Local `AppDatabase`.
- di 
    - modules
        - `ActivityBuilderModule` `@module` annotated that tells for `Dagger` Graph. 
        - `AppModule` `@module` annotated that tells for `Dagger` Graph. 
        - `DatabaseModule` `@module` annotated that tells for `Dagger` Graph. 
        - `ViewModelFactoryModule` `@module` annotated that tells for `Dagger` Graph. 
        - `ViewModelModule` `@module` annotated that tells for `Dagger` Graph. 
    - `AppComponent`  Dagger `@Component` that will generate code with all the dependencies required to satisfy the parameters of the methods it exposes.
    - `ViewModelKey` Used for `ViewModel` injection.
- repository
    - `ContactsRepository` The main App repository.  
- room
    - `AppDatabase` Local Storage to save the contacts locally. 
    - `ContactEntity` A simple model. 
    - `ContactsDao` Data Acess Object for `ContactEntity`.
- ui
    - `ContactItem` A simple model.
    - `ContactsAdapter` Adapter for `contactsRecyclerView`. 
    - `MainActivity`
    - `MainActivityViewModel`
    - `ViewModelFactory` needed to be able to add `ContactsRepository` as a parapeter for `MainActivityViewModel`.
- `MyApplication` A sub class of `DaggerApplication`.
- `PermissionHelper` Inculdes some helper functions for permissions  `checkPermission(parms)` & `requestPermission(parms).


## Apk

[Download APK](/extra/app-debug.apk)

## ScreenShots

### Light Mode
![screener1](extra/light_main.jpg?raw=true?raw=true)
![screener1](extra/light_empty.jpg?raw=true?raw=true)

### Dark Mode
![screener1](extra/dark_main.jpg?raw=true?raw=true)
![screener1](extra/dark_empty.jpg?raw=true?raw=true)

