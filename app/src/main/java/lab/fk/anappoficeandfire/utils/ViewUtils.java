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

}
