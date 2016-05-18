package lab.fk.anappoficeandfire.database;

import com.orm.SugarApp;
import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;

import java.util.List;

import lab.fk.anappoficeandfire.model.Meta;

/**
 *
 * Data Base Operations
 *
 * Created by will on 5/17/16.
 */
public class DBHandler {

    /**
     * Erase whole DB
     */
    public static void erase() {
        SugarRecord.deleteAll(Meta.class);
    }

    /**
     * Populate DB with mock objects
     */
    public static void populateWithMocks() {
        erase();
        SugarRecord.save(new Meta(1, System.currentTimeMillis()));
    }

    public static void populateWithData(List<Object> data) {
        //todo
    }
}
