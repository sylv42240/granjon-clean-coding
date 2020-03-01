package fr.appsolute.template.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fr.appsolute.template.R
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.adapter.UserAdapter
import fr.appsolute.template.ui.viewmodel.UserViewModel
import fr.appsolute.template.ui.widget.holder.OnCharacterClickListener
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment(), OnCharacterClickListener {

    private val userViewModel: UserViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.toolbar_title_user_list)
            this.setDisplayHomeAsUpEnabled(false)
        }
        userAdapter = UserAdapter(this)
        view.user_list_recycler_view.apply {
            adapter = userAdapter
        }
        userViewModel.userPagedList.observe(this) {
            userAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_view_menu, menu)
        val manager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search_item)
        val searchView = searchItem.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    userViewModel.getUserSearch(query).observe(this@UserListFragment) {
                        userAdapter.submitList(it)
                    }
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    // Implementation of OnCharacterClickListener
    override fun invoke(view: View, user: User) {
        findNavController().navigate(
            R.id.action_user_list_fragment_to_user_details_fragment,
            bundleOf(
                UserDetailsFragment.ARG_USER_ID_KEY to user.login
            )
        )
    }
}
