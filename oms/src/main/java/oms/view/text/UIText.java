package oms.view.text;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import oms.ability.R;
public class UIText extends AppCompatTextView {
    public UIText(@NonNull Context context) {
        this(context,null);
    }
    public UIText(@NonNull Context context, AttributeSet attributeSet) {
        super(context,attributeSet);
        TypedArray array = context.obtainStyledAttributes(attributeSet,R.styleable.UIText);
        String string = array.getString(R.styleable.UIText_textTypeface);
        if(string != null){
            if(string.trim().length()!=0){
                setTypeface(UITypeface.getTypefaceFromAssets(getContext(),string));
            }
        }
    }
}
