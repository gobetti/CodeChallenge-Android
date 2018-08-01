package me.gobetti.codechallenge

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import me.gobetti.codechallenge.modules.list.ListFragment
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    private val listFragment: ListFragment by lazy { ListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView?
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        return true
    }

    override fun onNewIntent(intent: Intent?) {
        if (intent != null && intent.action == Intent.ACTION_SEARCH) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            listFragment.onSearchAction(query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_clear_search_history) {
            listFragment.onSearchHistoryClearAction()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, listFragment, null)
                .commit()
    }
}
