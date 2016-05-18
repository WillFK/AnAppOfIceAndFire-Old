package lab.fk.anappoficeandfire.client;

public enum EnumRequestDir {

    BOOK("books"),
    CHARACTER("characters"),
    HOUSE("houses");

    private static final String REQUEST_DIR = "http://anapioficeandfire.com/api/%s?page=%d&pageSize=%d";
    private static final String REQUEST_DIR_ID = REQUEST_DIR+"/%d";

    private final String path;

    EnumRequestDir(String path) {
        this.path = path;
    }

    public String getRequestDir(int page, int pageSize) {
        return String.format(REQUEST_DIR, path, page, pageSize);
    }

    public String getRequestDir(Long id) {
        return String.format(REQUEST_DIR_ID, path, id);
    }
}
