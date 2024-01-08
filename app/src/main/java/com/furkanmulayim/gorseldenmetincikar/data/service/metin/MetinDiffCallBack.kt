package com.furkanmulayim.gorseldenmetincikar.data.service.metin

import androidx.recyclerview.widget.DiffUtil
import com.furkanmulayim.gorseldenmetincikar.domain.model.Metin


class MetinDiffCallback(
    private val oldList: List<Metin>,
    private val newList: List<Metin>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem == newItem
    }
}