package fr.appsolute.template.ui.viewmodel

import androidx.lifecycle.*
import fr.appsolute.template.data.extension.TokenRetriever
import fr.appsolute.template.data.model.User
import fr.appsolute.template.data.repository.UserRepository
import kotlinx.coroutines.launch

open class UserViewModel(
    private val repository: UserRepository,
    private val tokenRetriever: TokenRetriever
) : ViewModel() {

    private var _data = mutableListOf<Int>()
    private val accessToken = tokenRetriever.getToken()

    val data: List<Int>
        get() = _data

    
    val userPagedList = repository.getPaginatedList(viewModelScope, accessToken)

    fun getUserSearch(searchQuery: String)= repository.getSearchPaginatedList(viewModelScope, searchQuery, accessToken)


    fun getUserById(id: String, onSuccess: OnSuccess<User>) {
        viewModelScope.launch {
            repository.getCharacterDetails(id, accessToken)?.run(onSuccess)
        }
    }
}