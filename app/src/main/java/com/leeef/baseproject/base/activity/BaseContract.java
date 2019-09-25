package com.leeef.baseproject.base.activity;

/**
 *
 */

public interface BaseContract {

    interface BasePresenter<T> {

        void attachView(T view);

        void detachView();
    }

    interface BaseView {

        void showError(String msg);

        void showLoading();

        void hideLoading();
    }
}
