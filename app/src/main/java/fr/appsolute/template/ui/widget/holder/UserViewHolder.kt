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
typealias OnUserClickListener = (view: View, user: User) -> Unit

typealias OnUserLongClickListener = (view: View, userId: String) -> Unit

class UserViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(model: User, onClick: OnUserClickListener, onLongClick: OnUserLongClickListener) {
        itemView.apply {
            this.setOnClickListener { onClick(it, model) }
            this.setOnLongClickListener {
                onLongClick(it, model.login)
                return@setOnLongClickListener true
            }
            this.holder_user_name.text = model.login
            this.holder_user_type.text = model.type
            this.holder_user_id.text = context.getString(R.string.user_id_holder_text, model.id)
            Glide.with(this)
                .load(model.avatar_url)
                .circleCrop()
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