package com.catcompanion.app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BreedViewModel : ViewModel() {
    private val userRepository = BreedRepository()

    private val _catBreedsList = MutableLiveData<List<Breed>>()
    val catBreeds: LiveData<List<Breed>> get() = _catBreedsList

    @OptIn(DelicateCoroutinesApi::class)
    fun fetchUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            val catBreeds = userRepository.getBreeds() // Fetch breeds from the repository
            _catBreedsList.value = catBreeds
        }
    }
}