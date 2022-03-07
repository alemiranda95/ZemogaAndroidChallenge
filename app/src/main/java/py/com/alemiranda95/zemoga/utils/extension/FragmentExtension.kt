package py.com.alemiranda95.zemoga.utils.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import py.com.alemiranda95.zemoga.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

fun Fragment.showErrorSnackbar(error: String, showRetry: Boolean, func: () -> Unit) {
    val snackbar = Snackbar.make(
        activity!!.findViewById(android.R.id.content),
        error,
        BaseTransientBottomBar.LENGTH_SHORT
    )

    if (showRetry) snackbar.setAction(R.string.retry_error_button) { func.invoke() }

    snackbar.show()
}

fun Fragment.showToast(message: String) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()