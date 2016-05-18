package lab.fk.anappoficeandfire.client.RawHouse;

import android.text.TextUtils;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lab.fk.anappoficeandfire.database.DBHandler;
import lab.fk.anappoficeandfire.model.Book;
import lab.fk.anappoficeandfire.model.Character;

public class RawBook extends RawModel<Book>{

    public String url;
    public String name;
    public Integer pages;
    public String released;
    public List<String> characters;

    @Override
    public Book toModel() throws Exception {
        return new Book(
                RawModel.extractIdFromURL(url),
                name,
                pages,
                parseDate(released),
                DBHandler.getOrCreateList(Character.class,
                        Stream.of(characters)
                                .map(RawModel::extractIdFromURL)
                                .collect(Collectors.toList()))
        );
    }

    private static Date parseDate(String date) throws ParseException {
        return TextUtils.isEmpty(date) ? null : new SimpleDateFormat("yyyy-MM-dd").parse(date.split("T")[0]);
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s}", url, name, characters == null ? "<null>":characters.toString());
    }
}
