package com.emersonfiwre.testesofie.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.emersonfiwre.testesofie.R
import com.emersonfiwre.testesofie.service.constants.TaskConstants
import com.emersonfiwre.testesofie.service.model.TaskModel
import com.emersonfiwre.testesofie.viewmodel.TaskViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_task.*

class CreateTaskActivity : AppCompatActivity() {
    private lateinit var mViewModel: TaskViewModel
    private lateinit var mTaskModel: TaskModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)
        //Iniciando a ViewModel
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        observe()

        loadData()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close);
        supportActionBar?.title = getString(R.string.new_task)
    }

    private fun loadData() {
        val bundle = intent.extras;
        if (bundle != null) {
            mTaskModel = bundle.getSerializable(TaskConstants.BUNDLE.TASK_BUNDLE) as TaskModel

            edit_email.setText(mTaskModel.email)
            edit_name_task.setText(mTaskModel.title)
            edit_descricao.setText(mTaskModel.description)
            /* Carrega tarefa
            if (this.mTaskId != 0) {
                button_save.setText(R.string.update_task)
                mViewModel.load(mTaskId)
            }*/
        }
    }

    private fun observe() {
        mViewModel.validation.observe(this, Observer {
            if (it.success()) {
                Toast.makeText(this, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, it.failure(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_save -> {
                val email = edit_email.text.toString()
                val taskName = edit_name_task.text.toString()
                val desc = edit_descricao.text.toString()

                val task = TaskModel().apply {
                    this.id = mTaskModel.id
                    this.email = email
                    this.description = desc
                    this.title = taskName
                }

                mViewModel.save(task)
                true
            }
            android.R.id.home -> {
                super.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}