package py.com.alemiranda95.zemoga.utils.extension

import android.view.View
import androidx.recyclerview.widget.RecyclerView

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun RecyclerView.clear() {
    this.let {
        it.adapter = null
        it.layoutManager = null
    }
}