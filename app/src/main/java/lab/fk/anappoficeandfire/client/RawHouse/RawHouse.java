package lab.fk.anappoficeandfire.client.RawHouse;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import lab.fk.anappoficeandfire.database.DBHandler;
import lab.fk.anappoficeandfire.model.Character;
import lab.fk.anappoficeandfire.model.House;

/**
 *
 * Raw representation of House
 *
 * Created by will on 5/18/16.
 */
@SuppressWarnings("ConstantConditions")
public class RawHouse extends RawModel<House>{

    public String url;
    public String name;
    public String currentLord;
    public String words;
    public List<String> swornMembers = new ArrayList<>();

    @Override
    public House toModel() throws Exception{
        return new House(
                RawModel.extractIdFromURL(url),
                DBHandler.getOrCreate(Character.class, RawModel.extractIdFromURL(currentLord)),
                words,
                DBHandler.getOrCreateList(Character.class,
                        Stream.of(swornMembers).map(RawModel::extractIdFromURL).collect(Collectors.toList())));
    }

    @Override
    public String toString() {
        return String.format("{%s, %s}", url, swornMembers == null ? "<null>":swornMembers.toString());
    }
}
