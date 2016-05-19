package lab.fk.anappoficeandfire.datatype;

import lab.fk.anappoficeandfire.display.list.AbstractDisplayListViewHandler;
import lab.fk.anappoficeandfire.display.list.BookDisplayListViewHandler;
import lab.fk.anappoficeandfire.display.list.CharacterDisplayListViewHandler;
import lab.fk.anappoficeandfire.display.list.HouseDisplayListViewHandler;
import lab.fk.anappoficeandfire.display.viewhandler.AbstractFilterViewHandler;
import lab.fk.anappoficeandfire.display.viewhandler.BookFilterViewHandler;
import lab.fk.anappoficeandfire.display.viewhandler.CharacterFilterViewHandler;
import lab.fk.anappoficeandfire.display.viewhandler.HouseFilterViewHandler;
import lab.fk.anappoficeandfire.model.AbstractModel;
import lab.fk.anappoficeandfire.model.Book;
import lab.fk.anappoficeandfire.model.Character;
import lab.fk.anappoficeandfire.model.House;

public enum EnumDataType {

    BOOK("Books", "books", Book.class, new BookFilterViewHandler(), new BookDisplayListViewHandler()),
    CHARACTER("Characters", "characters", Character.class, new CharacterFilterViewHandler(), new CharacterDisplayListViewHandler()),
    HOUSE("Houses", "houses", House.class, new HouseFilterViewHandler(), new HouseDisplayListViewHandler());

    private static final String REQUEST_PATH = "http://anapioficeandfire.com/api/%s?page=%d&pageSize=%d";
    private static final String REQUEST_PATH_ID = "http://anapioficeandfire.com/api/%s/%d";

    private final String displayName;
    private final String method;
    private final Class<? extends AbstractModel> modelClass;
    private final AbstractFilterViewHandler viewHandler;
    private final AbstractDisplayListViewHandler displayListViewHandler;

    EnumDataType(String displayName, String path, Class<? extends AbstractModel> modelClass, AbstractFilterViewHandler viewHandler,
                 AbstractDisplayListViewHandler displayListViewHandler) {
        this.displayName  = displayName;
        this.method = path;
        this.modelClass = modelClass;
        this.viewHandler = viewHandler;
        this.displayListViewHandler = displayListViewHandler;
    }

    public String getRequestPath(int page, int pageSize) {
        return String.format(REQUEST_PATH, method, page, pageSize);
    }

    public String getRequestPath(Long id) {
        return String.format(REQUEST_PATH_ID, method, id);
    }

    public AbstractFilterViewHandler getViewHandler() {
        return viewHandler;
    }

    public AbstractDisplayListViewHandler getDisplayListViewHandler() {
        return displayListViewHandler;
    }

    public Class<? extends AbstractModel> getModelClass() {
        return modelClass;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
