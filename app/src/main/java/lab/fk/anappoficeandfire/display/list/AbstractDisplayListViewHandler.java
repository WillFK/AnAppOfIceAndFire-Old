package lab.fk.anappoficeandfire.display.list;

import android.text.TextUtils;

import com.annimon.stream.function.Function;
import com.annimon.stream.function.Predicate;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lab.fk.anappoficeandfire.display.FilterDisplayVO;
import lab.fk.anappoficeandfire.model.AbstractModel;

public abstract class AbstractDisplayListViewHandler<Model extends AbstractModel> {

    public String getName(Model model) {
        return null;
    };

    public String getDescription(Model model) {
        return null;
    };

    public Date getReleased(Model model) {
        return null;
    }

    public String getTitles(Model model) {
        return null;
    }

    public String getAliases(Model model) {
        return null;
    }

    public String getPlayedBy(Model model) {
        return null;
    }

    public String getWords(Model model) {
        return null;
    }

    public String getRegion(Model model) {
        return null;
    }

    public List<Predicate<Model>> getFilters(FilterDisplayVO filterDisplayVO) {
        List<Predicate<Model>> filters = new ArrayList<>();

        if (filterDisplayVO.name != null) {
            filters.add(model -> !TextUtils.isEmpty(getName(model)) &&
                    getName(model).toLowerCase().contains(filterDisplayVO.name.toLowerCase()));
        }

        if (filterDisplayVO.alias != null) {
            filters.add(model -> !TextUtils.isEmpty(getAliases(model)) &&
                    getAliases(model).toLowerCase().contains(filterDisplayVO.alias.toLowerCase()));
        }

        if (filterDisplayVO.title != null) {
            filters.add(model -> !TextUtils.isEmpty(getTitles(model)) && getTitles(model).toLowerCase().contains(filterDisplayVO.title.toLowerCase()));
        }

        if (filterDisplayVO.release != null) {
            filters.add(model -> getReleased(model) != null && getReleased(model).after(filterDisplayVO.release.getTime()));
        }

        if (filterDisplayVO.words != null) {
            filters.add(model -> !TextUtils.isEmpty(getWords(model)) && getWords(model).toLowerCase().contains(filterDisplayVO.words.toLowerCase()));
        }

        if (filterDisplayVO.region != null) {
            filters.add(model -> !TextUtils.isEmpty(getRegion(model)) && getRegion(model).toLowerCase().contains(filterDisplayVO.region.toLowerCase()));
        }

        return filters;
    }

}
