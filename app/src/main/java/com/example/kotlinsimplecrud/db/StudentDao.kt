package com.example.kotlinsimplecrud.db

import androidx.room.*
import com.example.kotlinsimplecrud.model.Student

@Dao
interface StudentDao {

    @Insert
    fun insert(student: Student)

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM students")
    fun getAll() : List<Student>

    @Query("SELECT * FROM students WHERE id = :id")
    fun getById(id: Int) : List<Student>
}