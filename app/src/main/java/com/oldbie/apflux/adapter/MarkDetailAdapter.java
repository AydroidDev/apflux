package com.oldbie.apflux.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oldbie.apflux.R;
import com.oldbie.apflux.model.GetMark;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MarkDetailAdapter extends ArrayAdapter<GetMark.GetMarkDetail> {
    private Context context;
    private ArrayList <GetMark.GetMarkDetail> objects;

    public MarkDetailAdapter(@NonNull Context context, @NonNull ArrayList<GetMark.GetMarkDetail> objects) {
        super( context, 0, objects );
        this.context=context;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder vh;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate( R.layout.item_mark_detail, parent, false);
            vh = new ViewHolder();
            vh.tvMarkType = convertView.findViewById(R.id.tvMarkType);
            vh.tvPercentage = convertView.findViewById(R.id.tvPercentage);
            vh.tvMarkDetail = convertView.findViewById(R.id.tvMarkDetail);

            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        ArrayList<GetMark>arrGM = new ArrayList<>(  );

//
        for (int i=0;i<objects.size();i++){

            vh.tvMarkType.setText(objects.get( position ).getmMarkType());
            vh.tvPercentage.setText(objects.get( position ).getmPercentage());
            vh.tvMarkDetail.setText(objects.get( position ).getmMarkDetail());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView tvMarkType, tvPercentage, tvMarkDetail;
    }
}