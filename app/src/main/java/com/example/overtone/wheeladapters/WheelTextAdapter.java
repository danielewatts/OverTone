package com.example.overtone.wheeladapters;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.example.overtone.R;
import com.example.overtone.data.MenuItemData;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class WheelTextAdapter extends CursorWheelLayout.CycleWheelAdapter {
    private Context mContext;
    private List<MenuItemData> menuItems;
    private LayoutInflater inflater;
    private int gravity;


    public WheelTextAdapter(Context mContext, List<MenuItemData> menuItems) {
        this.mContext = mContext;
        this.menuItems = menuItems;
        gravity = Gravity.CENTER;
        inflater = LayoutInflater.from(mContext);
    }

    public WheelTextAdapter(Context mContext, List<MenuItemData> menuItems, int gravity) {
        this.mContext = mContext;
        this.menuItems = menuItems;
        this.gravity = gravity;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public View getView(View parent, int position) {

        MenuItemData itemData = getItem(position);
        View root = inflater.inflate(R.layout.wheel_text,null,false);
        TextView textView = root.findViewById(R.id.wheel_menu_item_tv);
        textView.setVisibility(View.VISIBLE);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        textView.setText(itemData.mTitle);
        if(textView.getLayoutParams() instanceof FrameLayout.LayoutParams){
            ((FrameLayout.LayoutParams)textView.getLayoutParams()).gravity = gravity;
        }
        if(position==0){
            textView.setTextColor(ActivityCompat.getColor(mContext,R.color.coolBlue));
        }
        return root;
    }

    @Override
    public MenuItemData getItem(int position) {
        return menuItems.get(position);
    }





}
