package com.furkanmulayim.gorseldenmetincikar.presentation.history

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.gorseldenmetincikar.ItemSwipeListener
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.SwipeToDeleteCallback
import com.furkanmulayim.gorseldenmetincikar.data.service.metin.MetinDiffCallback
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin


class HistoryAdapter(
    private val dataList: ArrayList<Metin>,
    ct: Context,
    recyclerView: RecyclerView,
    private val itemSwipeListener: ItemSwipeListener
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(this, ct))

    init {
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ay: TextView = itemView.findViewById(R.id.ay)
        val gun: TextView = itemView.findViewById(R.id.gun)
        val metin: TextView = itemView.findViewById(R.id.metin)
        val itemm: LinearLayout = itemView.findViewById(R.id.itemm)
        val saat: TextView = itemView.findViewById(R.id.saat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_saved_text, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.ay.text = item.ay.substring(0,3).uppercase()
        holder.gun.text = item.gun
        holder.metin.text = item.metin
        holder.saat.text = item.saat

        holder.itemm.setOnClickListener {
            val act =
                HistoryFragmentDirections.actionHistoryFragmentToDetailHistoryFragment(item.uuid)
            Navigation.findNavController(it).navigate(act)
        }
    }

    fun removeItem(position: Int) {
        if (position in 0 until dataList.size) {
            val eskilist = dataList
            val deletedItem = dataList[position].uuid
            itemSwipeListener.onItemSwiped(deletedItem)
            dataList.removeAt(position)
            val diffCallback = MetinDiffCallback(eskilist, dataList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            diffResult.dispatchUpdatesTo(this)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Metin>) {
        dataList.clear()
        dataList.addAll((newList))
        notifyDataSetChanged()
    }
}

