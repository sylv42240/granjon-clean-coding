package fr.granjon.template.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import fr.granjon.template.R
import fr.granjon.template.ui.activity.MainActivity
import fr.granjon.template.ui.utils.DateConverter
import fr.granjon.template.ui.utils.hide
import fr.granjon.template.ui.utils.isOnline
import fr.granjon.template.ui.utils.show
import fr.granjon.template.ui.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.fragment_user_details.view.*
import kotlinx.android.synthetic.main.fragment_user_details.view.user_details_information_layout
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
            user_details_progress_bar.hide()
            if (it != null) {
                user_details_information_layout.show()
                val publicRepos = it.public_repos ?: 0
                val followers = it.followers ?: 0
                val email = it.email ?: getString(R.string.not_defined)
                val location = it.location ?: getString(R.string.not_defined)
                val name = it.name ?: getString(R.string.not_defined)
                val blog = if (it.blog.isNullOrBlank()) {
                    getString(R.string.not_defined)
                } else {
                    it.blog
                }
                val userState = if (it.savedInDatabase) {
                    getString(R.string.save_in_db)
                } else {
                    getString(R.string.not_save_in_db)
                }
                view.apply {
                    this.user_details_login.text = it.login
                    this.user_details_type.text = it.type
                    this.user_details_created_date_content.text =
                        it.created_at?.let { date -> DateConverter.convertDateToString(date) }
                    this.user_details_public_repos_content.text =
                        this.context.resources.getQuantityString(
                            R.plurals.public_repos_number,
                            publicRepos,
                            publicRepos
                        )
                    this.user_details_followers_content.text =
                        this.context.resources.getQuantityString(
                            R.plurals.followers_number,
                            followers,
                            followers
                        )
                    this.user_details_email_content.text = email
                    this.user_details_location_content.text = location
                    this.user_details_name_content.text = name
                    this.user_details_blog_content.text = blog
                    this.user_details_database_content.text = userState
                    Glide.with(this)
                        .load(it.avatar_url)
                        .circleCrop()
                        .into(this.user_details_image_view)
                }
            } else {
                showError()
            }

        }
    }

    private fun showError() {
        user_details_information_layout.hide()
        user_details_empty_user_image.show()
        user_details_empty_user_text.show()
    }

    companion object {
        const val ARG_USER_ID_KEY = "arg_user_id_key"
    }

}