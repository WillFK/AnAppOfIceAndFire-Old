package lab.fk.anappoficeandfire.model;

import com.orm.dsl.Table;

import java.util.List;

@Table
public class House extends AbstractModel {

    public String name;
    public Character currentLord;
    public String words;
    public String region;
   // public List<Character> swornMembers;

    public House() {
    }

    public House(long id, String name, String region, Character currentLord, String words, List<Character> swornMembers) {
        this.id = id;
        this.name = name;
        this.currentLord = currentLord;
        this.words = words;
        this.region = region;
        //this.swornMembers = swornMembers;
    }
}
