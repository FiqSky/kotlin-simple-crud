package com.example.kotlinsimplecrud

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinsimplecrud.db.StudentDao
import com.example.kotlinsimplecrud.db.StudentRoomDatabase
import com.example.kotlinsimplecrud.model.Student

class EditActivity : AppCompatActivity() {

    val STUDENT_EXTRA = "student_extra"
    private lateinit var student: Student
    private var isUpdate = false
    private lateinit var database: StudentRoomDatabase
    private lateinit var dao: StudentDao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val btn_delete = findViewById<Button>(R.id.btn_delete)
        val btn_save = findViewById<Button>(R.id.btn_save)
        val et_body = findViewById<EditText>(R.id.et_body)
        val et_nim = findViewById<EditText>(R.id.et_nim)
        val et_title = findViewById<EditText>(R.id.et_title)
        database = StudentRoomDatabase.getDatabase(applicationContext)
        dao = database.getStudentDao()

        if (intent.getParcelableExtra<Student>(STUDENT_EXTRA) != null){
            btn_delete.visibility = View.VISIBLE
            isUpdate = true
            student = intent.getParcelableExtra(STUDENT_EXTRA)!!
            et_title.setText(student.name)
            et_nim.setText(student.nim)
            et_body.setText(student.quote)

            student.name?.let { et_title.setSelection(it.length) }

        }

        btn_save.setOnClickListener {
            val name = et_title.text.toString()
            val nim = et_nim.text.toString()
            val quote = et_body.text.toString()

            if (name.isEmpty() && nim.isEmpty()){
                Toast.makeText(applicationContext, "Student cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else{
                if (isUpdate){
                    saveStudent(Student(id = student.id, name = name, nim = nim, quote = quote))
                }
                else{
                    saveStudent(Student(name = name, nim = nim, quote = quote))
                }
            }
            finish()
        }

        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to Delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
                        deleteStudent(student)
                        finish()
                    }
                    .setNegativeButton("No") { dialogInterface: DialogInterface, _: Int ->
                        // Dismiss the dialog
                        dialogInterface.dismiss()
                    }
            val alert = builder.create()
            alert.show()
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