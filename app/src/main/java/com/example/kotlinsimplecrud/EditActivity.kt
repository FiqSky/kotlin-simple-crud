package com.example.kotlinsimplecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.kotlinsimplecrud.db.StudentDao
import com.example.kotlinsimplecrud.db.StudentRoomDatabase
import com.example.kotlinsimplecrud.model.Student
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    val STUDENT_EXTRA = "student_extra"
    private lateinit var student: Student
    private var isUpdate = false
    private lateinit var database: StudentRoomDatabase
    private lateinit var dao: StudentDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        database = StudentRoomDatabase.getDatabase(applicationContext)
        dao = database.getStudentDao()

        if (intent.getParcelableExtra<Student>(STUDENT_EXTRA) != null){
            btn_delete.visibility = View.VISIBLE
            isUpdate = true
            student = intent.getParcelableExtra(STUDENT_EXTRA)!!
            et_title.setText(student.title)
            et_body.setText(student.body)

            et_title.setSelection(student.title.length)

        }

        btn_save.setOnClickListener {
            val title = et_title.text.toString()
            val body = et_body.text.toString()

            if (title.isEmpty() && body.isEmpty()){
                Toast.makeText(applicationContext, "Student cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                if (isUpdate){
                    saveStudent(Student(id = student.id, title = title, body = body))
                }
                else{
                    saveStudent(Student(title = title, body = body))
                }
            }

            finish()
        }

        btn_delete.setOnClickListener {
            deleteStudent(student)
            finish()
        }

    }

    private fun saveStudent(student: Student){

        if (dao.getById(student.id).isEmpty()){

            dao.insert(student)
        }
        else{

            dao.update(student)
        }

        Toast.makeText(applicationContext, "Student saved", Toast.LENGTH_SHORT).show()

    }

    private fun deleteStudent(student: Student){
        dao.delete(student)
        Toast.makeText(applicationContext, "Student removed", Toast.LENGTH_SHORT).show()
    }
}