package me.gobetti.codechallenge

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import me.gobetti.codechallenge.modules.list.ListFragment
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    private fun loadFragment() {
        val fragment = ListFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, null)
                .commit()
    }
}
