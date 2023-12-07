package com.example.list1110.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list1110.R
import com.example.list1110.data.Group
import com.example.list1110.data.Student
import com.example.list1110.databinding.FragmentStudentsBinding

class StudentsFragment : Fragment() {

    companion object {
        private lateinit var group: Group
        fun newInstance(group: Group): StudentsFragment {
            this.group = group
            return StudentsFragment()
        }
    }

    private lateinit var viewModel: StudentsViewModel
    private lateinit var _binding: FragmentStudentsBinding
    val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        binding.rvStudents.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)
        viewModel.setGroup(group)
        viewModel.studentList.observe(viewLifecycleOwner) {
            binding.rvStudents.adapter = StudentAdapter(it)
        }
    }

    private fun deleteDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление!")
            .setMessage("Вы действительно хотите удалить студента ${viewModel.student?.shortName ?: ""}?")
            .setPositiveButton("ДА") { _, _ ->
                viewModel.deleteStudent()
            }
            .setNegativeButton("НЕТ", null)
            .setCancelable(true)
            .create()
            .show()

    }

    private fun editStudent(facultyName: String = "") {
        TODO("Здесь вызов фрагмента ввода студента")
    }

    private inner class StudentAdapter(private val items: List<Student>) :
        RecyclerView.Adapter<StudentAdapter.ItemHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): StudentAdapter.ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_student_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: StudentAdapter.ItemHolder, position: Int) {
            holder.bind(viewModel.studentList.value!![position])
        }

        private var lastView: View? = null
        private fun updateCurrentView(view: View) {
            lastView?.findViewById<ConstraintLayout>(R.id.clFaculty)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.white)
            )
            view.findViewById<ConstraintLayout>(R.id.clFaculty).setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.yellow)
            )
            lastView = view
        }

        private inner class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
            private lateinit var student: Student
            fun bind(student: Student) {
                this.student = student
                if (student == viewModel.student)
                    updateCurrentView(itemView)
                val tv = itemView.findViewById<TextView>(R.id.tvFaculty)
                tv.text = student.shortName
                tv.setOnClickListener {
                    viewModel.setCurrentStudent(student)
                    updateCurrentView((itemView))
                }
            }
        }
    }
}

