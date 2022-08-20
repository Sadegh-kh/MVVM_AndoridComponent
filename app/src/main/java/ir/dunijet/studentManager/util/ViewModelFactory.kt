package ir.dunijet.studentManager.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.dunijet.studentManager.addStudent.AddStudentViewModel
import ir.dunijet.studentManager.mainScreen.MainViewModel
import ir.dunijet.studentManager.model.MainRepository

class MainViewModelFactory(private val mainRepository: MainRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(mainRepository) as T
    }
}

class AddStudentViewModelFactory(private val mainRepository: MainRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddStudentViewModel(mainRepository) as T
    }
}