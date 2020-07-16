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

public class FoodDonationAdapter extends ArrayAdapter<FoodDonationListItem> {

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
        TextView reason;
        TextView username;
        TextView number;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public FoodDonationAdapter(Context context, int resource, ArrayList<FoodDonationListItem> objects) {
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
        String reason=getItem(position).getReason();
        String userName=getItem(position).getUserName();
        String number=getItem(position).getNumberOfPeople();

        //Create the person object with the information
        FoodDonationListItem person = new FoodDonationListItem(city,area, contactNumber,reason, userName, number);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        FoodDonationAdapter.ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new FoodDonationAdapter.ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.textView1);
            holder.area=(TextView)convertView.findViewById(R.id.textView2);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.textView4);
            holder.reason=(TextView)convertView.findViewById(R.id.textView);
            holder.username=(TextView)convertView.findViewById(R.id.textView3);
            holder.number=(TextView)convertView.findViewById(R.id.textView5);
            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (FoodDonationAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.city.setText(person.getCity());
        holder.contactNumber.setText(person.getContactNumber());
        holder.reason.setText(person.getReason());
        holder.area.setText(person.getArea());
        holder.username.setText(person.getUserName());
        holder.number.setText(person.getNumberOfPeople());

        return convertView;
    }
}
