package ir.dunijet.studentManager.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.local.student.Student

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private lateinit var netDisposable: Disposable
    private val errorData = MutableLiveData<String>()

    init {
        mainRepository.refreshData().subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    netDisposable = d
                }

                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.message ?: "unknown error")
                }
            })
    }

    fun getAllStudent(): LiveData<List<Student>> {
        return mainRepository.getAllStudent()
    }

    fun deleteStudent(id: Int): Completable {
        return mainRepository.deleteStudent(id)
    }

    fun getError(): LiveData<String> {
        return errorData
    }

    override fun onCleared() {
        netDisposable.dispose()
        super.onCleared()
    }
}
