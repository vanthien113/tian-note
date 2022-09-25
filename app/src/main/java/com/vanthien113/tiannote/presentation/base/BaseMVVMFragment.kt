package com.vanthien113.tiannote.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import com.vanthien113.tiannote.helper.CloudLogHelper

/**
 * The type Base mvvm fragment.
 *
 * @param <V>  the type parameter of store the binding
 * @param <VM> the type parameter of store the view model
</VM></V> */
abstract class BaseMVVMFragment<V : ViewBinding, VM : BaseViewModel> : BaseFragment() {
    /**
     * Inflate the content view
     *
     * @return binding - call view on screen
     */
    private lateinit var viewBinding: V

    protected fun getViewBinding(): V {
        return viewBinding
    }

    /**
     * Initialize View Model
     *
     * @return viewModel - Class view model
     */
    private lateinit var viewModel: VM

    protected fun getViewModel(): VM {
        return viewModel
    }

    /**
     * Gets view model provider owner.
     *
     * @return the view model provider owner
     */
    protected open fun getViewModelProviderOwner(): ViewModelStoreOwner {
        return this
    }

    /**
     * Gets view model class.
     *
     * @return the view model class
     */
    protected abstract fun getViewModelClass(): Class<VM>

    /**
     * Gets layout binding.
     *
     * @return the layout binding
     */
    protected abstract fun getLayoutBinding(): V

    /**
     * Initialize.
     */
    protected abstract fun initialize()

    /**
     * Register view event.
     */
    protected abstract fun registerViewEvent()

    /**
     * Register view model obs.
     */
    protected abstract fun registerViewModelObs()

    /**
     * Add support for inflating the <fragment> tag.
    </fragment> */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(getViewModelProviderOwner())[getViewModelClass()]
        viewModel.isPopBackStack = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (!::viewBinding.isInitialized) {
            viewBinding = getLayoutBinding()
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerViewModelObs()
        registerBaseViewModelObs()
        if (!viewModel.isPopBackStack) {
            registerViewEvent()
            initialize()
        }
        viewModel.isPopBackStack = true
    }

    /**
     * Register base view model obs
     */
    private fun registerBaseViewModelObs() {
        viewModel.errorMessageObs.observe(viewLifecycleOwner, this::showToast)
        viewModel.loadingObs.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                (activity as BaseActivity?)?.showLoading()
            } else {
                (activity as BaseActivity?)?.hideLoading()
            }
        }
    }

    protected fun <T> defaultObserve(
        liveData: LiveData<ProcessState<T>>,
        doOnError: ((throwable: Throwable?) -> Unit)? = null,
        doOnFinish: (() -> Unit)? = null,
        doOnSuccess: (T?) -> Unit
    ) {
        liveData.observe(viewLifecycleOwner) { state ->
            if (state.isLoading) {
                changeLoadingState(true)
            }
            if (state.isError) {
                changeLoadingState(false)
                doOnError?.invoke(state.throwable) ?: handleError(state.throwable)
                doOnFinish?.invoke()
                CloudLogHelper.getInstance().logError(state.throwable)
            }
            if (state.isSuccess) {
                changeLoadingState(false)
                doOnSuccess(state.data)
                doOnFinish?.invoke()
            }
        }
    }

    open fun handleError(throwable: Throwable?) {
        throwable?.let { showToast(it.message) }
    }

    protected fun changeLoadingState(isLoading: Boolean) {
        if (isLoading) {
            (activity as BaseActivity?)?.showLoading()
        } else {
            (activity as BaseActivity?)?.hideLoading()
        }
    }
}