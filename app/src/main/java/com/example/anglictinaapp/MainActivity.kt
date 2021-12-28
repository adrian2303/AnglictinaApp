package com.example.anglictinaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:StudentAdapter? = null
    private var std:StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)

        btnAdd.setOnClickListener { addStudent() }
        btnView.setOnClickListener { getStudents() }
        btnUpdate.setOnClickListener { updateStudent() }

        adapter?.setOnClickItem {
            Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show()
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDeleteItem {
            deleteStudent(it.id)
        }
    }

    private fun getStudents() {
        val stdList = sqliteHelper.getAllStudents()
        Log.e("pppp", "${stdList.size}")
        adapter?.addItems(stdList)
        stdList.sortBy {
            it.name
        }
    }

    private fun addStudent() {
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (name.isEmpty()) {
            edName.error = getString(R.string.prazdneSlovo)
        }else if (email.isEmpty()){
            edEmail.error = getString(R.string.prazdnyPreklad)
//        if (name.isEmpty()  || email.isEmpty()) {
//            Toast.makeText(this, "Please eneter required field ", Toast.LENGTH_SHORT).show()
        }else {
            val std = StudentModel(name = name, email = email)
            val status = sqliteHelper.insertStudent(std)
            //Kontrola vlozenia zaznamu
            if (status > -1) {
                Toast.makeText(this, getString(R.string.toastPridania), Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudents()
            } else {
                Toast.makeText(this, getString(R.string.toastNepridania), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateStudent(){
        val name = edName.text.toString()
        val email = edEmail.text.toString()

//      kontrola ci nebol zaznam zmeneny
        if (name == std?.name && email == std?.email){
            Toast.makeText(this,getString(R.string.toastNezmenene), Toast.LENGTH_SHORT).show()
            return
        }

        if (std == null) return

        val std = StudentModel(id = std!!.id, name = name, email = email)
        val status = sqliteHelper.updateStudent(std)
        if (status > -1) {
            Toast.makeText(this, getString(R.string.toastZmenene), Toast.LENGTH_SHORT).show()
            clearEditText()
            getStudents()
        }else{
            Toast.makeText(this, getString(R.string.toastProblemPridanie),Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(id:Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.setMessageVymazanieSlova))
        builder.setCancelable(true)
        builder.setPositiveButton(getString(R.string.setPositiveVymazanieSlova)) { dialog, _ ->
            sqliteHelper.deleteStudentById(id)
            getStudents()
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.setNegativeVymazanieSlova)) { dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        edName = findViewById(R.id.edname)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }

}