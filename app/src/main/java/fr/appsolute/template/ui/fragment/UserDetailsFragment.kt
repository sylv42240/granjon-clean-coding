package fr.appsolute.template.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import fr.appsolute.template.R
import fr.appsolute.template.ui.activity.MainActivity
import fr.appsolute.template.ui.utils.DateConverter
import fr.appsolute.template.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_details.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class UserDetailsFragment : Fragment() {

    private val userViewModel: UserViewModel by viewModel()
    private var userId = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        (activity as? MainActivity)?.supportActionBar?.apply {
            this.title = getString(R.string.fragment_user_details_title)
            this.setDisplayHomeAsUpEnabled(true)
        }
        loadCharacter(view)
    }

    private fun loadCharacter(view: View) {
        userViewModel.getUserById(userId) {
            view.apply {
                this.user_details_name.text = it.login
                this.user_details_type.text = it.type
                this.user_details_created_date_content.text = it.created_at?.let { date -> DateConverter.convertDateToString(date) }
                this.user_details_public_repos_content.text = getString(R.string.public_repos_number, it.public_repos)
                Glide.with(this)
                    .load(it.avatar_url)
                    .circleCrop()
                    .into(this.user_details_image_view)
            }
        }
    }

    companion object {
        const val ARG_USER_ID_KEY = "arg_user_id_key"
    }

}