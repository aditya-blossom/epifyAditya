package c0m.quantum.epifi.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.*
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import c0m.quantum.epifi.R
import c0m.quantum.epifi.onChange
import c0m.quantum.epifi.onChangePanNumber
import c0m.quantum.epifi.utils.Utility
import c0m.quantum.epifi.utils.makeLinks
import c0m.quantum.epifi.viewModel.TestViewModel
import c0m.quantum.epifi.viewModel.TestViewModelObserver
import kotlinx.android.synthetic.main.activity_test.*
import java.text.DecimalFormat
import java.text.NumberFormat


class TestActivity : AppCompatActivity(),TestViewModelObserver ,View.OnClickListener {

    lateinit var viewModel: TestViewModel
    var mdate=""
    var mMonth=""
    var mYear=""
    var mPanNo=""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)


        viewModel=ViewModelProvider(this).get(TestViewModel::class.java)
        viewModel.attachObserver(this)


        enableButton(false)
        tvDisclaimer.makeLinks(
            Pair("Learn more", View.OnClickListener {
                Utility.launchUrlWithCustomTab(
                    Uri.parse("https://www.google.com/"),
                    -16711681,
                    this
                )
            })
        )

        etPanNumber.onChangePanNumber {
            if(it.length==0){
                mPanNo=""
            }else{
                mPanNo=it
            }

            if(it.length==10){
                etPanNumber.clearFocus()
            }
            validate()
        }

        etDate.onChange {
            if(it.length==0){
                mdate=""
            }else if(it.length>=1){
                val f: NumberFormat = DecimalFormat("00")
                mdate=f.format(it.toInt())
                if(it.length==2){
                    etMonth.requestFocus()
                    etYear.clearFocus()
                    etDate.clearFocus()
                }
            }
            validate()
        }

        etDate.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {

                etMonth.clearFocus()
                etYear.clearFocus()
            } else {
                etDate.setText(mdate)
            }
        })

        etMonth.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                etDate.clearFocus()
                etYear.clearFocus()
            } else {
                etMonth.setText(mMonth)
            }
        })

        etYear.setOnFocusChangeListener(OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                etDate.clearFocus()
                etMonth.clearFocus()
            } else {

            }
        })

        etMonth.onChange {
            if(it.length==0){
                mMonth=""
            }else if(it.length>=1){
                val f: NumberFormat = DecimalFormat("00")
                mMonth=f.format(it.toInt())
                if(it.length==2){
                    etYear.requestFocus()
                    etMonth.clearFocus()
                    etDate.clearFocus()
                }
            }
            validate()
        }

        etYear.onChange {
            mYear=it
            if(it.length==0){
                mYear=""
            }

            validate()
        }


        btNext.setOnClickListener(this)
        tvNoPAN_CARD.setOnClickListener(this)





    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun validate(){
        viewModel.validatePAN_And_BirthDate(mPanNo,mdate,mMonth,mYear)
    }

//    fun validatePAN(pan_no:String):Boolean{
//       return  viewModel.validatePAN(pan_no)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun validateDate():Boolean{
//        return viewModel.validateBirthDate()
//    }


    override fun enableNextButton(isEnable: Boolean) {
       enableButton(isEnable)
    }

    override fun onNextButtonClicked() {
        Toast.makeText(this,"Details submitted successfully",Toast.LENGTH_SHORT).show()
        finish()
    }

    fun enableButton(isEnable: Boolean){
        if(isEnable){
            btNext.isEnabled=true
            btNext.setBackgroundColor(btNext.getContext().getResources().getColor(R.color.purple_500));

        }else{
            btNext.isEnabled=false
            btNext.setBackgroundColor(btNext.getContext().getResources().getColor(R.color.color_A3AAB8));
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btNext -> {
                viewModel.onNextButtonClicked()
            }
            R.id.tvNoPAN_CARD -> {
                finish()
            }
        }    }


}