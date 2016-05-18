package lab.fk.anappoficeandfire.client.RawHouse;

import java.util.List;

import lab.fk.anappoficeandfire.model.Character;

public class RawCharacter extends RawModel{

    public String url;
    public String name;
    public List<String> titles;
    public List<String> aliases;
    public List<String> playedBy;

    @Override
    public Object toModel() throws Exception {
        return new Character(
                RawModel.extractIdFromURL(url),
                name,
                titles,
                aliases,
                playedBy
        );
    }

    @Override
    public String toString() {
        return String.format("{%s, %s, %s}", url, name, titles.toString());
    }
}
