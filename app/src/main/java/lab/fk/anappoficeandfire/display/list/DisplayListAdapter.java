package lab.fk.anappoficeandfire.display.list;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import lab.fk.anappoficeandfire.datatype.EnumDataType;
import lab.fk.anappoficeandfire.model.AbstractModel;

public class DisplayListAdapter extends RecyclerView.Adapter<DisplayListAdapter.ViewHolder> {

    private List<? extends AbstractModel> dataSet;
    private EnumDataType dataType;

    public DisplayListAdapter(List<? extends AbstractModel> dataSet, EnumDataType dataType) {
        this.dataSet = dataSet;
        this.dataType = dataType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDisplayList item = new ItemDisplayList(parent.getContext());
        item.setDataType(dataType);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.item.evaluate(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ItemDisplayList item;
        public ViewHolder(ItemDisplayList  item) {
            super(item);
            this.item = item;
        }
    }

}
