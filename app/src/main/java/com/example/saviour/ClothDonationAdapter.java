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

public class ClothDonationAdapter extends ArrayAdapter<ClothDonationListItem> {
    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView city;
        TextView contactNumber;
        TextView area;
        TextView type;
        TextView username;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ClothDonationAdapter(Context context, int resource, ArrayList<ClothDonationListItem> objects) {
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
        String contactNumber = getItem(position).getContactNumber();
        String type=getItem(position).getCategory();
        String userName=getItem(position).getUserName();

        //Create the person object with the information
        ClothDonationListItem person = new ClothDonationListItem(city,area, contactNumber,type, userName);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ClothDonationAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ClothDonationAdapter.ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.textView1);
            holder.area=(TextView)convertView.findViewById(R.id.textView2);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.textView4);
            holder.type=(TextView)convertView.findViewById(R.id.textView);
            holder.username=(TextView)convertView.findViewById(R.id.textView3);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ClothDonationAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.city.setText(person.getCity());
        holder.contactNumber.setText(person.getContactNumber());
        holder.type.setText(person.getCategory());
        holder.area.setText(person.getArea());
        holder.username.setText(person.getUserName());

        return convertView;
    }
}
