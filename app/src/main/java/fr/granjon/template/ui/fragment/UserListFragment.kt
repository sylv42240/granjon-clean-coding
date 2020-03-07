package fr.granjon.template.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import fr.granjon.template.R
import fr.granjon.template.data.model.User
import fr.granjon.template.ui.activity.MainActivity
import fr.granjon.template.ui.adapter.UserAdapter
import fr.granjon.template.ui.utils.dialog.DialogComponent
import fr.granjon.template.ui.utils.hide
import fr.granjon.template.ui.utils.isOnline
import fr.granjon.template.ui.utils.show
import fr.granjon.template.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_list.*
import kotlinx.android.synthetic.main.fragment_user_list.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

    private val dialogComponent: DialogComponent by inject()
    private val userViewModel: UserViewModel by viewModel()
    private lateinit var userAdapter: UserAdapter
    private var errorDetected = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        dialogComponent.dismissDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        showProgress()
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.toolbar_title_user_list)
            this.setDisplayHomeAsUpEnabled(false)
        }
        userAdapter = UserAdapter(this::goToUserDetailFragment, this::addToFavorite)
        view.user_list_recycler_view.apply {
            adapter = userAdapter
        }
        initLiveData()
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
                    showProgress()
                    userViewModel.getUserSearch(query).observe(this@UserListFragment) {
                        errorDetected = if (isOnline(requireContext())) {
                            userAdapter.submitList(it)
                            false
                        } else {
                            showError()
                            true
                        }
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

    private fun initLiveData(){
        userViewModel.userPagedList.observe(this) {
            errorDetected = if (isOnline(requireContext())) {
                userAdapter.submitList(it)
                false
            } else {
                showError()
                true
            }
        }
        userViewModel.itemCount.observe(this) {
            errorDetected = if (it == 0) {
                showError()
                true
            } else {
                false
            }
        }
        requireView().viewTreeObserver.addOnGlobalLayoutListener {
            if (user_list_recycler_view?.adapter?.itemCount ?: 0 > 0) {
                hideProgress()
            } else {
                if (errorDetected) {
                    showError()
                } else {
                    showProgress()
                }
            }
        }
    }

    private fun showProgress() {
        user_list_progress_bar?.show()
        user_list_recycler_view?.hide()
        user_list_empty_list_image?.hide()
        user_list_empty_list_text?.hide()
    }

    private fun showError() {
        user_list_progress_bar?.hide()
        user_list_empty_list_image?.show()
        user_list_empty_list_text?.show()
    }

    private fun hideProgress() {
        user_list_progress_bar?.hide()
        user_list_recycler_view?.show()
        user_list_empty_list_image?.hide()
        user_list_empty_list_text?.hide()
    }

    // Simple click implementation
    private fun goToUserDetailFragment(user: User) {
        findNavController().navigate(
            R.id.action_user_list_fragment_to_user_details_fragment,
            bundleOf(
                UserDetailsFragment.ARG_USER_ID_KEY to user.login
            )
        )
    }
    // Long click implementation
    private fun addToFavorite(view: View, userId: String) {
        dialogComponent.displayYesNoDialog(
            context = view.context,
            content = R.string.add_to_db_dialog_content,
            title = R.string.add_to_db_dialog_title,
            onPositiveClicked = {
                userViewModel.addUserToDatabase(userId) {
                    val response = if (it) {
                        getString(R.string.user_added_success)
                    } else {
                        getString(R.string.user_added_fail)
                    }
                    Toast.makeText(view.context, response, Toast.LENGTH_SHORT).show()
                }
            }
        )

    }

}
