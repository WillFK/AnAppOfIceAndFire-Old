package lab.fk.anappoficeandfire.client.RawHouse;

import android.text.TextUtils;

/**
 *
 * Base raw model
 *
 * Created by will on 5/18/16.
 */
public abstract class RawModel<Model> {

    public abstract Model toModel() throws Exception;

    public static Long extractIdFromURL(String url) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        String[] splitted = url.split("/");
        return Long.parseLong(splitted[splitted.length-1]);
    }
}
