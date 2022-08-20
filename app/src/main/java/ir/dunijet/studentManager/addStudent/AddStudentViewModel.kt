package ir.dunijet.studentManager.addStudent

import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.local.student.Student

class AddStudentViewModel(private val mainRepository: MainRepository):ViewModel() {

    fun insertStudent(student: Student):Completable{
        return mainRepository.insertStudent(student)
    }
    fun updateStudent(student: Student):Completable{
        return mainRepository.updateStudent(student)
    }
}