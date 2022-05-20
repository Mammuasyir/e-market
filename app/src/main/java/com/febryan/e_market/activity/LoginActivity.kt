package com.febryan.e_market.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.febryan.e_market.MainActivity
import com.febryan.e_market.R
import com.febryan.e_market.api.ApiConfig
import com.febryan.e_market.helper.SharedPreference
import com.febryan.e_market.model.ResponseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.edt_email
import kotlinx.android.synthetic.main.activity_login.edt_pass
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sPH: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            login()
        }

        sPH = SharedPreference(this)
    }

    private fun login() {
        val email = edt_email.text.toString()
        val pass = edt_pass.text.toString()

        if (email.isEmpty()) {
            edt_email.error = "Isi dulu !"
            return
        }

        if (pass.isEmpty()) {
            edt_pass.error = "Isi dulu !"
            return
        }


        pb.visibility = View.VISIBLE
        ApiConfig.instanceRetrofit.login(email, pass)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {
                    pb.visibility = View.GONE
                    val respon = response.body()

                    if (respon != null) {
                        if (respon.status == 0) {
                            Toast.makeText(
                                this@LoginActivity,
                                respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            pb.visibility = View.GONE
                            sPH.setStatusLogin(true)
                            sPH.setUser(respon.data!!)
                            val i = Intent(this@LoginActivity, MainActivity::class.java)
                            Toast.makeText(
                                this@LoginActivity,
                                respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(i)
                            finish()
                        }
                    }


                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}