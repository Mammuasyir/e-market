package com.febryan.e_market.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.febryan.e_market.R
import com.febryan.e_market.api.ApiConfig
import com.febryan.e_market.model.ResponseUser
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_regis.setOnClickListener {
            register()
        }

        tv_isi_dummy.setOnClickListener {
            dataDummy()
        }

    }

    private fun dataDummy() {
        edt_nama.setText("Febryan")
        edt_email.setText("@gmail.com")
        edt_notel.setText("082191170001")
        edt_pass.setText("2345")
    }

    private fun register() {
        val nama = edt_nama.text.toString()
        val email = edt_email.text.toString()
        val telp = edt_notel.text.toString()
        val pass = edt_pass.text.toString()

        if (nama.isEmpty()) {
            edt_nama.error = "Isi dulu !"
            return
        }

        if (email.isEmpty()) {
            edt_email.error = "Isi dulu !"
            return
        }

        if (telp.isEmpty()) {
            edt_notel.error = "Isi dulu !"
            return
        }

        if (pass.isEmpty()) {
            edt_pass.error = "Isi dulu !"
            return
        }

        ApiConfig.instanceRetrofit.registrasi(nama, email, telp, pass)
            .enqueue(object : Callback<ResponseUser> {
                override fun onResponse(
                    call: Call<ResponseUser>,
                    response: Response<ResponseUser>
                ) {

                    val respon = response.body()


                    if (respon != null) {
                        if (respon.status == 0) {
                            Toast.makeText(
                                this@RegisterActivity,
                                respon.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Welcome " + respon.data?.name,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


                }

                override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                    Toast.makeText(this@RegisterActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}