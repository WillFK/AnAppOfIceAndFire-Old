package lab.fk.anappoficeandfire.model;

import com.orm.SugarApp;
import com.orm.SugarRecord;
import com.orm.dsl.Table;

import java.util.List;

@Table
public class Character extends AbstractModel {

    public String name;
    public List<String> titles;
    public List<String> aliases;
    public List<String> playedBy;

    public Character() {
    }

    public Character(Long id, String name, List<String> titles, List<String> aliases, List<String> playedBy) {
        this.id = id;
        this.name = name;
        this.titles = titles;
        this.aliases = aliases;
        this.playedBy = playedBy;
    }

    public List<Book> getBooks() {
        return SugarRecord.find(Book.class, " ? in characters", String.valueOf(id));
    }

}
