package com.example.mainproject

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.FragmentAssetPlanListBinding
import com.example.mainproject.databinding.FragmentAssetPlanListBinding.*
import com.example.mainproject.viewmodel.AccountViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


class assetPlanListFragment : Fragment() {
    var binding: FragmentAssetPlanListBinding? = null

    val model: AccountViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAssetPlanListBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        binding?.addBtn?.setOnClickListener { findNavController().navigate(R.id.action_assetPlanListFragment_to_setAssetPlanFragment) }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding=DataBindingUtil?.setContentView<FragmentFetchBinding>(this,R.layout.fragment_fetch)
        binding?.list?.layoutManager = LinearLayoutManager(context)
        model.things.observe(viewLifecycleOwner) { thing ->
            binding?.list?.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = thing?.let { ListAdapter(it,model) }
            }
        }
    }





    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}