package com.jger.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.jger.R
import com.jger.transferClass.EventTransfer
import com.jger.ui.adapter.ListEventAdapter
import kotlinx.android.synthetic.main.activity_lis_event.*

class ListEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lis_event)
        supportActionBar!!.title = intent.getStringExtra("tournamentName");
        val adapter = ListEventAdapter(EventTransfer.listEvents)
        EventRecyclerView.adapter=adapter
        EventRecyclerView.layoutManager=LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search,menu)
        menu!!.findItem(R.id.search).isVisible = false
        menu!!.findItem(R.id.search).isEnabled=false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeButton -> {
                var homeIntent = Intent(this, MainActivity::class.java)
                homeIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(homeIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
