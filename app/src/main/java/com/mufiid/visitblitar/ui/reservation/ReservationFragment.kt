package com.mufiid.visitblitar.ui.reservation

import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.FragmentReservationBinding
import com.mufiid.visitblitar.view.ReservationView
import io.reactivex.rxjava3.disposables.CompositeDisposable

class ReservationFragment : Fragment(), ReservationView {
    private lateinit var binding: FragmentReservationBinding
    private lateinit var presenter: ReservationPresenter
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
        init()
        presenter.getReservation()
        searchingData()
    }

    private fun init() {
        presenter = ReservationPresenter(this)
    }

    private fun searchingData() {
        binding.svTouristAttraction.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getReservation(query)
                binding.svTouristAttraction.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDisposable().clear()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getDataReservation(message: String?, data: List<TourismEntity>) {
        binding.rvWisataReservation.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = ReservationAdapter(data)
        }
    }

    override fun failedGetDataReservation(message: String?) {
        showToast(message)
    }

    override fun loading(state: Boolean) {
        if (state) {
            binding.shimmerReservationContainer.startShimmer()
            binding.shimmerReservationContainer.visibility = View.VISIBLE
        } else {
            binding.shimmerReservationContainer.stopShimmer()
            binding.shimmerReservationContainer.visibility = View.GONE
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}