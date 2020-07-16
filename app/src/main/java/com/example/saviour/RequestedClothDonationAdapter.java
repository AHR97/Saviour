package com.example.saviour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class RequestedClothDonationAdapter extends ArrayAdapter<RequestedClothDonationListItem> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView city;
        TextView area;
        TextView type;
        TextView place;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public RequestedClothDonationAdapter(Context context, int resource, ArrayList<RequestedClothDonationListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String city = getItem(position).getCity();
        String area=getItem(position).getArea();
        String type=getItem(position).getCategory();
        String place=getItem(position).getPlace();

        //Create the person object with the information
        RequestedClothDonationListItem person = new RequestedClothDonationListItem(city,area,type,place);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        RequestedClothDonationAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new RequestedClothDonationAdapter.ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.textView1);
            holder.area=(TextView)convertView.findViewById(R.id.textView2);
            holder.type=(TextView)convertView.findViewById(R.id.textView);
            holder.place=(TextView)convertView.findViewById(R.id.textView3);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (RequestedClothDonationAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.city.setText(person.getCity());
        holder.type.setText(person.getCategory());
        holder.area.setText(person.getArea());
        holder.place.setText(person.getPlace());

        return convertView;
    }
}
