package com.example.list1110

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.addCallback
import com.example.list1110.data.Student
import com.example.list1110.fragments.FacultyListFragment
import com.example.list1110.fragments.GroupFragment
import com.example.list1110.fragments.StudentFragment
import com.example.list1110.interfaces.MainActivityInterface
import com.example.list1110.repository.AppRepository

class MainActivity : AppCompatActivity() ,MainActivityInterface{
    interface Edit{
        fun append()
        fun update()
        fun delete()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this){
            if(supportFragmentManager.backStackEntryCount>0){
                supportFragmentManager.popBackStack()
                when (activeFragment){
                    NameOfFragment.GROUP ->{
                        activeFragment=NameOfFragment.FACULTY
                    }
                    NameOfFragment.STUDENT ->{
                        activeFragment=NameOfFragment.GROUP
                    }
                    NameOfFragment.FACULTY ->{
                        finish()
                    }
                    else -> {}
                }
                updateMenu(activeFragment)
            }
            else {
                finish()
            }
        }
        showFragment(activeFragment,null)
    }

    private var _miAppendFaculty: MenuItem? = null
    private var _miUpdateFaculty: MenuItem? = null
    private var _miDeleteFaculty: MenuItem? = null
    private var _miAppendGroup: MenuItem? = null
    private var _miUpdateGroup: MenuItem? = null
    private var _miDeleteGroup: MenuItem? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        _miAppendFaculty = menu?.findItem(R.id.miAppendFaculty)
        _miUpdateFaculty = menu?.findItem(R.id.miUpdateFaculty)
        _miDeleteFaculty = menu?.findItem(R.id.miDeleteFaculty)
        _miAppendGroup = menu?.findItem(R.id.miAppendGroup)
        _miDeleteGroup = menu?.findItem(R.id.miDeleteGroup)
        _miUpdateGroup = menu?.findItem(R.id.miUpdateGroup)
        updateMenu(activeFragment)
        return true
    }

    var activeFragment: NameOfFragment=NameOfFragment.FACULTY

    private fun updateMenu(fragmentType: NameOfFragment){
        _miAppendFaculty?.isVisible = fragmentType==NameOfFragment.FACULTY
        _miUpdateFaculty?.isVisible = fragmentType==NameOfFragment.FACULTY
        _miDeleteFaculty?.isVisible = fragmentType==NameOfFragment.FACULTY
        _miAppendGroup?.isVisible = fragmentType==NameOfFragment.GROUP
        _miDeleteGroup?.isVisible = fragmentType==NameOfFragment.GROUP
        _miUpdateGroup?.isVisible = fragmentType==NameOfFragment.GROUP
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.miAppendFaculty -> {
                val f=(FacultyListFragment.getInstance() as Edit)
                f.append()
                true
            }
            R.id.miUpdateFaculty -> {
                val f=(FacultyListFragment.getInstance() as Edit)
                f.update()
                true
            }
            R.id.miDeleteFaculty -> {
                val f=(FacultyListFragment.getInstance() as Edit)
                f.delete()
                true
            }
            R.id.miAppendGroup -> {
                val fedit: Edit = GroupFragment.getInstance()
                fedit.append()
                true
            }
            R.id.miUpdateGroup -> {
                val fedit: Edit = GroupFragment.getInstance()
                fedit.update()
                true
            }
            R.id.miDeleteGroup -> {
                val fedit: Edit = GroupFragment.getInstance()
                fedit.delete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showFragment(fragmentType: NameOfFragment, student: Student?) {
        when (fragmentType){
            NameOfFragment.FACULTY ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain,FacultyListFragment.getInstance())
                    .addToBackStack(null)
                    .commit()
            }
            NameOfFragment.GROUP ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain,GroupFragment.newInstance())
                    .addToBackStack(null)
                    .commit()
            }
            NameOfFragment.STUDENT ->{
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fcMain, StudentFragment.newInstance(student!!))
                    .addToBackStack(null)
                    .commit()
            }
        }
        activeFragment=fragmentType
        updateMenu(fragmentType)
    }

    override fun updateTitle(newTitle: String) {
        title=newTitle
    }

    override fun onStop() {
        AppRepository.getInstance().saveData()
        super.onStop()
    }
}