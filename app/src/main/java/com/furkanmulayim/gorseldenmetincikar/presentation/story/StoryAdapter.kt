package com.furkanmulayim.gorseldenmetincikar.presentation.story

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.gorseldenmetincikar.R
import com.furkanmulayim.gorseldenmetincikar.model.Message
import com.furkanmulayim.gorseldenmetincikar.utils.showMessage


class StoryAdapter(
    private val dataList: ArrayList<Message>, val contex: Context
) : RecyclerView.Adapter<StoryAdapter.ViewHolder>() {
    private var lastAddedItemPosition: Int = -1

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val sagBosluk: ImageView = itemView.findViewById(R.id.sagdan)
        val solBosluk: ImageView = itemView.findViewById(R.id.soldan)

        val message: TextView = itemView.findViewById(R.id.message)
        val mBack: ConstraintLayout = itemView.findViewById(R.id.messageBack)
        val isim: TextView = itemView.findViewById(R.id.isim)
        val photo: ImageView = itemView.findViewById(R.id.photo)
        val lay: LinearLayout = itemView.findViewById(R.id.lay)
    }

    private fun copyText(textToCopy: String) {
        val clipboardManager =
            contex.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textToCopy)
        clipboardManager.setPrimaryClip(clipData)
        contex.showMessage("Metin Panoya Kopyalandı 📝")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.message.text = item.mesaj

        if (position == lastAddedItemPosition) {
            val animation: Animation = AnimationUtils.loadAnimation(contex, R.anim.anim)
            // Animasyonu butona uygula
            holder.lay.startAnimation(animation)
        }


        if (item.isuser) {
            holder.sagBosluk.visibility = View.GONE
            holder.solBosluk.visibility = View.VISIBLE
            holder.isim.visibility = View.GONE
            holder.photo.visibility = View.GONE
            holder.mBack.setBackgroundResource(R.drawable.me_message_back)
        } else {
            holder.solBosluk.visibility = View.GONE
            holder.sagBosluk.visibility = View.VISIBLE
            holder.isim.visibility = View.VISIBLE
            holder.photo.visibility = View.VISIBLE
            holder.mBack.setBackgroundResource(R.drawable.ai_back)
        }


        holder.message.setOnLongClickListener {
            copyText(textToCopy = item.mesaj)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Message>) {
        dataList.clear()
        dataList.addAll((newList))
        lastAddedItemPosition = newList.size - 1
        notifyItemInserted(newList.size - 1)
        notifyDataSetChanged()
    }
}

