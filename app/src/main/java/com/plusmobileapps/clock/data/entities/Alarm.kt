package com.plusmobileapps.clock.data.entities

import java.time.DayOfWeek

data class Alarm(
        val id: String,
        val min: Int,
        val hour: Int,
        val enabled: Boolean = true,  //true because if you are instantiating this the intent is to be enabled?
        val days: List<DayOfWeek>,  //should this be nullable?
        val isRepeating: Boolean = false    //shouldn't be enabled by default
        //val tone: Whatever a music file is stored as?
        //val label: String     //let the user name the alarm
        //val snoozeDuration: Int
        //val silenceAfter: Int
        //val alarmVolume: Int
        //val graduallyIncreaseVolume: Int
        //val buttonsControlVolume: Boolean    //either disable alarm or control volume functionality
        //val startWeekOnSunday: Boolean       //I have gotten to used to my week starting on a monday because of starbucks
        //val vibrate: Boolean
        /**
         * Stretch goals
         *
         * NFC disabling alarm - have one to allow your alarm to stop playing sound, the other to dismiss alarm
         *
         * Calendar sync - have alarms set off specific calendars I have set in google calendar or outlook
         *
         * QR Code disabling alarm - for those that don't have nfc tags, cheap alternative is to print out
         *                           a QR code that when scanned can disable the alarm
         *
         * What if the user cannot scan or tap the disabler? How do we handle this?
         * Really complicated math problem the user must solve?
         *
         *
         * Business Model: Action Launcher - Free to download - 7 day trial with in app purchase
         *                                   for full access to all features
         *                                   or just free?
         */
) {
}