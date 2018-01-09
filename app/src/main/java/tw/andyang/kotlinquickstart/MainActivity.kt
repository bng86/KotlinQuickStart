package tw.andyang.kotlinquickstart

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatSpinner
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import tw.andyang.kotlinquickstart.data.AppDatabaseImpl
import tw.andyang.kotlinquickstart.data.entity.Todo
import tw.andyang.kotlinquickstart.data.entity.TodoCategory

class MainActivity : AppCompatActivity() {

    private val adapter = TodoAdapter()
    private val database = AppDatabaseImpl.instance()
    private val categories = arrayListOf(TodoCategory.Family, TodoCategory.Friend, TodoCategory.Other)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        loadTodo()
    }

    private fun loadTodo() {
        database.todoDao().findAllTodo()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ adapter.refresh(it) })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menuAdd -> {
                val view = LayoutInflater.from(this).inflate(R.layout.dailog_create_todo, null)

                val spinnerCategory: AppCompatSpinner = view.findViewById(R.id.spinnerCategory)
                spinnerCategory.adapter = ArrayAdapter<TodoCategory>(this, android.R.layout.simple_spinner_dropdown_item, categories)
                val editMessage: EditText = view.findViewById(R.id.editMessage)

                AlertDialog.Builder(this)
                        .setTitle("建立一則待辦事項")
                        .setView(view)
                        .setPositiveButton("新增", { _, _ -> insertTodo(editMessage.text.toString(), spinnerCategory.getCategory()) })
                        .setNegativeButton("取消", { _, _ -> })
                        .create()
                        .show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertTodo(message: String, category: TodoCategory) {
        database.todoDao().insertTodo(Todo(message, category))
        loadTodo()
    }

}

fun AppCompatSpinner.getCategory(): TodoCategory = this.selectedItem as TodoCategory

class TodoAdapter : RecyclerView.Adapter<TodoViewHolder>() {

    private var todos: List<Todo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todos[position]

        holder.run {
            textMessage.text = todo.message
            textCategory.text = todo.todoCategory.display
        }
    }

    override fun getItemCount(): Int = todos.size

    fun refresh(todos: List<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }

}

class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textMessage: TextView = view.findViewById(R.id.textMessage)
    val textCategory: TextView = view.findViewById(R.id.textCategory)
}
