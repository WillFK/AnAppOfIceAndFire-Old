package lab.fk.anappoficeandfire.model;

import com.orm.dsl.Table;

/**
 *
 * App's metadata
 *
 * Created by will on 5/17/16.
 */
@Table
public class Meta extends AbstractModel {

    public long id;
    public long lastUpdate;

    public Meta() {
    }

    public Meta(long id, long lastUpdate) {
        this.id = id;
        this.lastUpdate = lastUpdate;
    }
}
