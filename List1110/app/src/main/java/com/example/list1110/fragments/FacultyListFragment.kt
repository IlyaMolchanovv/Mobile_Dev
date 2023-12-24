package com.example.list1110.fragments

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.list1110.MainActivity
import com.example.list1110.NameOfFragment
import com.example.list1110.R
import com.example.list1110.data.Faculty
import com.example.list1110.databinding.FragmentFacultyListBinding
import com.example.list1110.interfaces.MainActivityInterface
import java.lang.Exception

class FacultyListFragment : Fragment(), MainActivity.Edit {

    companion object {
        //fun newInstance() = FacultyListFragment()
        private  var INSTANCE: FacultyListFragment? = null
        fun getInstance(): FacultyListFragment{
            if (INSTANCE == null) INSTANCE = FacultyListFragment()
            return INSTANCE ?: throw Exception("FacultyListFragment не создан")
        }
    }

    private lateinit var viewModel: FacultyListViewModel
    private var _binding : FragmentFacultyListBinding? = null
    val  binding
        get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=FragmentFacultyListBinding.inflate(inflater,container,false)
        binding.rvFaculty.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(FacultyListViewModel::class.java)
        viewModel.facultyList.observe(viewLifecycleOwner){
            binding.rvFaculty.adapter=FacultyListAdapter(it)
        }
    }


    private inner class FacultyListAdapter(private val items: List<Faculty>)
        : RecyclerView.Adapter<FacultyListAdapter.ItemHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
            val view = layoutInflater.inflate(R.layout.element_faculty_list, parent, false)
            return ItemHolder(view)
        }

        override fun getItemCount(): Int = items.size
        override fun onBindViewHolder(holder: ItemHolder, position: Int) {
            holder.bind(items[position])
        }
        private var lastView:View?=null
        private fun updateCurrentView (view:View){
            lastView?.findViewById<ConstraintLayout>(R.id.clFaculty)?.setBackgroundColor(
                ContextCompat.getColor(requireContext(),R.color.white))
            view.findViewById<ConstraintLayout>(R.id.clFaculty).setBackgroundColor(
                ContextCompat.getColor(requireContext(),R.color.yellow))
            lastView=view
        }
        private inner class ItemHolder(view:View)
            :RecyclerView.ViewHolder(view) {
            private lateinit var faculty: Faculty
            fun bind(faculty: Faculty) {
                this.faculty = faculty
                if(faculty==viewModel.faculty)
                    updateCurrentView(itemView)
                val tv = itemView.findViewById<TextView>(R.id.tvFaculty)
                tv.text=faculty.name
                tv.setOnClickListener{
                    viewModel.setCurrentFaculty(faculty)
                    updateCurrentView(itemView)
                }
                tv.setOnLongClickListener{
                    tv.callOnClick()
                    (requireActivity() as MainActivityInterface).showFragment(NameOfFragment.GROUP)
                    true
                }
            }
        }

    }

    override fun append() {
        editFaculty()
    }

    override fun update() {
        editFaculty(viewModel.faculty?.name?:"")
    }

    override fun delete() {
        deleteDialog()
    }
    private fun deleteDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление")
            .setMessage("Действительно хотите удалить? ${viewModel.faculty?.name?:""}")
            .setPositiveButton("Да"){_,_ ->
                viewModel.deleteFaculty()
            }
            .setNegativeButton("Нет", null)
            .setCancelable(true)
            .create()
            .show()
    }
    private fun editFaculty(facultyName: String=""){
        val mDialogView =LayoutInflater.from(requireContext()).inflate(R.layout.dialog_string,null)
        val messageText=mDialogView.findViewById<TextView>(R.id.tvInfo)
        val inputString=mDialogView.findViewById<EditText>(R.id.etString)
        inputString.setText(facultyName)
        messageText.text="Факультет"
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("Изменить")
            .setView(mDialogView)
            .setPositiveButton("Подтверждаю"){_,_->
                if(inputString.text.isNotBlank()){
                    if(facultyName.isBlank())
                        viewModel.appendFaculty(inputString.text.toString())
                    else
                        viewModel.updateFaculty((inputString.text.toString()))
                }
            }
            .setNegativeButton("Отмена",null)
            .setCancelable(true)
            .create()
            .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as MainActivityInterface).updateTitle("Список факультетов")
    }
}
