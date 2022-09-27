package com.web.testtask.util

import com.web.testtask.AppDelegate.Companion.NEW_PREF_KEY
import com.web.testtask.AppDelegate.Companion.sharedPref
import com.web.testtask.data.model.DataModel

fun getSharedPref() =
    sharedPref?.getStringSet(NEW_PREF_KEY, null)?.toMutableSet()

fun putSharedPref(data: DataModel) {
    val setData = getSharedPref() ?: mutableSetOf()
    setData.add(data.id)

    sharedPref?.edit()?.putStringSet(NEW_PREF_KEY, setData)?.apply()
}