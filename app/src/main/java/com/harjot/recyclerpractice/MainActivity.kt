package com.harjot.recyclerpractice

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.harjot.recyclerpractice.databinding.ActivityMainBinding
import com.harjot.recyclerpractice.databinding.DialogLayoutBinding

class MainActivity : AppCompatActivity(),RecyclerInterface {
    lateinit var binding: ActivityMainBinding
    var arrayList = ArrayList<UserModel>()
    var recyclerAdapter = RecyclerAdapter(arrayList,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = recyclerAdapter

        binding.fabAdd.setOnClickListener {
            dialog()
        }
    }

    override fun onListclicked(position: Int) {
        dialog(position)
    }
    fun dialog(position: Int = -1){
        var dialogBinding = DialogLayoutBinding.inflate(layoutInflater)
        var dialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
            window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            if (position == -1){
                dialogBinding.btnAdd.setText("Add")
                dialogBinding.tvText.setText("Add Item")
                dialogBinding.btnDelete.visibility = View.GONE
            }else{
                dialogBinding.btnAdd.setText("Update")
                dialogBinding.tvText.setText("Update Item")
                dialogBinding.btnDelete.visibility = View.VISIBLE
                dialogBinding.etName.setText(arrayList[position].name)
                dialogBinding.etRollNo.setText(arrayList[position].rollNo.toString())
                dialogBinding.etSubject.setText(arrayList[position].subject)
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etName.text.toString().trim().isNullOrEmpty()){
                    dialogBinding.etName.error = "Enter Name"
                }else  if (dialogBinding.etRollNo.text.toString().trim().isNullOrEmpty()){
                    dialogBinding.etRollNo.error = "Enter RollNo"
                }else  if (dialogBinding.etSubject.text.toString().trim().isNullOrEmpty()){
                    dialogBinding.etSubject.error = "Enter Subject"
                }else{
                    if (position > -1){
                        arrayList[position] = UserModel(
                            dialogBinding.etName.text.toString(),
                            dialogBinding.etRollNo.text.toString().toInt(),
                            dialogBinding.etSubject.text.toString()
                        )
                    }else{
                        arrayList.add(UserModel(
                            dialogBinding.etName.text.toString(),
                            dialogBinding.etRollNo.text.toString().toString().toInt(),
                            dialogBinding.etSubject.text.toString())
                        )
                    }
                    recyclerAdapter.notifyDataSetChanged()
                    dismiss()
                }
            }
            dialogBinding.btnDelete.setOnClickListener {
                var alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Delete Item")
                alertDialog.setMessage("Do you want to delete the item?")
                alertDialog.setCancelable(false)
                alertDialog.setNegativeButton("No") { _, _ ->
                    alertDialog.setCancelable(true)
                }
                alertDialog.setPositiveButton("Yes") { _, _ ->
                    if (arrayList.size == 0){
                        Toast.makeText(this@MainActivity, "List Is Empty", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Toast.makeText(
                            this@MainActivity,
                            "The item is  deleted",
                            Toast.LENGTH_SHORT
                        ).show()
                        arrayList.removeAt(position)
                        recyclerAdapter.notifyDataSetChanged()
                        dismiss()
                    }
                }
                alertDialog.show()
            }
            show()
        }
    }
}