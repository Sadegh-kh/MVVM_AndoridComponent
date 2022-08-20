package ir.dunijet.studentManager.model
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import ir.dunijet.studentManager.model.local.student.Student
import ir.dunijet.studentManager.model.local.student.StudentDao
import ir.dunijet.studentManager.model.server.ApiService
import ir.dunijet.studentManager.util.studentToJsonObject

class MainRepository(
    private val apiService: ApiService,
    private val studentDao: StudentDao
) {

    fun getAllStudent():LiveData<List<Student>>{
        return studentDao.getAllStudent()
    }
    //caching
    fun refreshData():Completable{

        return apiService.getAllStudent()
            .doOnSuccess {
                studentDao.insertAll(it)
            }.ignoreElement()
    }


    fun insertStudent(student: Student):Completable{
        val studentJson= studentToJsonObject(student)
        return apiService.insertStudent(studentJson)
            .doOnSuccess {
                studentDao.insertOrUpdate(student)
            }.ignoreElement()
    }
    fun updateStudent(student: Student):Completable{
        val studentJson= studentToJsonObject(student)
        return apiService.updateStudent(student.id!!,studentJson)
            .doOnSuccess {
                studentDao.insertOrUpdate(student)
            }
            .ignoreElement()
    }
    fun deleteStudent(id: Int):Completable{
        return apiService.deleteStudent(id)
            .doOnSuccess {
                studentDao.deleteItem(id)
            }
            .ignoreElement()
    }
}