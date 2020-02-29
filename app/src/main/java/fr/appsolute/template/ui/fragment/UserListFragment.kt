package fr.appsolute.template.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Base64
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import fr.appsolute.template.R
import fr.appsolute.template.data.extension.getToken
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.adapter.UserAdapter
import fr.appsolute.template.ui.viewmodel.UserViewModel
import fr.appsolute.template.ui.widget.holder.OnCharacterClickListener
import kotlinx.android.synthetic.main.fragment_user_list.view.*

class UserListFragment : Fragment(), OnCharacterClickListener {

    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var accessToken: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            userViewModel = ViewModelProvider(this, UserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
    }

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
        accessToken = getToken(requireContext())
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.app_name)
            this.setDisplayHomeAsUpEnabled(false)
        }
        // We need to inject the OnCharacterClickListener in the constructor of the adapter
        userAdapter = UserAdapter(this)
        view.character_list_recycler_view.apply {
            adapter = userAdapter
        }
        userViewModel.getAllUsers(accessToken).observe(this) {
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
                    userViewModel.getUserSearch(query, accessToken).observe(this@UserListFragment){
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
//        findNavController().navigate(
//            R.id.action_character_list_fragment_to_character_details_fragment,
//            bundleOf(
//                CharacterDetailsFragment.ARG_CHARACTER_ID_KEY to character.id
//            )
//        )
    }
}
