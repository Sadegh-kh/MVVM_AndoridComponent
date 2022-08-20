package ir.dunijet.studentManager.util

import com.google.gson.GsonBuilder
import ir.dunijet.studentManager.model.server.ApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiServiceSingleton {
    var apiService: ApiService? = null
        get() {
            if (field == null) {

                val gson = GsonBuilder()
                    .setLenient()
                    .create()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

                field=retrofit.create(ApiService::class.java)
            }
            return field
        }
}