package ir.dunijet.studentManager.model.local.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getAllStudent():LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(students:List<Student>)

    @Query("DELETE FROM student WHERE id = :idStudent")
    fun deleteItem(idStudent:Int)
}