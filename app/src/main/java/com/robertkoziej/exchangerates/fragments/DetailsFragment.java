package com.robertkoziej.exchangerates.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.robertkoziej.exchangerates.R;
import com.robertkoziej.exchangerates.fragments.views.DetailsFragmentView;
import com.robertkoziej.exchangerates.list.RateItem;
import com.robertkoziej.exchangerates.presenters.DetailsFragmentPresenter;
import com.robertkoziej.exchangerates.tools.ErrorDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment implements DetailsFragmentView {

    public static final String TAG = DetailsFragment.class.getSimpleName();

    private DetailsFragmentPresenter presenter;

    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.codeValue)
    TextView codeValue;

    private AlertDialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailsFragmentPresenter(getActivity(), this);
        presenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void setView(final RateItem item) {
        date.setText(item.getDate());
        String desc = item.getCode() + ": " + Double.toString(item.getValue());
        codeValue.setText(desc);
        showView();
    }

    private void showView() {
        codeValue.setVisibility(View.VISIBLE);
        date.setVisibility(View.VISIBLE);
    }


    @Override
    public void showErrorDialog(int textId) {
        alertDialog = ErrorDialog.getErrorDialog(getActivity(), textId);
        alertDialog.show();
    }

    @Override
    public void dismissErrorDialog() {
        alertDialog.dismiss();
    }
}
