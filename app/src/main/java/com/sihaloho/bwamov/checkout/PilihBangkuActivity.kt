package com.sihaloho.bwamov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sihaloho.bwamov.checkout.model.Checkout
import com.sihaloho.bwamov.home.model.Film
import com.sihaloho.bwamov.R
import kotlinx.android.synthetic.main.activity_pilih_bangku.*

class PilihBangkuActivity : AppCompatActivity() {

    var statusA3:Boolean = false
    var statusA4:Boolean = false
    var total:Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilih_bangku)

        val data = intent.getParcelableExtra<Film>("data")

        tv_kursi.text = data.judul

        a3.setOnClickListener {
            if (statusA3) {
                a3.setImageResource(R.drawable.ic_rectangle_empty)
                statusA3 = false
                total -=1
                belitiket(total)

            } else {
                a3.setImageResource(R.drawable.ic_rectangle_selected)
                statusA3 = true
                total +=1
                belitiket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        a4.setOnClickListener {
            if (statusA4) {
                a4.setImageResource(R.drawable.ic_rectangle_empty)
                statusA4 = false
                total -=1
                belitiket(total)
            } else {
                a4.setImageResource(R.drawable.ic_rectangle_selected)
                statusA4 = true
                total +=1
                belitiket(total)

                val data = Checkout("A4", "70000")
                dataList.add(data)
            }
        }

        btn_home.setOnClickListener {

                val intent = Intent(
                    this,
                    CheckoutActivity::class.java
                ).putExtra("data", dataList)
                startActivity(intent)
        }

    }

    private fun belitiket(total:Int) {
        if (total == 0) {
            btn_home.setText("Beli Tiket")
            btn_home.visibility = View.INVISIBLE
        } else {
            btn_home.setText("Beli Tiket ("+total+")")
            btn_home.visibility = View.VISIBLE
        }

    }
}
