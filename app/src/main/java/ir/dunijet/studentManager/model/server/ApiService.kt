package ir.dunijet.studentManager.model.server

import com.google.gson.JsonObject
import io.reactivex.Single
import ir.dunijet.studentManager.model.local.Student
import retrofit2.http.*


interface ApiService {

    @GET("/student")
    fun getAllStudent():Single<List<Student>>

    @POST("/student")
    fun insertStudent(@Body body:JsonObject):Single<String>

    @PUT("/student/update{Id}")
    fun updateStudent(@Path("Id")id:Int,@Body jsonObject: JsonObject):Single<String>

    @DELETE("/student/delete{Id}")
    fun deleteStudent(@Path("Id")id:Int):Single<String>
}