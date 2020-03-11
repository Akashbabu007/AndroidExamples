package com.example.views

import android.content.Context
import android.util.AttributeSet


class MyTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    )
}
