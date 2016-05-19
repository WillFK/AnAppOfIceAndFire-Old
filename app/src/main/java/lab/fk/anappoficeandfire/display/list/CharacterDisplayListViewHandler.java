package lab.fk.anappoficeandfire.display.list;

import lab.fk.anappoficeandfire.model.Character;

public class CharacterDisplayListViewHandler extends AbstractDisplayListViewHandler<Character> {

    @Override
    public String getName(Character model) {
        return model.name;
    }

    @Override
    public String getDescription(Character model) {
        if (model.aliases == null || model.aliases.isEmpty()) {
            if (model.titles == null || model.titles.isEmpty()) {
                return null;
            }
            return model.titles.split(";")[0];
        }
        return model.aliases.split(";")[0];
    }

    @Override
    public String getAliases(Character model) {
        return model.aliases;
    }

    @Override
    public String getTitles(Character model) {
        return model.titles;
    }

    @Override
    public String getPlayedBy(Character model) {
        return model.playedBy;
    }
}
