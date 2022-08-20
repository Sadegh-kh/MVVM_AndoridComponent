package ir.dunijet.studentManager.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.viewModelFactory
import ir.dunijet.studentManager.addStudent.AddStudentViewModel
import ir.dunijet.studentManager.mainScreen.MainViewModel
import ir.dunijet.studentManager.model.MainRepository


class MainViewModelFactory(private val mainRepository: MainRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return MainViewModel(mainRepository) as T
    }
}

class AddStudentViewModelFactory(private val mainRepository: MainRepository) :
    ViewModelProvider.AndroidViewModelFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddStudentViewModel(mainRepository)as T
    }
}

