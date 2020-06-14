package com.jger.util

import androidx.lifecycle.MutableLiveData

class RequestCountUtil {

    companion object{
        var counter = 0
        var tooMany = MutableLiveData(false)
    }
}