package lab.fk.anappoficeandfire.display.viewhandler;

public class HouseFilterViewHandler extends AbstractFilterViewHandler{

    @Override
    public boolean useViewName() {
        return true;
    }

    @Override
    public boolean useViewRegion() {
        return true;
    }

    @Override
    public boolean useViewWords() {
        return true;
    }
}
