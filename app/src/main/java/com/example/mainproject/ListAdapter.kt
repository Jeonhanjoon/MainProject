package com.example.mainproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.mainproject.viewmodel.AccountViewModel
import com.google.android.material.snackbar.Snackbar

class ListAdapter(val list: ArrayList<Things>,val viewModel: AccountViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.memo.text = list[position].name
        holder.Budget.text = list[position].budget.toString() + "원"
        holder.Category.text = list[position].category.toString()
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val memo: TextView = itemView.findViewById(R.id.memo) ?: TextView(itemView.context)
        val Budget: TextView = itemView.findViewById(R.id.budget) ?: TextView(itemView.context)
        val Category: TextView = itemView.findViewById(R.id.category1) ?: TextView(itemView.context)

        fun bind(thing: Things) {
            memo.text = thing.name
            Budget.text = thing.budget.toString()
            Category.text = thing.category

            // ViewHolder의 init 블록에서 스와이프하여 삭제 처리
            val itemTouchHelper =
                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val deletedThing = list[viewHolder.adapterPosition]

                        // ViewModel에서 deleteThing 메서드 호출
                        viewModel.deleteThing(deletedThing)

                        // 로컬 리스트를 업데이트할 필요 없음. 변경 사항은 데이터베이스에서 자동으로 반영됨
                    }
                })

            itemTouchHelper.attachToRecyclerView(itemView as RecyclerView)
        }
    }

}


