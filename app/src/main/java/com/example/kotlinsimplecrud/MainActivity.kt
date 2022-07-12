package com.example.kotlinsimplecrud

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinsimplecrud.adapter.StudentAdapter
import com.example.kotlinsimplecrud.db.StudentRoomDatabase
import com.example.kotlinsimplecrud.model.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        getStudentData()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, EditActivity::class.java))
        }
    }

    private fun getStudentData(){
        val database = StudentRoomDatabase.getDatabase(applicationContext)
        val dao = database.getStudentDao()
        val listItems = arrayListOf<Student>()
        val tv_empty = findViewById<TextView>(R.id.tv_empty)

        listItems.addAll(dao.getAll())
        setupRecyclerView(listItems)
        if (listItems.isNotEmpty()){
            tv_empty.visibility = View.GONE
        }
        else{
            tv_empty.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView(listItems: ArrayList<Student>){
        val rv_main = findViewById<RecyclerView>(R.id.rv_main)
        rv_main.apply {
            adapter = StudentAdapter(listItems, object : StudentAdapter.StudentListener{
                override fun OnItemClicked(student: Student) {
                    val intent = Intent(this@MainActivity, EditActivity::class.java)
                    intent.putExtra(EditActivity().STUDENT_EXTRA, student)
                    startActivity(intent)
                }
            })
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profil -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        getStudentData()
    }
}