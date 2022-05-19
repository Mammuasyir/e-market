package com.febryan.e_market.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.febryan.e_market.R
import com.febryan.e_market.activity.WelcomeActivity
import com.febryan.e_market.databinding.FragmentNotificationsBinding
import com.febryan.e_market.helper.SharedPreference
import kotlinx.android.synthetic.main.activity_welcome.*
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {


    lateinit var sph: SharedPreference
    lateinit var btnLogout: TextView
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_notifications, container,false)

        init(view)
        sph= SharedPreference(requireActivity())

        btnLogout.setOnClickListener {
            sph.setStatusLogin(false)
            Toast.makeText(activity, "Berhasil logout", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, WelcomeActivity::class.java))
            activity?.finishAffinity()
        }

        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun init(view: View){
        btnLogout = view.findViewById(R.id.tv_logout)
    }
}