package com.emersonfiwre.testesofie.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.emersonfiwre.testesofie.R
import com.emersonfiwre.testesofie.view.adapter.TaskAdapter
import com.emersonfiwre.testesofie.viewmodel.TaskViewModel
import kotlinx.android.synthetic.main.fragment_tasks.view.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TasksFragment : Fragment() {
    private lateinit var mViewRoot: View
    private lateinit var mViewModel: TaskViewModel
    private val mAdapter: TaskAdapter = TaskAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mViewRoot = inflater.inflate(R.layout.fragment_tasks, container, false)
        mViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        setupRecycler()
        //Criando observadores
        observe()
        //Carregamento da Lista
        mViewModel.list()
        return mViewRoot
    }

    private fun setupRecycler() {
        mViewRoot.recycler_tasks?.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = mAdapter
            it.setHasFixedSize(true)
        }
    }

    private fun observe() {
        mViewModel.taskList.observe(viewLifecycleOwner, Observer {
            mAdapter.notifyChanged(it)
        })
    }

}