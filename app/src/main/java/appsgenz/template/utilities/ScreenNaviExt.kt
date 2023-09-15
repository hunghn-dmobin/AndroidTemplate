@file:Suppress("unused")

package appsgenz.template.utilities

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import appsgenz.template.R
import appsgenz.template.base.BaseAppFragment


fun Fragment.actionbar(): ActionBar? {
    return (activity as? AppCompatActivity)?.supportActionBar
}

fun Fragment.goBack(): Boolean {
    return findNavController().popBackStack()
}

fun FragmentActivity.goBack(): Boolean {
    return when (val page = activePage()) {
        is BaseAppFragment -> {
            return page.onBack()
        }
        else -> false
    }
}

fun FragmentActivity.activePage(holder: Int = R.id.navHost): Fragment? {
    return supportFragmentManager.findFragmentById(holder)?.childFragmentManager?.fragments?.firstOrNull()
}

fun Fragment.activePage(holder: Int = R.id.navHost): Fragment? {
    return parentFragmentManager.findFragmentById(holder)?.childFragmentManager?.fragments?.firstOrNull()
}

fun Fragment.activeChildPage(holder: Int = R.id.childNavHost): Fragment? {
    return childFragmentManager.findFragmentById(holder)
}