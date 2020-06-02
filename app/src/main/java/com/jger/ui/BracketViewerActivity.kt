package com.jger.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.jger.BracketVisualizer.Fragment.BracketsFragment
import com.jger.R
import kotlinx.android.synthetic.main.activity_bracket_viewer.*

class BracketViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bracket_viewer)
        initialiseBracketsFragment()
    }

    private var bracketFragment: BracketsFragment? = null


    private fun initialiseBracketsFragment() {
        bracketFragment = BracketsFragment()
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.containeur, bracketFragment!!, "brackets_home_fragment")
        transaction.commit()
        manager.executePendingTransactions()
    }
}