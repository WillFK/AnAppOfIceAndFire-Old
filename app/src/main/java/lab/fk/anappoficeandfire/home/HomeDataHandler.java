package lab.fk.anappoficeandfire.home;

import android.annotation.SuppressLint;
import android.app.Activity;

import com.orm.SugarRecord;

import java.text.SimpleDateFormat;
import java.util.Date;

import lab.fk.anappoficeandfire.model.Meta;

/**
 * Created by will on 5/17/16.
 */
public class HomeDataHandler {

    private Activity homeActivity;

    private Meta meta;

    public HomeDataHandler(Activity homeActivity) {
        this.homeActivity = homeActivity;
        init();
    }

    private void init() {
        meta = SugarRecord.findById(Meta.class, 1);
    }

    private Date getLastUpdateDate() {
        return meta == null ? null : new Date(meta.lastUpdate);
    }

    @SuppressLint("SimpleDateFormat")
    public String getFormattedLastUpdateDate() {
        String formattedDate;
        Date date = getLastUpdateDate();

        if (date == null) {
            formattedDate = "---";
        } else {
            formattedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(date);
        }

        return formattedDate;
    }
}
