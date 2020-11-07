package com.truongbx.demoandroidroomkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.truongbx.demoandroidroomkotlin.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(),
    CoroutineScope by MainScope(), ItemClick {

    private lateinit var binding: ActivityMainBinding
    private val bookDatabase = BookDatabase
    lateinit var book2: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initRecyclerView()

        binding.btnUpdate.setOnClickListener {
            updateData(book2)
        }
    }

    fun insertBook(view: View) {
        val name = binding.edtNameBook.text.toString().trim()
        val category = binding.edtCategory.text.toString().trim()
        if (name.isEmpty()) {
            binding.edtNameBook.error = "Do not blank name"
            binding.edtNameBook.requestFocus()
            return
        }
        if (category.isEmpty()) {
            binding.edtCategory.error = "Do not blank category"
            binding.edtCategory.requestFocus()
            return
        }
        launch {
            val book = Book(name, category, R.drawable.a)
            bookDatabase.getDatabase(this@MainActivity).userDAO().insertBook(book)
            this@MainActivity.toast("Save Book Successful")
            clearForm()
            initRecyclerView()
        }
    }

    private fun initRecyclerView() {
        binding.rcBook.layoutManager = LinearLayoutManager(this)
        binding.rcBook.setHasFixedSize(true)
        launch {
            val bookList = bookDatabase.getDatabase(this@MainActivity).userDAO()
                .getAllBook()
            binding.rcBook.adapter =
                BookAdapter(bookList as ArrayList<Book>, this@MainActivity, this@MainActivity)
            binding.rcBook.adapter?.notifyDataSetChanged()
            binding.rcBook.scrollToPosition(bookList.size - 1)
            toast("${bookList.size}")
        }
    }

    private fun clearForm() {
        binding.edtNameBook.text = null
        binding.edtCategory.text = null
    }

    override fun setOnItemClick(position: Int) {
        launch {
            val bookList = bookDatabase.getDatabase(this@MainActivity).userDAO().getAllBook()
            binding.edtNameBook.setText(bookList[position].name)
            binding.edtCategory.setText(bookList[position].category)
            book2 = bookList[position]
        }
    }

    fun clearForm(view: View) {
        clearForm()
    }

    private fun updateData(book: Book) {
        val name = binding.edtNameBook.text.toString().trim()
        val category = binding.edtCategory.text.toString().trim()
        if (name.isEmpty()) {
            binding.edtNameBook.error = "Do not blank name"
            binding.edtNameBook.requestFocus()
            return
        }
        if (category.isEmpty()) {
            binding.edtCategory.error = "Do not blank category"
            binding.edtCategory.requestFocus()
            return
        }
        launch {
            book.let {
                it.name = name
                it.category = category
            }
            bookDatabase.getDatabase(this@MainActivity).userDAO().updateBook(book)
            this@MainActivity.toast("Update Book Successful")
            clearForm()
            initRecyclerView()
        }
    }

}