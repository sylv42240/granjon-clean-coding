package fr.granjon.template.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import fr.granjon.template.BuildConfig
import fr.granjon.template.data.model.User
import fr.granjon.template.data.repository.UserRepository
import kotlinx.coroutines.launch


open class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private var _data = mutableListOf<Int>()
    private val accessToken = BuildConfig.API_TOKEN
    val itemCount = MutableLiveData<Int>()

    val data: List<Int>
        get() = _data


    val userPagedList = repository.getPaginatedList(viewModelScope, accessToken)

    fun getUserSearch(searchQuery: String): LiveData<PagedList<User>>{
        viewModelScope.launch {
            itemCount.postValue(repository.getItemCount(accessToken, searchQuery))
        }
        return repository.getSearchPaginatedList(viewModelScope, searchQuery, accessToken)
    }


    fun getUserById(id: String, onSuccess: OnSuccess<User?>) {
        viewModelScope.launch {
            repository.getCharacterDetails(id, accessToken).run(onSuccess)
        }
    }

    fun addUserToDatabase(id: String, onSuccess: OnSuccess<Boolean>) {
        viewModelScope.launch {
            repository.addUserToDatabase(id, accessToken).run(onSuccess)
        }
    }
}