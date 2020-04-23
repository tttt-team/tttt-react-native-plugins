package org.tttt.rn.rong.im;

import android.content.Context;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

public class RongImConversationListFragmentLayout extends RelativeLayout {

    public RongImConversationListFragmentLayout(@NonNull Context context) {
        super(context);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        post(measureAndLayout);
    }

    private final Runnable measureAndLayout = new Runnable() {
        @Override
        public void run() {
            measure(
                    MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
            layout(getLeft(), getTop(), getRight(), getBottom());
        }
    };

}
