package fr.appsolute.template.ui.widget.holder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.appsolute.template.R
import fr.appsolute.template.data.model.User
import kotlinx.android.synthetic.main.holder_user.view.*


/**
 * SAM (Single Abstract Method) to listen a click.
 *
 * This callback contains the view clicked, and the character attached to the view
 */
typealias OnCharacterClickListener = (view: View, character: User) -> Unit

class UserViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: User, onClick: OnCharacterClickListener) {
        itemView.apply {
            this.setOnClickListener { onClick(it, model) }
            this.holder_user_name.text = model.login
            this.holder_user_info.text = model.type
            Glide.with(this)
                .load(model.avatar_url)
                .into(this.holder_user_avatar)
        }
    }

    companion object {
        /**
         * Create a new Instance of [UserViewHolder]
         */
        fun create(parent: ViewGroup): UserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.holder_user,
                parent,
                false
            )
            return UserViewHolder(view)
        }
    }

}