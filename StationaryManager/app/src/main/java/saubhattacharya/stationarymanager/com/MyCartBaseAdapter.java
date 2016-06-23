package saubhattacharya.stationarymanager.com;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCartBaseAdapter extends BaseAdapter {

    public Context ba_context;
    public ArrayList<ListRowItem> listitem = new ArrayList<>();
    public LayoutInflater inflater;
    ListRowItem currentlistitem;
    String status;

    public MyCartBaseAdapter(Context ma_context, ArrayList<ListRowItem> ma_listitem) {
        super();
        this.ba_context = ma_context;
        this.listitem = ma_listitem;

        inflater = (LayoutInflater) ba_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return this.listitem.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listitem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (convertView == null)
            vi = inflater.inflate(R.layout.single_listitem_cart, parent, false);

        TextView item_name = (TextView) vi.findViewById(R.id.item_name);
        TextView item_qty = (TextView) vi.findViewById(R.id.item_qty);
        TextView item_id = (TextView) vi.findViewById(R.id.item_id);

        currentlistitem = listitem.get(position);

        String str_item_name = currentlistitem.getItemName();
        String str_item_id = currentlistitem.getItemId();
        String str_item_qty = currentlistitem.getItemQty();

        item_name.setText(str_item_name);
        item_qty.setText(str_item_qty);
        item_id.setText(str_item_id);

        return vi;
    }
}


