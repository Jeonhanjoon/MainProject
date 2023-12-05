package com.example.mainproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.databinding.FragmentSetAssetPlanBinding
import com.example.mainproject.viewmodel.AccountViewModel


class setAssetPlanFragment : Fragment() {

    lateinit var binding:  FragmentSetAssetPlanBinding
    val model: AccountViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentSetAssetPlanBinding.inflate(layoutInflater)

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = listOf("- 선택하세요 -", "취미 및 여가", "식비", "쇼핑", "저축", "교통비", "공부")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinner?.adapter = adapter
        binding?.btnSaveData?.setOnClickListener {
            val name = binding.InputMemo.text.toString()
            val budget = binding.InputP.text.toString()
            val category = binding.spinner.selectedItem.toString()

            if (name.isBlank() || budget.isBlank() || category == "- 선택하세요 -") {
                Toast.makeText(requireActivity(), "입력창에 빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                val user = Things(name, budget, category)
                model.addUser(user)
             findNavController().navigate(R.id.action_setAssetPlanFragment_to_assetPlanListFragment)
            }
        }
        /*val simpleCallback=object: ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper
            .START or ItemTouchHelper.END,0){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }

        }*/

    }

}
