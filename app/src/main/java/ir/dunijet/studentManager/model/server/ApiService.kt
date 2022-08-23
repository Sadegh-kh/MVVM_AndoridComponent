package ir.dunijet.studentManager.model.server

import com.google.gson.JsonObject
import io.reactivex.Single
import ir.dunijet.studentManager.model.local.student.Student
import retrofit2.http.*


interface ApiService {

    @GET("/student")
    fun getAllStudent():Single<List<Student>>

    @POST("/student")
    suspend fun insertStudent(@Body body:JsonObject):String

    @PUT("/student/update{Id}")
    suspend fun updateStudent(@Path("Id")id:Int,@Body jsonObject: JsonObject):String

    @DELETE("/student/delete{Id}")
    fun deleteStudent(@Path("Id")id:Int):Single<String>
}