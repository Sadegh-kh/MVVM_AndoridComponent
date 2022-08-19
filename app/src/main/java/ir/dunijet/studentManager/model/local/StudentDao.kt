package ir.dunijet.studentManager.model.local

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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