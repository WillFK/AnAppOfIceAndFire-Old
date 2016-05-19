package lab.fk.anappoficeandfire.database;

import android.util.Log;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.orm.SugarRecord;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
            populateWithData(getBookMocks());
            populateWithData(getHouseMocks());
            populateWithData(getCharacterMocks());
            updateMeta();
        } catch (Exception e) {
            Log.e("MOCKS", e.getMessage(), e);
        }
    }

    private static List<Book> getBookMocks() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1996);
        Date d1 = cal.getTime();
        cal.set(Calendar.YEAR, 2000);
        Date d2 = cal.getTime();
        return Arrays.asList(
                new Book(1, "[Mock] A Game of Thrones", 10, d1, null),
                new Book(2, "[Mock] A Clash of Kings", 11, d2, null)
                );
    }

    private static List<House> getHouseMocks() {
        return Arrays.asList(
                new House(1, "[Mock] House Stark of Winterfell", "The North", null, "Winter is Comming", null),
                new House(2, "[Mock] House Lannister of Casterly Rock", "The Westerlands", null, "Hear me Roar", null)
                );
    }

    private static List<Character> getCharacterMocks() {
        return Arrays.asList(
                new Character(1,
                        "[Mock] Jon Snow",
                        Arrays.asList("Lord Commander of the Night's Watch"),
                        Arrays.asList("Lord Snow", "Ned Stark's Bastard"),
                        Arrays.asList("Kit Harington")),
                new Character(2,
                        "[Mock] Eddard Stark",
                        Arrays.asList("Lord of Winterfell", "Regent"),
                        Arrays.asList("The Quiet Wolf", "Ned Stark"),
                        Arrays.asList("Sean Bean")),
                new Character(3,
                        "[Mock] Jaime Lannister",
                        Arrays.asList("Ser", "Commander of the Kingsguard"),
                        Arrays.asList("The Kingslayer", "The Young Lion"),
                        Arrays.asList("Nikolaj Coster-Waldau")),
                new Character(4,
                        "[Mock] Tyrion Lannister",
                        Arrays.asList("Acting Hand of the King", "Master of Coin"),
                        Arrays.asList("The Imp", "Halfman"),
                        Arrays.asList("Peter Dinklage"))
                );
    }

    public static void populateWithData(List<? extends AbstractModel> data) {
        Stream.of(data).forEach(SugarRecord::save);
    }

    public static void updateMeta() {
        Meta meta = SugarRecord.findById(Meta.class, 1);
        if (meta == null) {
            meta = new Meta();
            meta.id = 1;
        }
        meta.lastUpdate = System.currentTimeMillis();
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
