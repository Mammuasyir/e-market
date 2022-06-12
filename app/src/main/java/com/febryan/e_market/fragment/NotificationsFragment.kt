package com.febryan.e_market.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.febryan.e_market.R
import com.febryan.e_market.activity.WelcomeActivity
import com.febryan.e_market.databinding.FragmentNotificationsBinding
import com.febryan.e_market.helper.SharedPreference
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    lateinit var sPH: SharedPreference
    lateinit var btnKeluar: Button
    lateinit var namaUser: TextView
    lateinit var emailUser: TextView
    lateinit var telpUser: TextView
    lateinit var img: ImageView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_notifications, container,false)

        init(view)

        sPH = SharedPreference(requireActivity())
        btnKeluar.setOnClickListener {
            sPH.setStatusLogin(false)
            Toast.makeText(activity, "Anda Berhasil Keluar", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, WelcomeActivity::class.java))
            activity?.finish()
        }

        setUser()
        return view

    }

    private fun setUser() {
        val user = sPH.getUser()
        namaUser.text = user?.name
        emailUser.text = user?.email
        telpUser.text = user?.telp

        Glide.with(requireActivity())
            .load("https://ui-avatars.com/api/?name=${user?.name}")
            .into(img)
    }


    private fun init(view: View) {
        btnKeluar = view.findViewById<Button>(R.id.logout)
        namaUser = view.findViewById<TextView>(R.id.tv_user)
        emailUser = view.findViewById<TextView>(R.id.tv_email_user)
        telpUser = view.findViewById<TextView>(R.id.tv_telp_user)
        img = view.findViewById<ImageView>(R.id.img_profile)

    }
}