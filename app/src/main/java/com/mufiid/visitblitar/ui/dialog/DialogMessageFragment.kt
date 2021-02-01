package com.mufiid.visitblitar.ui.dialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.mufiid.visitblitar.R
import com.mufiid.visitblitar.databinding.FragmentDialogMessageBinding
import com.mufiid.visitblitar.ui.addreservation.AddReservationActivity
import com.mufiid.visitblitar.ui.login.LoginActivity

class DialogMessageFragment : DialogFragment(), View.OnClickListener {

    private lateinit var binding: FragmentDialogMessageBinding
    private var titleMessage: String? = ""
    private var message: String? = ""
    private var buttonListener: ButtonListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogMessageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToLoginPage.setOnClickListener(this)
        binding.btnBack.setOnClickListener(this)
        dialog?.setCanceledOnTouchOutside(false)
    }

    override fun onResume() {
        super.onResume()
        val params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            titleMessage = savedInstanceState.getString(TITLE)
            message = savedInstanceState.getString(MESSAGE)
        }

        if (arguments != null) {
            titleMessage = arguments?.getString(TITLE)
            message = arguments?.getString(MESSAGE)

            binding.titleMessage.text = titleMessage
            binding.message.text = message
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_to_login_page -> {
                dialog?.dismiss()
                startActivity(Intent(context, LoginActivity::class.java).apply {
                    putExtra(LoginActivity.CHECK_LOGIN, true)
                })
            }
            R.id.btn_back -> {
                if(buttonListener != null) {
                    buttonListener?.choose()
                }
                dialog?.dismiss()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = requireActivity()
        if (activity is AddReservationActivity) {
            this.buttonListener = activity.buttonListener
        }
    }

    companion object {
        var TITLE = "title"
        var MESSAGE = "message"
    }

    interface ButtonListener {
        fun choose()
    }

}