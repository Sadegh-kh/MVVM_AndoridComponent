package ir.dunijet.studentManager.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.dunijet.studentManager.databinding.ItemMainBinding
import ir.dunijet.studentManager.model.local.student.Student

class StudentAdapter(private val data: ArrayList<Student>, val studentEvent: StudentEvent) :
    RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    lateinit var binding: ItemMainBinding

    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bindViews(student: Student) {

            binding.txtName.text = "${student.firstName} ${student.lastName}"
            binding.txtCourse.text = student.course
            binding.txtScore.text = student.score.toString()
            binding.txtHarfAval.text = student.firstName[0].uppercaseChar().toString()

            itemView.setOnClickListener {
                studentEvent.onItemClicked(student, adapterPosition)
            }

            itemView.setOnLongClickListener {
                studentEvent.onItemLongClicked(student, adapterPosition)
                true
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {

        binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding.root)

    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {

        holder.bindViews(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun refreshData(it: List<Student>?) {
        data.clear()
        data.addAll(ArrayList(it!!))
        notifyDataSetChanged()

    }

    interface StudentEvent {

        fun onItemClicked(student: Student, position: Int)
        fun onItemLongClicked(student: Student, position: Int)

    }


}