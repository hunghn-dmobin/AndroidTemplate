package appsgenz.template.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import appsgenz.template.base.dialog.LoadingDialogFragment
import appsgenz.template.base.dialog.listener.OnActionInDialogListener
import appsgenz.template.utilities.AppLog

/**
 * @author HungHN on 09/15/2023.
 * This is base App fragment.
 * It contains some default attributes:  Api, menu
 */
@Suppress("unused")
abstract class BaseAppFragment : Fragment(), OnActionInDialogListener {

    private var mHandler: Handler? = null
    protected open val progressView: View?
        get() = (activity as? BaseAppActivity)?.progressView

    @get:LayoutRes
    abstract val layoutRes: Int

    init {
        arguments = Bundle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(FRAGMENT_REQUEST_RESULT) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported.
            onHandleFragmentResult(requestKey, bundle)
            // Do something with the result.
        }
    }

    /**
     * Shouldn't override this function...Use [.getLayoutRes]
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = super.onCreateView(inflater, container, savedInstanceState)
        if (layoutRes != 0) {
            view = inflater.inflate(layoutRes, container, false)
        }
        return view
    }

    open fun onBack(): Boolean {
        // return true if you custom back
        return false
    }

    protected fun showLoading() {
        val loading = childFragmentManager.findFragmentByTag(LOADING_TAG)
        if (loading != null) return

        LoadingDialogFragment().show(childFragmentManager, LOADING_TAG)
    }

    protected fun hideLoading() {

        if (mHandler == null) mHandler = Handler(Looper.getMainLooper())
        mHandler?.postDelayed(Runnable {
            val loading = childFragmentManager.findFragmentByTag(LOADING_TAG) ?: return@Runnable

            (loading as DialogFragment).dismissAllowingStateLoss()
        }, 500)
    }

    protected fun showDialog(dialog: DialogFragment?) {
        if (dialog == null) return

        dialog.show(childFragmentManager, DIALOG_TAG)
    }

    fun showProgressView() {
        if (progressView?.visibility != View.VISIBLE) {
            progressView?.visibility = View.VISIBLE
        }
    }

    fun hiddenProgressView() {
        if (progressView?.visibility == View.VISIBLE) {
            progressView?.visibility = View.GONE
        }
    }

    override fun onDialogResult(requestCode: Int, action: Int, result: Bundle?) {
        AppLog.d("requestCode: $requestCode ---- action: $action ---  result: $result")
    }

    fun onHandleFragmentResult(requestKey: String, bundle: Bundle) {
        AppLog.d("requestKey: $requestKey ---- bundle: $bundle")
    }

    override fun onDestroy() {
        super.onDestroy()
        mHandler?.removeCallbacksAndMessages(null)
        mHandler = null
    }


    companion object {

        const val DIALOG_TAG = "dialog_tag"
        const val LOADING_TAG = "loading_tag"
        const val FRAGMENT_REQUEST_RESULT = "fragmentRequestKey"
    }
}
