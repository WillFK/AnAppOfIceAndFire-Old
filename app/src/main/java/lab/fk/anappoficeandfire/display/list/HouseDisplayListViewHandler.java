package lab.fk.anappoficeandfire.display.list;

import lab.fk.anappoficeandfire.model.House;

public class HouseDisplayListViewHandler extends AbstractDisplayListViewHandler<House> {

    @Override
    public String getName(House model) {
        return model.name;
    }

    @Override
    public String getWords(House model) {
        return model.words;
    }

    @Override
    public String getRegion(House model) {
        return model.region;
    }
}
