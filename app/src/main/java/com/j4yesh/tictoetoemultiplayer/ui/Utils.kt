package com.j4yesh.tictoetoemultiplayer.ui

import android.app.Activity
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.j4yesh.tictoetoemultiplayer.Data.Network.Resource
import androidx.fragment.app.Fragment
import com.j4yesh.tictoetoemultiplayer.ui.Auth.LoginFragment
import com.j4yesh.tictoetoemultiplayer.ui.base.BaseFragment

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enable: Boolean) {
    isEnabled = enable
    alpha=if(enable) 1f else 0.5f

}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).apply {
        action?.let {
            setAction("Retry") { it() }
        }
    }.show()
}


fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> {
            requireView().snackbar("Please check your internet connection", retry)
        }

        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*,*,*>).logout()
            }
        }

        else -> {
            val error = failure.errorBody?.string() ?: "Unknown error occurred"
            requireView().snackbar(error)
        }
    }
}


