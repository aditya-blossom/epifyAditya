package c0m.quantum.epifi

import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText
import java.util.regex.Pattern


var panFilter = InputFilter { source, start, end, dest, dstart, dend ->
    for (i in start until end) {
        if (!Pattern.compile("[ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789]*")
                .matcher(source[i].toString()).matches()
        ) {
            return@InputFilter ""
        }
    }
    null
}


val spaceFilter = InputFilter { source, _, _, _, _, _ ->
    val blockCharacterSet = " "
    if (source != null && blockCharacterSet.contains("" + source)) {
        ""
    } else null
}

var uppercase = object : InputFilter.AllCaps() {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence {
        return source.toString().uppercase()
    }
}

fun EditText.onChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            cb(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}


fun EditText.onChangePanNumber(cb: (String) -> Unit) {
    //this.filters = arrayOf(uppercase)
    onChange {
        cb(it)
    }
}