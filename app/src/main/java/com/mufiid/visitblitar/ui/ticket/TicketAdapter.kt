package com.mufiid.visitblitar.ui.ticket

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.databinding.ItemMyTicketBinding
import com.mufiid.visitblitar.ui.detailticket.DetailTicketActivity
import com.mufiid.visitblitar.utils.helper.DiffCallback

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    private val data = ArrayList<TicketEntity>()

    //    private val click: OnClickItem? = null
    private var context: Context? = null

    inner class ViewHolder(val binding: ItemMyTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(ticket: TicketEntity) {
            val res = itemView.resources
            with(binding) {
                name.text = ticket.nameTouristAttraction
                totalVisitor.text = res.getString(R.string.total_visitor, ticket.totalVisitors)
                date.text = res.getString(R.string.date, ticket.date)
                status.text = when (ticket.statusIsComing) {
                    1 -> res.getString(
                        R.string.status_is_coming,
                        res.getString(R.string.have_arrived)
                    )
                    2 -> res.getString(R.string.status_is_coming, res.getString(R.string.goodbye))
                    else -> res.getString(
                        R.string.status_is_coming,
                        res.getString(R.string.yet_coming)
                    )
                }
                container.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTicketActivity::class.java).apply {
                        putExtra(DetailTicketActivity.EXTRA_RESERVATION, ticket)
                        putExtra(DetailTicketActivity.EXTRA_POSITION, adapterPosition)
                    }
//                    if (ticket.statusIsComing == 0) {
//                            context.start
//                        (context as Activity).startActivityForResult(
//                            intent,
//                            DetailTicketActivity.REQUEST_COMING
//                        )
//                    } else if (ticket.statusIsComing == 1){
//                        (context as Activity).startActivityForResult(
//                            intent,
//                            DetailTicketActivity.REQUEST_GO_HOME
//                        )
//                    }
                    itemView.context.startActivity(intent)
//                    click?.clicked(data[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketAdapter.ViewHolder {
        val itemMyTicketBinding =
            ItemMyTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(itemMyTicketBinding)
    }

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
        data[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int = data.size

    fun addTicket(data: List<TicketEntity>) {
//        val diffCallback = DiffCallback(this.data, data)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
    }

//    interface OnClickItem {
//        fun clicked(item: TicketEntity?)
//    }
}