package com.mufiid.visitblitar.ui.ticket

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity
import com.mufiid.visitblitar.databinding.FragmentTicketBinding
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.view.TicketView
import io.reactivex.rxjava3.disposables.CompositeDisposable


class TicketFragment : Fragment(), TicketView {
    private lateinit var binding : FragmentTicketBinding
    private lateinit var presenter: TicketPresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTicketBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        presenter = TicketPresenter(this)

        context?.let { context ->
            UserPref.getUserData(context)?.id?.let {
                presenter.getAllMyTicket(
                    it
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDisposable().clear()
    }

    override fun getDataMyTicket(message: String?, data: List<TicketEntity>?) {

        if (data.isNullOrEmpty()) {
            binding.layoutEmpty.visibility = View.VISIBLE
        } else {
            binding.rvMyTicket.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = TicketAdapter(data)
            }
        }

    }

    override fun failedGetDataMyTicket(message: String?) {
        showToast(message)
    }

    override fun loading(state: Boolean) {
        if (state) {
            binding.shimmerTicketContainer.startShimmer()
            binding.shimmerTicketContainer.visibility = View.VISIBLE
        } else {
            binding.shimmerTicketContainer.stopShimmer()
            binding.shimmerTicketContainer.visibility = View.GONE
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}