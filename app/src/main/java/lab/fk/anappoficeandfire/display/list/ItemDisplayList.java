package lab.fk.anappoficeandfire.display.list;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lab.fk.anappoficeandfire.R;
import lab.fk.anappoficeandfire.datatype.EnumDataType;
import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.utils.ViewUtils;

@SuppressWarnings("unchecked")
public class ItemDisplayList extends LinearLayout {

    private EnumDataType dataType;

    public ItemDisplayList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public ItemDisplayList(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ItemDisplayList(Context context) {
        super(context);
        initView();
    }

    private void evaluateSimple(String tag, String value) {
        TextView textView = ViewUtils.getViewByTag(this, tag);
        textView.setText(value);
    }

    private void evaluateGroup(String tag, String value) {
        View group = findViewWithTag(tag);
        if (TextUtils.isEmpty(value)) {
            group.setVisibility(GONE);
        } else {
            group.setVisibility(VISIBLE);
            TextView textView = ViewUtils.getViewByTag(group, "controller");
            textView.setText(value);
        }

    }

    public String formatDate(Date date) {
        return date == null ? null : new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public void evaluate(AbstractModel model) {
        evaluateSimple("name", getDisplayListViewHandler().getName(model));
        evaluateSimple("description", getDisplayListViewHandler().getDescription(model));
        evaluateGroup("aliases", getDisplayListViewHandler().getAliases(model));
        evaluateGroup("titles", getDisplayListViewHandler().getTitles(model));
        evaluateGroup("released",
                formatDate(getDisplayListViewHandler().getReleased(model)));
        evaluateGroup("region", getDisplayListViewHandler().getRegion(model));
        evaluateGroup("words", getDisplayListViewHandler().getWords(model));
        evaluateGroup("playedBy", getDisplayListViewHandler().getPlayedBy(model));

    }

    private void initView() {
        View view = inflate(getContext(), R.layout.layout_item_display_list, null);
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        addView(view);
    }

    public EnumDataType getDataType() {
        return dataType;
    }

    private AbstractDisplayListViewHandler getDisplayListViewHandler() {
        return getDataType().getDisplayListViewHandler();
    }

    public void setDataType(EnumDataType dataType) {
        this.dataType = dataType;
    }
}