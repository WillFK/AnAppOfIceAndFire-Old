package lab.fk.anappoficeandfire.model;

import com.orm.dsl.Table;

import java.util.List;

@Table
public class House extends AbstractModel {

    public Character currentLord;
    public String words;
    public List<Character> swornMembers;

    public House() {
    }

    public House(long id, Character currentLord, String words, List<Character> swornMembers) {
        this.id = id;
        this.currentLord = currentLord;
        this.words = words;
        this.swornMembers = swornMembers;
    }
}
