package me.gobetti.codechallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import me.gobetti.codechallenge.modules.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment()
    }

    private fun loadFragment() {
        val fragment = ListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, null)
                .commit()
    }
}
