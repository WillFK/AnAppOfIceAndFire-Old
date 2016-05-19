package lab.fk.anappoficeandfire.utils;

import android.app.Activity;
import android.view.View;

/**
 *
 * Class with utilities for the handling of views
 *
 * Created by will on 5/17/16.
 */
@SuppressWarnings("unchecked")
public class ViewUtils {

    public static <T extends View> T getViewById(Activity root, Integer id) {
        return (T) root.findViewById(id);
    }

    public static <T extends View> T getViewByTag(Activity root, Integer idContainer, Object tag) {
        View view = root.findViewById(idContainer);
        return getViewByTag(view, tag);
    }

    public static <T extends View> T getViewByTag(View view, Object tag) {
        return (T) view.findViewWithTag(tag);
    }

}
