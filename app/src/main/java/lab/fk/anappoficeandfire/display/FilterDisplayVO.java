package lab.fk.anappoficeandfire.display;

import java.io.Serializable;
import java.util.Calendar;

import lab.fk.anappoficeandfire.datatype.EnumDataType;

public class FilterDisplayVO implements Serializable {

    public EnumDataType dataType;
    public String name;
    public Calendar release;
    public String title;
    public String alias;
    public String words;
    public String region;

}
