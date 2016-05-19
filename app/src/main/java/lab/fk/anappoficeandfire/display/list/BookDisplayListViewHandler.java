package lab.fk.anappoficeandfire.display.list;

import java.util.Date;

import lab.fk.anappoficeandfire.model.Book;

public class BookDisplayListViewHandler extends AbstractDisplayListViewHandler<Book> {

    @Override
    public String getName(Book model) {
        return model.name;
    }

    @Override
    public Date getReleased(Book model) {
        return model.released;
    }
}
