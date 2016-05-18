package lab.fk.anappoficeandfire.database;

import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.orm.SugarRecord;

import java.util.List;

import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.model.Book;
import lab.fk.anappoficeandfire.model.Character;
import lab.fk.anappoficeandfire.model.House;
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
        SugarRecord.deleteAll(Book.class);
        SugarRecord.deleteAll(House.class);
        SugarRecord.deleteAll(Character.class);
    }

    /**
     * Populate DB with mock objects
     */
    public static void populateWithMocks() {
        try {
            erase();
            updateMeta();
        } catch (Exception e) {
            Log.e("MOCKS", e.getMessage(), e);
        }
    }

    public static void populateWithData(List<? extends AbstractModel> data) {
        Stream.of(data).forEach(SugarRecord::save);
    }

    public static void updateMeta() {
        Meta meta = SugarRecord.findById(Meta.class, 1);
        if (meta == null) {
            meta = new Meta();
            meta.id = 1;
            meta.lastUpdate = System.currentTimeMillis();
        }
        SugarRecord.save(meta);
    }

    public static <Model extends AbstractModel> List<Model> getOrCreateList(Class<Model> clazz, List<Long> ids) throws Exception {
        //List<Model> result = new ArrayList<>();
        return Stream.of(ids).map(id -> {
            try {
                return DBHandler.getOrCreate(clazz, id);
            } catch (Exception e) {
                return null; //TODO handle exception
            }
        }).collect(Collectors.toList());
        //return result;
    }

    public static <Model extends AbstractModel> Model getOrCreate(Class<Model> clazz, Long id) throws Exception {
        if (id == null) {
            return null;
        }
        Model model = SugarRecord.findById(clazz, id);
        if (model == null) {
            model = clazz.newInstance();
            model.id = id;
            SugarRecord.save(model);
        }
        return model;
    }
}
