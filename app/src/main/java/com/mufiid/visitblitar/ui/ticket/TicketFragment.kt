package com.mufiid.visitblitar.ui.ticket

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.visitblitar.databinding.FragmentTicketBinding
import com.mufiid.visitblitar.ui.detailticket.DetailTicketActivity
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.viewmodel.ViewModelFactory


class TicketFragment : Fragment() {
    private lateinit var binding: FragmentTicketBinding
    private lateinit var viewModel: TicketViewModel
    private lateinit var ticketAdapter: TicketAdapter
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
        ticketAdapter = TicketAdapter()

        // set UI recycler view
        with(binding.rvMyTicket) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ticketAdapter
        }

        // init view model factory
        val factory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, factory)[TicketViewModel::class.java]

        context?.let { context ->
            UserPref.getUserData(context)?.id?.let {
                viewModel.getMyTicket(it)
            }
        }

        // Observe message
        viewModel.message.observe(viewLifecycleOwner, {
            showToast(it)
        })

        // Observe loading
        viewModel.loading.observe(viewLifecycleOwner, { state ->
            if (state) {
                binding.shimmerTicketContainer.startShimmer()
                binding.shimmerTicketContainer.visibility = View.VISIBLE
            } else {
                binding.shimmerTicketContainer.stopShimmer()
                binding.shimmerTicketContainer.visibility = View.GONE
            }
        })

        // Observe data ticket
        viewModel.ticket.observe(viewLifecycleOwner, { ticket ->
            if (!ticket.isNullOrEmpty()) {

                // set list of ticket to adapter
                ticketAdapter.apply {
                    addTicket(ticket)
                }

            } else {
                binding.layoutEmpty.visibility = View.VISIBLE
            }
        })
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (data != null) {
//            if (resultCode == DetailTicketActivity.RESULT_COMING) {
//                showToast("Welcome")
//            } else if (resultCode == DetailTicketActivity.RESULT_GO_HOME) {
//                showToast("Bye")
//            }
//        }
//    }
}