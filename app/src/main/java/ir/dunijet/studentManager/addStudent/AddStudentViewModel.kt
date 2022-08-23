package ir.dunijet.studentManager.addStudent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Completable
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.local.student.Student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddStudentViewModel(private val mainRepository: MainRepository):ViewModel() {

    private val errorList=MutableLiveData<String>()
    private val resultList=MutableLiveData<String>()

    fun insertStudent(student: Student){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultInsert=mainRepository.insertStudent(student)
                resultList.postValue(resultInsert)
            }catch (ex:Exception){
                errorList.postValue(ex.message)
            }
        }

    }
    fun updateStudent(student: Student):Job{
        val completeOperation=viewModelScope.launch(Dispatchers.IO){
            try {
               val resultUpdate=mainRepository.updateStudent(student)
                resultList.postValue(resultUpdate)

            }catch (ex:Exception){
                errorList.postValue(ex.message)
            }
        }
        return completeOperation

    }

    fun getResultList():LiveData<String>{
        return resultList
    }
    fun getErrorList():LiveData<String>{
        return errorList
    }
}