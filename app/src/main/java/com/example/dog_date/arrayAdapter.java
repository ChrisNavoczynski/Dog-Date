package com.example.dog_date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class arrayAdapter extends ArrayAdapter<Upload> {

    public arrayAdapter(Context context, int resourceId, List<Upload> items){
        super(context, resourceId,items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Upload users_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        ImageView profileImage = (ImageView) convertView.findViewById(R.id.profileImage);

        name.setText(users_item.getOwnerName());
        Picasso.get().load(users_item.getmImageUrl()).into(profileImage);

        return  convertView;
    }
}
