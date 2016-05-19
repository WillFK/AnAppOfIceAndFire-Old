package lab.fk.anappoficeandfire.display.viewhandler;

import android.view.View;

/**
 *
 * Abstract Filter view's Handler
 *
 * Constrols visuals aspects of visual elements
 * each display setup must keep its own implementation
 *
 * Created by will on 5/18/16.
 */
public class AbstractFilterViewHandler {

    public boolean useViewName() {
        return false;
    }

    public boolean useViewReleased() {
        return false;
    }

    public boolean useViewTitles() {
        return false;
    }

    public boolean useViewAlias() {
        return false;
    }

    public boolean useViewWords() {
        return false;
    }

    public boolean useViewRegion() {
        return false;
    }

}
