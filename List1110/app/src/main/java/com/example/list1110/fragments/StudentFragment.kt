package com.example.list1110.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.list1110.R
import com.example.list1110.data.Student
import com.example.list1110.databinding.FragmentStudentBinding
import com.example.list1110.repository.AppRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat

private const val ARG_PARAM1 = "param_student"

class StudentFragment : Fragment() {
    private lateinit var student: Student
    private lateinit var _binding : FragmentStudentBinding

    val binding
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val param1 = it.getString(ARG_PARAM1)
            if (param1==null)
                student=Student()
            else {
                val paramType = object : TypeToken<Student>() {}.type
                student = Gson().fromJson<Student>(param1,paramType)
            }
        }
    }

    @SuppressLint("SimpleDataFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentStudentBinding.inflate(inflater,container,false)

        val sexArray = resources.getStringArray(R.array.SEX)
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_spinner_item, sexArray)
        binding.spSex.adapter = adapter
        binding.spSex.setSelection(student.sex)
        binding.spSex.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                student.sex=position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //write code to perform some action
            }
        }
        binding.cvBirthDate.setOnDateChangeListener { view, year, month, dayOfMonth ->
            student.birthDate.time =
                SimpleDateFormat("yyyy.MM.dd").parse("$year.$month.$dayOfMonth")?.time ?: student.birthDate.time
        }
        binding.etFirstName.setText(student.firstName)
        binding.etLastName.setText(student.lastName)
        binding.etMiddleName.setText(student.middleName)
        binding.etPhone.setText(student.phone)
        binding.cvBirthDate.date = student.birthDate.time
        binding.btnCancel.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            student.lastName = binding.etLastName.text.toString()
            student.firstName = binding.etFirstName.text.toString()
            student.middleName = binding.etMiddleName.text.toString()
            student.phone = binding.etPhone.text.toString()
            AppRepository.getInstance().addStudent(student)

            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(student: Student?) =
            StudentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, Gson().toJson(student))
                }
            }
    }
}