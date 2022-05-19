package com.febryan.e_market.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.febryan.e_market.R
import com.febryan.e_market.api.ApiConfig
import com.febryan.e_market.helper.SharedPreference
import com.febryan.e_market.model.ResponseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.edt_email
import kotlinx.android.synthetic.main.activity_register.edt_pass
import kotlinx.android.synthetic.main.activity_register.tv_isi_dummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var sph: SharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sph = SharedPreference(this)

        btn_login.setOnClickListener {
            login()
        }

    }

    private fun login() {
        val email = edt_email_login.text.toString()
        val pass = edt_pass_login.text.toString()

        if (email.isEmpty()) {
            edt_email.error = "Isi dulu !"
            return
        }

        if (pass.isEmpty()) {
            edt_pass.error = "Isi dulu !"
            return
        }



        ApiConfig.instanceRetrofit.login(email, pass)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {

                    val respon = response.body()


                    if (respon != null) {
                        if (respon.status == 0) {
                            Toast.makeText(
                                this@LoginActivity,
                                respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Welcome " + respon.data?.name,
                                Toast.LENGTH_SHORT
                            ).show()
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