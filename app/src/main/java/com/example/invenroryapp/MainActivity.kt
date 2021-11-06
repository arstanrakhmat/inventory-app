package com.example.invenroryapp

import android.annotation.SuppressLint
import android.graphics.drawable.ClipDrawable.VERTICAL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.invenroryapp.db.UserEntity


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.RowClickListener {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: MainActivityViewModel

    private val saveButton = findViewById<Button>(R.id.saveButton)
    private val name = findViewById<EditText>(R.id.name)
    private val price = findViewById<EditText>(R.id.price)
    private val quantity = findViewById<EditText>(R.id.quantity)
    private val supplier = findViewById<EditText>(R.id.supplier)

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerViewAdapter = RecyclerViewAdapter(this@MainActivity)
            adapter = recyclerViewAdapter

            val divider = DividerItemDecoration(applicationContext, VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getAllUsersObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })


        saveButton.setOnClickListener {
            val saveName = name.text.toString()
            val savePrice = price.text.toString()
            val saveQuantity = quantity.text.toString()
            val saveSupplier = supplier.text.toString()

            if (saveButton.text.equals("Save")) {
                val user = UserEntity(0, saveName, savePrice, saveQuantity, saveSupplier)
                viewModel.insertUserInfo(user)
            } else {
                val user = UserEntity(name.getTag(name.id).toString().toInt(), saveName, savePrice, saveQuantity, saveSupplier)
                viewModel.updateUserInfo(user)
                saveButton.setText("Save")
            }

            name.setText("")
            price.setText("")
            quantity.setText("")
            supplier.setText("")
        }
    }

    override fun onDeleteUserClickListener(user: UserEntity) {
        viewModel.deleteUserInfo(user)
    }

    override fun onItemClickListener(user: UserEntity) {
        name.setText(user.name)
        price.setText(user.price)
        quantity.setText(user.quantity)
        supplier.setText(user.supplier)

        name.setTag(name.id, user.id)

        saveButton.setText("Update")
    }
}