package ir.dunijet.studentManager.model
import androidx.lifecycle.LiveData
import io.reactivex.Completable
import ir.dunijet.studentManager.model.local.student.Student
import ir.dunijet.studentManager.model.local.student.StudentDao
import ir.dunijet.studentManager.model.server.ApiService
import ir.dunijet.studentManager.util.studentToJsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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


    suspend fun insertStudent(student: Student):String{
        val studentJson= studentToJsonObject(student)
        val resultInsert=apiService.insertStudent(studentJson)
        studentDao.insertOrUpdate(student)
        return resultInsert
    }
    suspend fun updateStudent(student: Student):String{
        val studentJson= studentToJsonObject(student)
        val resultUpdate=apiService.updateStudent(student.id!!,studentJson)
        studentDao.insertOrUpdate(student)

        return resultUpdate
    }
    fun deleteStudent(id: Int):Completable{
        return apiService.deleteStudent(id)
            .doOnSuccess {
                studentDao.deleteItem(id)
            }
            .ignoreElement()
    }
}