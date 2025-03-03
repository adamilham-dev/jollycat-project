// Generated by view binder compiler. Do not edit!
package com.example.jollycat.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.jollycat.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentProfileBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton btnAboutUs;

  @NonNull
  public final MaterialButton btnLogOut;

  @NonNull
  public final CardView layoutOne;

  @NonNull
  public final LinearLayout layoutProfile;

  @NonNull
  public final CardView layoutToolbar;

  @NonNull
  public final CardView layoutTwo;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView tvProfileName;

  @NonNull
  public final TextView tvProfilePhoneNumber;

  private FragmentProfileBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton btnAboutUs, @NonNull MaterialButton btnLogOut,
      @NonNull CardView layoutOne, @NonNull LinearLayout layoutProfile,
      @NonNull CardView layoutToolbar, @NonNull CardView layoutTwo, @NonNull Toolbar toolbar,
      @NonNull TextView tvProfileName, @NonNull TextView tvProfilePhoneNumber) {
    this.rootView = rootView;
    this.btnAboutUs = btnAboutUs;
    this.btnLogOut = btnLogOut;
    this.layoutOne = layoutOne;
    this.layoutProfile = layoutProfile;
    this.layoutToolbar = layoutToolbar;
    this.layoutTwo = layoutTwo;
    this.toolbar = toolbar;
    this.tvProfileName = tvProfileName;
    this.tvProfilePhoneNumber = tvProfilePhoneNumber;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_about_us;
      MaterialButton btnAboutUs = ViewBindings.findChildViewById(rootView, id);
      if (btnAboutUs == null) {
        break missingId;
      }

      id = R.id.btnLogOut;
      MaterialButton btnLogOut = ViewBindings.findChildViewById(rootView, id);
      if (btnLogOut == null) {
        break missingId;
      }

      id = R.id.layout_one;
      CardView layoutOne = ViewBindings.findChildViewById(rootView, id);
      if (layoutOne == null) {
        break missingId;
      }

      id = R.id.layout_profile;
      LinearLayout layoutProfile = ViewBindings.findChildViewById(rootView, id);
      if (layoutProfile == null) {
        break missingId;
      }

      id = R.id.layout_toolbar;
      CardView layoutToolbar = ViewBindings.findChildViewById(rootView, id);
      if (layoutToolbar == null) {
        break missingId;
      }

      id = R.id.layout_two;
      CardView layoutTwo = ViewBindings.findChildViewById(rootView, id);
      if (layoutTwo == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tvProfileName;
      TextView tvProfileName = ViewBindings.findChildViewById(rootView, id);
      if (tvProfileName == null) {
        break missingId;
      }

      id = R.id.tvProfilePhoneNumber;
      TextView tvProfilePhoneNumber = ViewBindings.findChildViewById(rootView, id);
      if (tvProfilePhoneNumber == null) {
        break missingId;
      }

      return new FragmentProfileBinding((ConstraintLayout) rootView, btnAboutUs, btnLogOut,
          layoutOne, layoutProfile, layoutToolbar, layoutTwo, toolbar, tvProfileName,
          tvProfilePhoneNumber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
