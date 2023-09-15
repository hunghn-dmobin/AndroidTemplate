package appsgenz.template.base.dialog

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import appsgenz.template.R
import com.app.base.dialog.toolbox.NotifyUtil

/**
 * @author HungHN on 09/15/2023.
 */
@Suppress("unused")
class AppDialogFragment : BaseDialogFragment() {

    private var mBuilder: Builder? = null

    private var mMessageView: TextView? = null
    private var mNegativeView: TextView? = null
    private var mPositiveView: TextView? = null
    private var mTitleView: TextView? = null

    private val mOnClickListener = View.OnClickListener { v ->
        when (v.id) {
            R.id.btnCancel -> NotifyUtil.notifyAction(true, this@AppDialogFragment, mRequestCode, ACTION_CLICK_NEGATIVE, null)

            R.id.btnOk -> NotifyUtil.notifyAction(true, this@AppDialogFragment, mRequestCode, ACTION_CLICK_POSITIVE, null)
        }
    }

    class Builder() : Parcelable {
        internal var requestCode: Int = 0
        internal var positiveText: String? = null
        internal var negativeText: String? = null
        internal var message: String? = null
        internal var title: String? = null
        internal var isCancelable: Boolean = false

        constructor(parcel: Parcel) : this() {
            requestCode = parcel.readInt()
            positiveText = parcel.readString()
            negativeText = parcel.readString()
            message = parcel.readString()
            title = parcel.readString()
            isCancelable = parcel.readByte() != 0.toByte()
        }

        fun setRequestCode(requestCode: Int): Builder {
            this.requestCode = requestCode
            return this
        }

        /**
         * The positive button is used in case you want to create a dialog with one button.
         *
         * @param positiveText text of positive button
         */
        fun setPositiveText(positiveText: String): Builder {
            this.positiveText = positiveText
            return this
        }

        fun setNegativeText(negativeText: String): Builder {
            this.negativeText = negativeText
            return this
        }

        fun setCancelable(cancelable: Boolean): Builder {
            this.isCancelable = cancelable
            return this
        }

        fun setMessage(message: String): Builder {
            this.message = message
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): AppDialogFragment {
            return newInstance(this)
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(requestCode)
            parcel.writeString(positiveText)
            parcel.writeString(negativeText)
            parcel.writeString(message)
            parcel.writeString(title)
            parcel.writeByte(if (isCancelable) 1 else 0)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Builder> {
            override fun createFromParcel(parcel: Parcel): Builder {
                return Builder(parcel)
            }

            override fun newArray(size: Int): Array<Builder?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null)
            getDataFrom(savedInstanceState)
        else {
            val bundle = arguments
            if (bundle != null)
                getDataFrom(bundle)
        }
    }

    override fun getDataFrom(bundle: Bundle) {
        super.getDataFrom(bundle)
        mBuilder = bundle.getParcelable(EXTRA_BUILDER)
    }

    override fun onStart() {
        super.onStart()
        val window = dialog?.window ?: return

        window.setBackgroundDrawableResource(android.R.color.transparent)

        //set window size programmatically
        val displayMetrics = resources.displayMetrics
        val params = window.attributes

        val marginRL = resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        params.width = displayMetrics.widthPixels - 2 * marginRL
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        window.setLayout(params.width, params.height)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val window = dialog?.window
        window?.requestFeature(Window.FEATURE_NO_TITLE)

        val layoutRes: Int = if (TextUtils.isEmpty(mBuilder?.positiveText) || TextUtils.isEmpty(mBuilder?.negativeText))
            R.layout.dialog_ok
        else
            R.layout.dialog_ok_cancel

        return inflater.inflate(layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mMessageView = view.findViewById(R.id.content)
        mNegativeView = view.findViewById(R.id.btnCancel)
        mPositiveView = view.findViewById(R.id.btnOk)
        mTitleView = view.findViewById(R.id.title)

        displayText(mMessageView, mBuilder?.message)
        displayText(mNegativeView, mBuilder?.negativeText)
        displayText(mPositiveView, mBuilder?.positiveText)
        displayText(mTitleView, mBuilder?.title)

        settingOnClick(mNegativeView)
        settingOnClick(mPositiveView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isCancelable = mBuilder?.isCancelable ?: false
    }

    private fun displayText(view: TextView?, text: String?) {
        if (view == null || TextUtils.isEmpty(text)) return
        view.text = text
    }

    private fun settingOnClick(view: View?) {
        if (view == null) return
        view.setOnClickListener(mOnClickListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(EXTRA_BUILDER, mBuilder)
    }

    companion object {

        private const val EXTRA_BUILDER = "extra_builder"

        const val ACTION_CLICK_NEGATIVE = 1001
        const val ACTION_CLICK_POSITIVE = 1002

        private fun newInstance(builder: Builder): AppDialogFragment {
            val fragmentV4 = AppDialogFragment()
            val bundle = makeBundle(builder.requestCode)
            bundle.putParcelable(EXTRA_BUILDER, builder)
            fragmentV4.arguments = bundle
            return fragmentV4
        }
    }
}
