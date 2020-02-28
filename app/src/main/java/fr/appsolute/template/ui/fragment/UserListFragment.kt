package fr.appsolute.template.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.observe
import fr.appsolute.template.R
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.adapter.UserAdapter
import fr.appsolute.template.ui.viewmodel.UserViewModel
import fr.appsolute.template.ui.widget.holder.OnCharacterClickListener
import kotlinx.android.synthetic.main.fragment_user_list.view.*

class UserListFragment : Fragment(), OnCharacterClickListener {

    private lateinit var characterViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            characterViewModel = ViewModelProvider(this, UserViewModel).get()
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
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.setTitle(R.string.app_name)
            this.setDisplayHomeAsUpEnabled(false)
        }
        // We need to inject the OnCharacterClickListener in the constructor of the adapter
        userAdapter = UserAdapter(this)
        view.character_list_recycler_view.apply {
            adapter = userAdapter
        }
        characterViewModel.charactersPagedList.observe(this) {
            userAdapter.submitList(it)
        }
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
