package ir.dunijet.studentManager.mainScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ir.dunijet.studentManager.addStudent.AddStudentActivity
import ir.dunijet.studentManager.databinding.ActivityMainBinding
import ir.dunijet.studentManager.model.MainRepository
import ir.dunijet.studentManager.model.local.MyDatabase
import ir.dunijet.studentManager.model.local.student.Student
import ir.dunijet.studentManager.recycler.StudentAdapter
import ir.dunijet.studentManager.util.*


class MainActivity : AppCompatActivity(), StudentAdapter.StudentEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: StudentAdapter
    private var compositeDisposable = CompositeDisposable()
    lateinit var mainViewModel: MainViewModel
    private var listSize = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)

        mainViewModel=ViewModelProvider(this,MainViewModelFactory(
            MainRepository(
                ApiServiceSingleton.apiService!!,
                MyDatabase.getDatabase(applicationContext).studentDao
            )
        )).get(MainViewModel::class.java)

        initUi()

    }

    private fun initUi() {

        binding.btnAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            intent.putExtra(Constants.STUDENT_INSERT_KEY, listSize + 1)
            startActivity(intent)
        }
        initRecycler()

        mainViewModel.getAllStudent().observe(this){
            listSize=it.size

            //refresh Recycler view when list changed
            myAdapter.refreshData(it)
        }

        logErrors()

    }

    private fun logErrors() {
        mainViewModel.getError().observe(this){
            Log.v("errors",it)
        }
    }

    private fun initRecycler() {
        myAdapter = StudentAdapter(ArrayList(), this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onItemClicked(student: Student, position: Int) {
        updateDataInServer(student)
    }
    private fun updateDataInServer(student: Student) {

        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra(Constants.STUDENT_UPDATE_KEY, student)
        startActivity(intent)

    }

    override fun onItemLongClicked(student: Student, position: Int) {
        val dialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
        dialog.contentText = "Delete this Item?"
        dialog.cancelText = "cancel"
        dialog.confirmText = "confirm"
        dialog.setOnCancelListener {
            dialog.dismiss()
        }
        dialog.setConfirmClickListener {

            deleteDataFromServer(student, position)
            dialog.dismiss()

        }
        dialog.show()
    }
    private fun deleteDataFromServer(student: Student, position: Int) {
        mainViewModel
            .deleteStudent(student.id!!)
            .asyncRequest()
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onComplete() {
                    showToast("Delete item success ")
                }

                override fun onError(e: Throwable) {
                    showToast("i can't delete this item because : ${e.message}")
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }


}