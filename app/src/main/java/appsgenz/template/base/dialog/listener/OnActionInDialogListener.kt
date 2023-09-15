package appsgenz.template.base.dialog.listener

import android.os.Bundle

/**
 * @author HungHN on 09/15/2023.
 */
interface OnActionInDialogListener {
    /**
     * @param requestCode the request code
     * @param action      action from dialog
     * @param result   the extra data that will be passed to the source fragment
     */
    fun onDialogResult(requestCode: Int, action: Int, result: Bundle?)
}
