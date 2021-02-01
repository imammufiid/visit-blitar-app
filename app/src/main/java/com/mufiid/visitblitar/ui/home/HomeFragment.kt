package com.mufiid.visitblitar.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.data.source.local.entity.TourismEntity
import com.mufiid.visitblitar.databinding.FragmentHomeBinding
import com.mufiid.visitblitar.ui.profiledialog.ProfileDialogFragment
import com.mufiid.visitblitar.utils.pref.AuthPref
import com.mufiid.visitblitar.utils.pref.PrefCore
import com.mufiid.visitblitar.utils.pref.UserPref
import com.mufiid.visitblitar.view.WisataView
import io.reactivex.rxjava3.disposables.CompositeDisposable


class HomeFragment : Fragment(), WisataView, View.OnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private var presenter: HomePresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        presenter?.getTourismRandom(1)
        presenter?.getTourismRecommended(1)
    }

    override fun onDestroy() {
        super.onDestroy()
        CompositeDisposable().clear()
    }


    private fun init() {
        presenter = HomePresenter(this)
        binding.icProfile.setOnClickListener(this)
    }

    override fun getDataWisata(message: String?, data: List<TourismEntity>?) {
        binding.rvWisata.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HomeAdapter(data)
        }
    }

    override fun getDataWisataRecommended(message: String?, data: List<TourismEntity>?) {
        binding.rvRecommended.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TouristRecommendedAdapter(data)

        }
    }

    override fun failedGetDataWisata(message: String?) {
        showToast(message)
    }

    override fun loading(state: Boolean) {
        if (state) {
            binding.shimmerHomeContainer.startShimmer()
            binding.shimmerHomeContainer.visibility = View.VISIBLE
            binding.tvRecommendation.visibility = View.GONE
        } else {
            binding.shimmerHomeContainer.stopShimmer()
            binding.shimmerHomeContainer.visibility = View.GONE
            binding.tvRecommendation.visibility = View.VISIBLE
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showProfileDialog(username: String?) {
        val mBundle = Bundle().apply {
            putString(ProfileDialogFragment.TITLE_DIALOG, username)
        }

        ProfileDialogFragment().apply {
            arguments = mBundle
        }.show(
            childFragmentManager, ProfileDialogFragment::class.java.simpleName
        )
    }

    internal var buttonListener: ProfileDialogFragment.ButtonListener =
        object : ProfileDialogFragment.ButtonListener {
            override fun logout() {
                context?.let {
                    AuthPref.setIsLoggedIn(it, false)
                    PrefCore.clearPrefAuth(it)
                }

            }
        }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ic_profile -> {
                context?.let {
                    var username = UserPref.getUserData(it)?.username
                    if (username?.isEmpty() == true) username = "Kawan"
                    showProfileDialog(username)
                }
            }
        }
    }
}