package com.example.overtone.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;


    public VerticalSpacingItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight;
        }
    }


//    public class DividerItemDecoration extends RecyclerView.ItemDecoration{
//        private final int[] ATTRS = new int[]{android.R.attr.listDivider};
//        private Drawable divider;
//
//        public DividerItemDecoration(Context context){
//            final TypedArray styleAttributes = context.obtainStyledAttributes(ATTRS);
//            divider = styleAttributes.getDrawable(0);
//            styleAttributes.recycle();
//        }
//        public DividerItemDecoration(Context context, int resId){
//            divider = ContextCompat.getDrawable(context,resId);
//        }
//
//        @Override
//        public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//            int left = parent.getPaddingLeft();
//            int right = parent.getWidth() - parent.getPaddingRight();
//
//            int childCount = parent.getChildCount();
//            for (int i = 0; i < childCount; i++) {
//                View child = parent.getChildAt(i);
//
//                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
//
//                int top = child.getBottom() + params.bottomMargin;
//                int bottom = top + divider.getIntrinsicHeight();
//
//                divider.setBounds(left, top, right, bottom);
//                divider.draw(c);
//            }
//        }
//
//
//
//
//
//
//    }





}
