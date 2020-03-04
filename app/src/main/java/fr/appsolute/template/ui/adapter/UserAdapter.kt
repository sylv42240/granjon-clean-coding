package fr.appsolute.template.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import fr.appsolute.template.data.model.User
import fr.appsolute.template.ui.widget.holder.OnUserClickListener
import fr.appsolute.template.ui.widget.holder.OnUserLongClickListener
import fr.appsolute.template.ui.widget.holder.UserViewHolder

class UserAdapter(
    private val onUserClickListener: OnUserClickListener,
    private val onUserLongClickListener: OnUserLongClickListener
) : PagedListAdapter<User, UserViewHolder>(Companion) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.run { holder.bind(this, onUserClickListener, onUserLongClickListener) }
    }

    companion object : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }
}