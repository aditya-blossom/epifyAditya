package c0m.quantum.epifi.viewModel

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import c0m.quantum.epifi.utils.Utility
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.time.Year
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.reflect.typeOf


class TestViewModel : ViewModel() {

    private var mTestViewModelObserver: TestViewModelObserver? = null

    fun attachObserver(observer: TestViewModelObserver) {
        mTestViewModelObserver = observer
    }
    private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        Log.d("exception",""+exception.message)
    }






    @RequiresApi(Build.VERSION_CODES.O)
    fun validatePAN_And_BirthDate(pan_no: String, date: String?=null, month: String?=null, year: String?=null){

        if(validatePAN(pan_no) && validateBirthDate(date,month,year)){
            mTestViewModelObserver?.enableNextButton(true)
        }else{
            mTestViewModelObserver?.enableNextButton(false)
        }
    }

    fun validatePAN(pan_no: String):Boolean {

        if (pan_no.length == 10) {


            val pattern: Pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

            val matcher: Matcher = pattern.matcher(pan_no)

            if(matcher.matches()){
                return true
            }else{
                return false
            }


        } else {
            return false
        }


    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun validateBirthDate(date: String?=null, month: String?=null, year: String?=null) :Boolean {


        if(date.isNullOrEmpty() ||month.isNullOrEmpty() ||year.isNullOrEmpty()  ){
           return false
        }else{
            val bitrhDate=month+"/"+date+"/"+year
            return Utility.validateDate(bitrhDate)

        }



    }

    fun onNextButtonClicked(){
        viewModelScope.launch(exceptionHandler) {
            mTestViewModelObserver?.onNextButtonClicked()
        }
    }





}