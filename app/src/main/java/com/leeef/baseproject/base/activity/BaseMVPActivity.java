package com.leeef.baseproject.base.activity;

/**
 *
 */

public abstract class BaseMVPActivity<T extends BaseContract.BasePresenter> extends BaseActivity {

    protected T mPresenter;

    protected abstract T bindPresenter();

    @Override
    protected void initPresenter() {
        attachView(bindPresenter());
    }

    private void attachView(T presenter) {
        mPresenter = presenter;
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}