package com.mufiid.visitblitar.ui.ticket

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.databinding.ItemMyTicketBinding
import com.mufiid.visitblitar.ui.detailticket.DetailTicketActivity

class TicketAdapter(private val data: List<TicketEntity>?): RecyclerView.Adapter<TicketAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemMyTicketBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("StringFormatMatches")
        fun bind(ticket: TicketEntity) {
            val res = itemView.resources
            with(binding) {
                name.text = ticket.nameTouristAttraction
                totalVisitor.text = res.getString(R.string.total_visitor, ticket.totalVisitors)
                date.text = res.getString(R.string.date, ticket.date)
                status.text = when (ticket.statusIsComing) {
                    1 -> res.getString(R.string.status_is_coming, res.getString(R.string.have_arrived))
                    2 -> res.getString(R.string.status_is_coming, res.getString(R.string.goodbye))
                    else -> res.getString(R.string.status_is_coming, res.getString(R.string.yet_coming))
                }
                container.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTicketActivity::class.java).apply {
                        putExtra(DetailTicketActivity.EXTRA_RESERVATION, ticket)
                    }
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketAdapter.ViewHolder {
        val itemMyTicketBinding = ItemMyTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemMyTicketBinding)
    }

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
        data?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = data?.size ?: 0
}