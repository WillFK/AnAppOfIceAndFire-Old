package lab.fk.anappoficeandfire.model;

import com.orm.SugarApp;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

import lab.fk.anappoficeandfire.utils.ArrayUtils;

@Table
public class Character extends AbstractModel {

    public String name;
    public String titles;
    public String aliases;
    public String playedBy;

    public Character() {
    }

    public Character(Long id, String name, List<String> titles, List<String> aliases, List<String> playedBy) {
        this.id = id;
        this.name = name;
        this.titles = ArrayUtils.listToString(titles);
        this.aliases = ArrayUtils.listToString(aliases);
        this.playedBy = ArrayUtils.listToString(playedBy);
    }

    /*public List<Book> getBooks() {
        return SugarRecord.find(Book.class, " ? in characters", String.valueOf(id));
    }*/

}
