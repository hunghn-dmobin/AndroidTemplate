package com.app.base.dialog.toolbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import appsgenz.template.base.BaseAppFragment.Companion.FRAGMENT_REQUEST_RESULT
import appsgenz.template.base.dialog.BaseDialogFragment
import appsgenz.template.base.dialog.listener.OnActionInDialogListener

/**
 * @author HungHN on 09/15/2023.
 * This class is used to notify events from
 *
 * DialogFragment -> Fragment host
 * Fragment -> target Fragment
 */
object NotifyUtil {

    fun notifyAction(
        isDismiss: Boolean,
        dialog: BaseDialogFragment?,
        requestCode: Int,
        action: Int,
        result: Bundle?
    ) {
        if (dialog == null) {
            throw IllegalStateException("Dialog cannot be null !")
        }

        if (isDismiss) dialog.dismiss()

        val responseFragment = dialog.parentFragment
        if (responseFragment != null && responseFragment is OnActionInDialogListener) {
            (responseFragment as OnActionInDialogListener).onDialogResult(
                requestCode,
                action,
                result
            )
            return
        }

        val activity = dialog.activity
        if (activity != null && activity is OnActionInDialogListener) {
            (activity as OnActionInDialogListener).onDialogResult(requestCode, action, result)
        }
    }

    fun notifyFragmentAction(fragment: Fragment?, result: Bundle) {
        if (fragment == null) return
        fragment.setFragmentResult(requestKey = FRAGMENT_REQUEST_RESULT, result)
    }
}
