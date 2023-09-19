package appsgenz.template.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import appsgenz.template.R
import appsgenz.template.base.dialog.LoadingDialogFragment
import appsgenz.template.base.dialog.listener.OnActionInDialogListener
import appsgenz.template.databinding.FragmentPlantDetailBinding
import appsgenz.template.screens.gallery.GalleryViewModel
import appsgenz.template.utilities.AppLog

/**
 * @author HungHN on 09/15/2023.
 * This is base App fragment.
 * It contains some default attributes:  Api, menu
 */
@Suppress("unused")
abstract class BaseAppBindingFragment<T : ViewDataBinding> : BaseAppFragment() {


    protected lateinit var binding: T

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mBinding = DataBindingUtil.inflate<T>(
            inflater,
            layoutRes,
            container,
            false
        )
        binding = mBinding
        onBindingView(mBinding)
        return mBinding.root
    }

    abstract fun onBindingView(viewBinding: T)
}
