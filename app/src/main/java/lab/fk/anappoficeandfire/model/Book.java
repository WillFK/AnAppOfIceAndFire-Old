package lab.fk.anappoficeandfire.model;

import com.orm.dsl.Table;

import java.util.Date;
import java.util.List;

@Table
public class Book extends AbstractModel{

    public String name;
    public Integer pages;
    public Date released;
    //public List<Character> characters;

    public Book() {
    }

    public Book(long id, String name, Integer pages, Date released, List<Character> characters) {
        this.id = id;
        this.name = name;
        this.pages = pages;
        this.released = released;
        //this.characters = characters;
    }
}
