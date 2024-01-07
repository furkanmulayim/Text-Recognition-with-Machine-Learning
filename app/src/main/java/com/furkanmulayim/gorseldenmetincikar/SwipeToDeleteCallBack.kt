package com.furkanmulayim.gorseldenmetincikar
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.furkanmulayim.gorseldenmetincikar.presentation.history.HistoryAdapter

class SwipeToDeleteCallback(private val adapter: HistoryAdapter,private val ct:Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {


    private val backgroundPaint = Paint().apply {

        color = ContextCompat.getColor(ct,R.color.delete)
        style = Paint.Style.FILL

    }

    private val iconMargin = 30 // Silme ikonu kenar boşluğu

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.removeItem(position)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Sağa doğru kaydırma sırasında arkaplanı çizin
        c.drawRoundRect(
            itemView.right.toFloat(),
            itemView.top.toFloat(),
            itemView.left.toFloat() + dX,
            itemView.bottom.toFloat(),
            30.0F,
            30.0F,
            backgroundPaint
        )

        // Silme ikonunu eklemek için viewHolder.itemView'i kullanın
        val icon = ContextCompat.getDrawable(ct, R.drawable.trash)
            if (icon != null) {
                val iconHeight = 80
                val iconWidth = 80

                val iconTop = itemView.top + (itemHeight - iconHeight) / 2
                val iconBottom = iconTop + iconHeight

                // İkona sağ kenar boşluğunu ekleyin
                val iconLeft = itemView.right - iconMargin - iconWidth
                val iconRight = itemView.right - iconMargin

                // İkonu çizin
                icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                icon.draw(c)
            }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}
