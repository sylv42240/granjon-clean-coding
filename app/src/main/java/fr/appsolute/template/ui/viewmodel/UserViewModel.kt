package fr.appsolute.template.ui.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import fr.appsolute.template.data.model.PaginatedResult
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _data = mutableListOf<Int>()


    val data: List<Int>
        get() = _data

    
    fun getAllUsers(accessToken: String) = repository.getPaginatedList(viewModelScope, accessToken)

    fun getUserSearch(searchQuery: String, accessToken: String)= repository.getSearchPaginatedList(viewModelScope, searchQuery, accessToken)


    fun getUserById(id: String, accessToken: String, onSuccess: OnSuccess<User>) {
        viewModelScope.launch {
            repository.getCharacterDetails(id, accessToken)?.run(onSuccess)
        }
    }
}