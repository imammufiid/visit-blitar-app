package com.mufiid.reservation.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.visitblitar.databinding.FragmentReservationBinding
import com.mufiid.reservation.viewmodel.ViewModelFactory
import com.mufiid.visitblitar.vo.Status

class ReservationFragment : Fragment() {
    private lateinit var binding: FragmentReservationBinding
    private lateinit var reservationAdapter : ReservationAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservationBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchingData()

        if (activity != null) {
            reservationAdapter = ReservationAdapter()
            viewModelObserver()
        }
    }

    private fun viewModelObserver() {
        val factory = ViewModelFactory.getInstance(requireActivity())
        val viewModel = ViewModelProvider(this, factory)[ReservationViewModel::class.java]

        viewModel.getAllTourism().observe(viewLifecycleOwner, { tourism ->
            if (tourism != null) {
                when (tourism.status) {
                    Status.LOADING -> {
                        binding.shimmerReservationContainer.startShimmer()
                        binding.shimmerReservationContainer.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (tourism.data != null) {
                            binding.shimmerReservationContainer.stopShimmer()
                            binding.shimmerReservationContainer.visibility = View.GONE
                            reservationAdapter.submitList(tourism.data)
                        }
                    }
                    Status.ERROR -> {
                        showToast(tourism.message)
                    }
                }
            }
            with(binding.rvWisataReservation) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = reservationAdapter
            }
        })
    }

    private fun searchingData() {
        binding.svTouristAttraction.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                binding.svTouristAttraction.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}