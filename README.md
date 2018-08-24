# Clock+ - An Android Jetpack / Kotlin Playground

With Google announcing [Android Jetpack](https://developer.android.com/jetpack/) and Google adopting Kotlin as a first party language, this project is an open source alarm clock app that embraces all of these concepts for others to contribute and learn!


## Architecture Components

For the longest time Android developers have always asked Google what type of architecture they should develop in and the community was never given a definitive answer, which spawned this page of different [Android architecture blueprints](https://github.com/googlesamples/android-architecture) on the companys Github. 

[Architecture components](https://developer.android.com/topic/libraries/architecture/) when assembled all together is now Google's *opinion* on how Android architecture should be assembled. The beautiful thing about each component is that each one handles a specific task very well, so the developer can pick and choose any to solve their problems.

### Lifecycles

One of the biggest pain points in developing Android has been having to deal with the `Activity` lifecycle and you would understand if you saw this [picture](https://developer.android.com/guide/components/activities/activity-lifecycle#alc) why that is. Then if you saw the `Fragment` activity lifecycle [picture](https://developer.android.com/guide/components/fragments#Creating), your head is probably starting to hurt. 

So the solution was to make [lifecycle aware components](https://developer.android.com/topic/libraries/architecture/lifecycle) that could help take this burden of having an extensive and defensive knowledge of dealing with the Android lifecycle. How this is done was through two components:

1. [`LifecycleOwner`](https://developer.android.com/topic/libraries/architecture/lifecycle#lco) - an interface implemented from `Activity` and `Fragment` that will return the [lifecyle](https://developer.android.com/topic/libraries/architecture/lifecycle#lc) event or state.
2. [`LifecycleObserver`](https://developer.android.com/reference/android/arch/lifecycle/LifecycleObserver) - marks a class as an observer of a lifecycle

With these two concepts, is what each tool preceding utilizes to make Android development even easier. 

### ViewModel

Screen rotation in Android has always been a tricky problem to handle, causing issues with losing the state of your app disappearing, causing memory leaks, or in extreme cases crashing your app. 

The [`ViewModel`](https://developer.android.com/topic/libraries/architecture/viewmodel) was the solution to this problem by decoupling data and background operations to a separate class which is *aware* of the activity lifecycle, and will not be destroyed unless the activity is.  


### LiveData

At its core, [`LiveData`](https://developer.android.com/topic/libraries/architecture/livedata) is just observable data holder class but on Android steroids because it is *aware* of its current host activtity lifecycle. 

With that in mind, there are plenty of [advantages of using LiveData](https://developer.android.com/topic/libraries/architecture/livedata#the_advantages_of_using_livedata), some of the more notable being: 

*  Ensure UI matches data state
*  No more crashes due to stopped activities
*  No memory leaks
*  No more manual lifecycle handling
*  Easy configuration changes

### Room

If you ever look up "SQLite Android," you will find in the first [link](https://developer.android.com/training/data-storage/sqlite) is Android documentation that cautions:

> Caution: Although these APIs are powerful, they are fairly low-level and require a great deal of time and effort to use. 

[Room](https://developer.android.com/topic/libraries/architecture/room) is the solution to make this exact task of caching data simple, and with Kotlin even simpler. When paired with `LiveData`, notifying UI is even easier since Room is able to return `LiveData` objects and emit changes to the underlying data to its observers if any changes are made to the database.

### Navigation

With Google having no strong opinion on navigation and the iterations `Fragment`'s have gone through
over the years. [The Navigation Architecture Component](https://developer.android.com/topic/libraries/architecture/navigation/) is a useful tool for easily implementing navigation in your app with a single `Activity` housing the app chrome (bottom navigation bar, app bar, components that are stationary) and each content screen being a `Fragment`. This component provides a nice GUI to link content screens to one another and allow you to navigate your app without ever needing to make a fragment transaction!

As of today, this component is still in alpha and has some limitations but is a great start to create navigation quickly for any app. 

## Firebase

Firebase is configured in the app currently with Firebase Auth and Firebase Cloud Messaging, with plans to add more tools from the platform. If you wish to contribute and gain access to the console, please contact the admin [andrew@plusmobileapps.com](mailto:andrew@plusmobileapps.com). 


## Dagger 2

To glue each of these components together with dependency injection, [Dagger 2](https://google.github.io/dagger/) was the tool of choice which really helped enabled writing cleaner code, specifically injecting [DAO](https://developer.android.com/training/data-storage/room/accessing-data)'s into the [repository](https://developer.android.com/jetpack/docs/guide#connecting_viewmodel_and_the_repository). 

### Coming Soon

* [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) - abstraction over every possible way to do background work on Android independent of platform with guaranteed execution

## Contribute

Currently this project is being developed in [Android Stuidio 3.2 RC1](https://developer.android.com/studio/preview/) as some of the experimental features are being used with plans to update to the stable channel upon release. 

If you wish to contribute, there is a [Trello board](https://trello.com/b/gpvd905I) which if you wish to have access to edit, please also contact the admin [andrew@plusmobileapps.com](mailto:andrew@plusmobileapps.com).

