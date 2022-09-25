package com.vanthien113.tiannote.presentation.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.vanthien113.tiannote.R;

/**
 * The type Base mvvm activity.
 *
 * @param <V>  the type parameter of store the binding
 * @param <VM> the type parameter of store the view model
 */
public abstract class BaseMVVMActivity<V extends ViewBinding, VM extends BaseViewModel> extends BaseActivity {
    private V binding;
    private VM viewModel;

    protected VM getViewModel() {
        return viewModel;
    }

    protected abstract Class<VM> getViewModelClass();

    protected abstract V getLayoutBinding();

    protected abstract void initialize();

    protected abstract void registerViewEvent();

    protected abstract void registerViewModelObs();

    protected V getViewBinding() {
        return binding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getLayoutBinding();
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(getViewModelClass());
        registerViewModelObs();
        registerBaseViewModelObs();
        registerViewEvent();
        initialize();
        changeStatusBarColor(R.color.colorGrayMineShaft);
    }

    private void registerBaseViewModelObs() {
        viewModel.getErrorMessageObs().observe(this, msg -> showToast(msg));

        viewModel.getLoadingObs().observe(this, isLoading -> {
            if (isLoading) {
                showLoading();
            } else {
                hideLoading();
            }
        });
    }
}
