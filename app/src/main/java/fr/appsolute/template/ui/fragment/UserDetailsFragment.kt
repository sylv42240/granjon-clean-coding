package fr.appsolute.template.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import fr.appsolute.template.R
import fr.appsolute.template.data.extension.getToken
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_details.view.*


class UserDetailsFragment : Fragment() {

    private lateinit var userViewModel: UserViewModel
    private var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.run {
            userViewModel = ViewModelProvider(this, UserViewModel).get()
        } ?: throw IllegalStateException("Invalid Activity")
        userId =
            arguments?.getString(ARG_USER_ID_KEY) ?: throw IllegalStateException("No ID found")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCharacter(view)
    }

    private fun loadCharacter(view: View) {
        val token = getToken(requireContext())
        userViewModel.getUserById(userId, token) {
            (activity as? MainActivity)?.supportActionBar?.apply {
                this.title = it.login
                this.setDisplayHomeAsUpEnabled(true)
            }
            view.apply {
                this.user_details_name.text = it.login
                this.user_details_type.text = it.type
                Glide.with(this)
                    .load(it.avatar_url)
                    .into(this.user_details_image_view)
            }
        }
    }

    companion object {
        const val ARG_USER_ID_KEY = "arg_user_id_key"
    }

}