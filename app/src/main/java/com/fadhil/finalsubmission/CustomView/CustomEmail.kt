package com.fadhil.finalsubmission.CustomView

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.utils.isEmailValid
import com.google.android.material.textfield.TextInputEditText


class CustomEmail: TextInputEditText {

    constructor(context: Context): super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super (context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputan = s.toString()
                when {

                    inputan.isBlank() -> error = context.getString(R.string.error_email)
                    !inputan.isEmailValid()-> error = context.getString(R.string.error_invalid_email)
                }
            }

            override fun afterTextChanged(p0: Editable?) {


            }
        })
    }


}