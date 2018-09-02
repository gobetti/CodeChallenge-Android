package me.gobetti.codechallenge

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import me.gobetti.codechallenge.modules.list.ListFragment
import android.app.SearchManager
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.MenuItem
import me.gobetti.codechallenge.model.Movie
import me.gobetti.codechallenge.modules.details.DetailsFragment
import me.gobetti.codechallenge.modules.details.OpenDetailsListener

class MainActivity : AppCompatActivity(), OpenDetailsListener {
    sealed class FragmentType {
        class Details(val movie: Movie): FragmentType()
        class List: FragmentType()

        val isRoot: Boolean
            get() = this is List
    }

    private val listFragment: ListFragment by lazy { ListFragment() }
    private var optionsMenu: Menu? = null
    private val currentFragmentType: FragmentType
        get() {
            val fragment = supportFragmentManager.findFragmentById(R.id.content_frame)
            return when (fragment) {
                is DetailsFragment -> FragmentType.Details(fragment.detailedMovie)
                is ListFragment -> FragmentType.List()
                else -> TODO("unreachable")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) return
        loadFragment(FragmentType.List())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        optionsMenu = menu
        menuInflater.inflate(R.menu.options_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchMenuItem = menu?.findItem(R.id.action_search)
        searchMenuItem?.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean = true

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                listFragment.onSearchEnded()
                return true
            }
        })

        val searchView = searchMenuItem?.actionView as SearchView?
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

    override fun onBackPressed() {
        super.onBackPressed()
        updateToolbar(currentFragmentType)
    }

    // OpenDetailsListener
    override fun onDetailsRequested(movie: Movie) {
        loadFragment(FragmentType.Details(movie))
    }

    private fun loadFragment(type: FragmentType) {
        updateToolbar(type)

        val fragment: Fragment = when (type) {
            is FragmentType.Details -> DetailsFragment.newInstance(type.movie)
            is FragmentType.List -> listFragment
        }

        val transaction = supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment, null)

        if (!type.isRoot) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    private fun updateToolbar(fragmentType: FragmentType) {
        val appName = resources.getString(R.string.app_name)
        val title = when(fragmentType) {
            is FragmentType.Details -> fragmentType.movie.title
            is FragmentType.List -> appName
        }

        supportActionBar?.title = title

        setSearchItemsVisible(fragmentType is FragmentType.List)
    }

    private fun setSearchItemsVisible(isVisible: Boolean) {
        listOf(R.id.action_search, R.id.action_clear_search_history).map {
            optionsMenu?.findItem(it)?.isVisible = isVisible
        }
    }
}
