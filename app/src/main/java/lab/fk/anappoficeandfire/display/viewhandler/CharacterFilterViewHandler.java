package lab.fk.anappoficeandfire.display.viewhandler;

public class CharacterFilterViewHandler extends AbstractFilterViewHandler{

    @Override
    public boolean useViewName() {
        return true;
    }

    @Override
    public boolean useViewAlias() {
        return true;
    }

    @Override
    public boolean useViewTitles() {
        return true;
    }
}
