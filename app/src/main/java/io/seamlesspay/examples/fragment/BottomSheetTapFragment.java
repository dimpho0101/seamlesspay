package io.seamlesspay.examples.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import io.seamlesspay.R;
import io.seamlesspay.databinding.FragmentBottomSheetTapBinding;
import za.co.seamlesspay.v1.feature.ConfigureViewModel;
import za.co.seamlesspay.v1.feature.NoUi.SeamlessNoUI;
import za.co.seamlesspay.v1.interfaces.SeamlessObserver;
import za.co.seamlesspay.v1.util.EmvUtil.model.EmvCard;
import za.co.seamlesspay.v1.viewmodel.SeamlessViewModel;

import static java.util.Objects.requireNonNull;

public class BottomSheetTapFragment extends Fragment {

  private FragmentBottomSheetTapBinding mBinding;

  private SeamlessViewModel mSeamlessViewModel;

  private SeamlessNoUI mTap;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet_tap, container, false);
    return mBinding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mBinding.button.setOnClickListener(aView -> ((BottomSheetTapActivity2) requireNonNull(getActivity())).startReading());
  }

  @Override
  public void onStart() {
    super.onStart();
    mSeamlessViewModel = new ConfigureViewModel(getContext()).createViewModel();
    mTap = new SeamlessNoUI(getContext());
    configureOb();
  }

  private void configureOb() {
    final Observer<Intent> intentObserver = aIntent -> mTap.startReading(new SeamlessObserver.ResourceStatus() {
      @Override
      public void onSuccess(EmvCard aEmvCard) {
        mBinding.text.setText(aEmvCard.getCardNumber());
      }

      @Override
      public void onError(Throwable aThrowable) {
        mBinding.text.setText(aThrowable.getMessage());
      }
    }, aIntent);
    mSeamlessViewModel.getIntentMutableLiveData().observe((LifecycleOwner) requireNonNull(getContext()), intentObserver);
  }

  @Override
  public void onPause() {
    super.onPause();
    mTap.stopReading();
  }
}
