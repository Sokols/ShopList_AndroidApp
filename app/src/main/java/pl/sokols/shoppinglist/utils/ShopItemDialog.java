package pl.sokols.shoppinglist.utils;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import pl.sokols.shoppinglist.R;
import pl.sokols.shoppinglist.data.entities.ShopItem;
import pl.sokols.shoppinglist.databinding.ShopItemDialogBinding;

public class ShopItemDialog extends DialogFragment {

    private final ShopItem shopItem;
    private final OnItemClickListener listener;
    private ShopItemDialogBinding dialogBinding;

    public ShopItemDialog(int shopListId, OnItemClickListener listener) {
        this.shopItem = new ShopItem("", 0, shopListId, false);
        this.listener = listener;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        dialogBinding = ShopItemDialogBinding.inflate(inflater, container, false);
        dialogBinding.setShopItem(shopItem);
        setComponents();
        return dialogBinding.getRoot();
    }

    private void setComponents() {
        dialogBinding.applyDialogButton.setOnClickListener(v -> {
            System.out.println(shopItem);
            if (dialogBinding.itemNameEditText.getText().toString().isEmpty()) {
                dialogBinding.itemNameTextInputLayout.setError(getString(R.string.cannot_be_empty));
                dialogBinding.itemNameTextInputLayout.setErrorEnabled(true);
            } else {
                dialogBinding.itemNameTextInputLayout.setErrorEnabled(false);
            }
            if (dialogBinding.itemAmountEditText.getText().toString().isEmpty()) {
                dialogBinding.itemAmountTextInputLayout.setError(getString(R.string.cannot_be_empty));
                dialogBinding.itemAmountTextInputLayout.setErrorEnabled(true);
            } else {
                dialogBinding.itemAmountTextInputLayout.setErrorEnabled(false);
            }

            if (shopItem.getAmount() != null && shopItem.getName() != null
                    && shopItem.getAmount() != 0 && !shopItem.getName().isEmpty()) {
                listener.onItemClickListener(shopItem);
                Objects.requireNonNull(this.getDialog()).hide();
            }
        });
    }
}
