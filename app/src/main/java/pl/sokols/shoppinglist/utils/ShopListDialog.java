package pl.sokols.shoppinglist.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import pl.sokols.shoppinglist.R;
import pl.sokols.shoppinglist.data.entities.ShopList;
import pl.sokols.shoppinglist.databinding.ShopListDialogBinding;

public class ShopListDialog extends DialogFragment {

    private final OnItemClickListener listener;
    private final ShopList shopList;
    private ShopListDialogBinding dialogBinding;

    public ShopListDialog(ShopList shopList, OnItemClickListener listener) {
        this.listener = listener;
        this.shopList = shopList == null ? new ShopList("") : shopList;
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dialogBinding = ShopListDialogBinding.inflate(inflater, container, false);
        dialogBinding.setShopList(shopList);
        setComponents();
        return dialogBinding.getRoot();
    }

    private void setComponents() {
        dialogBinding.applyListDialogButton.setOnClickListener(v -> {
            if (dialogBinding.listNameEditText.getText() != null
                    && dialogBinding.listNameEditText.getText().toString().trim().isEmpty()) {
                dialogBinding.listNameTextInputLayout.setError(getString(R.string.incorrect_value));
                dialogBinding.listNameTextInputLayout.setErrorEnabled(true);
            } else {
                dialogBinding.listNameTextInputLayout.setErrorEnabled(false);
            }

            if (!shopList.getName().isEmpty()) {
                listener.onItemClickListener(shopList);
                dismiss();
            }
        });
    }
}
