package c0m.quantum.epifi.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import java.time.Year
import java.util.regex.Pattern

object Utility {

    fun launchUrlWithCustomTab(uri: Uri, articleColor: Int, context: Context) {
        CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(articleColor)
                    .setNavigationBarColor(articleColor)
                    .build()
            )
            .setShareState(CustomTabsIntent.SHARE_STATE_ON)
            .setUrlBarHidingEnabled(true)
            .build().let { chromeTab ->
                try {
                    chromeTab.launchUrl(context, uri)
                } catch (e: Exception) {
                    //context.showToast("Could not open article")
                    e.printStackTrace()
                }
            }
    }

    ///inputFormate MM/DD/YYYY
    @RequiresApi(Build.VERSION_CODES.O)
    fun validateDate(date: String): Boolean {

        val regex = "^(0[0-9]||1[0-2])/([0-2][0-9]||3[0-1])/([0-9][0-9])?[0-9][0-9]$"
        val matcher = Pattern.compile(regex).matcher(date)
        return if (matcher.matches()) {
            matcher.reset()
            if (matcher.find()) {
                val currentYear = Year.now().value
                val dateDetails = date.split("/")
                val day: String = dateDetails[1]
                val month: String = dateDetails[0]
                val year: String = dateDetails[2]
                if (validateMonthWithMaxDate(day, month)) {
                    false
                }
                else if (isFebruaryMonth(month)) {
                    if (isLeapYear(year)) {
                        leapYearWith29Date(day)
                    } else {
                        notLeapYearFebruary(day)
                    }
                }
                else if(year.length!=4){
                    return false
                }
                else if(year.toInt()>currentYear){
                    return false
                }
                else {
                    true
                }
            } else {
                false
            }
        } else {
            false
        }
    }

    private fun validateMonthWithMaxDate(day: String, month: String): Boolean = day == "31" && (month == "4" || month == "6" || month == "9" || month == "11" || month == "04" || month == "06" || month == "09")
    private fun isFebruaryMonth(month: String): Boolean = month == "2" || month == "02"
    private fun isLeapYear(year: String): Boolean = year.toInt() % 4 == 0
    private fun leapYearWith29Date(day: String): Boolean = !(day == "30" || day == "31")
    private fun notLeapYearFebruary(day: String): Boolean = !(day == "29" || day == "30" || day == "31")

}