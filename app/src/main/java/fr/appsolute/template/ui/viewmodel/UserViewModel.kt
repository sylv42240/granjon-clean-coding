package fr.appsolute.template.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.repository.UserRepository
import kotlinx.coroutines.launch

open class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _data = mutableListOf<Int>()

    val data: List<Int>
        get() = _data


    val charactersPagedList = repository.getPaginatedList(viewModelScope)


    fun getCharacterById(id: Int, onSuccess: OnSuccess<User>) {
        viewModelScope.launch {
            repository.getCharacterDetails(id)?.run(onSuccess)
        }
    }

    companion object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return UserViewModel(UserRepository.instance) as T
        }
    }
}