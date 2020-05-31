package com.jger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jger.R
import com.jger.transferClass.EventTransfer
import com.jger.ui.adapter.ListEventAdapter

class ListEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lis_event)
        val adapter = ListEventAdapter(EventTransfer.listEvents)
    }
}
