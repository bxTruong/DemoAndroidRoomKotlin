package com.truongbx.demoandroidroomkotlin

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.truongbx.demoandroidroomkotlin.databinding.ItemBookBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class BookAdapter(
    private val bookList: ArrayList<Book>,
    private val context: Context,
    private val itemClick: ItemClick
) : RecyclerView.Adapter<BookAdapter.UserHolder>(), CoroutineScope by MainScope() {
    private lateinit var binding: ItemBookBinding
    private val bookDatabase = BookDatabase

    class UserHolder(binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_book,
            parent,
            false
        )
        return UserHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val book = bookList[position]
        binding.book = book
        holder.itemView.setOnClickListener {
            context.toast("${book.name} $position")
            itemClick.setOnItemClick(position)
        }
        holder.itemView.setOnLongClickListener {
            openDialog(position)
            return@setOnLongClickListener true
        }
    }

    private fun openDialog(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete this data ?")
        builder.setMessage("Are you sure")
        builder.setPositiveButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.setNegativeButton("OK") { dialog, _ ->
            bookList.toMutableList().removeAt(position)
            context.toast("Delete Book Successful ${bookList.size}")
            launch {
                val book = bookList[position]
                bookDatabase.getDatabase(context).userDAO().deleteBook(book)
                bookList.remove(book)
                context.toast("Delete Book Successful ${bookList.size}")
            }
            dialog.dismiss()
            notifyItemRemoved(position)
        }
        builder.show()
    }


}