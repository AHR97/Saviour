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
import java.util.List;

public class RequestedBloodDonationAdapter extends ArrayAdapter<RequestedBloodDonationListItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
   

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView city;
        TextView area;
        TextView bloodGroup;
        TextView units;
        TextView hospital;

    }


    /**
     * Default constructor for the RequestedBloodDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */




    public RequestedBloodDonationAdapter(Context context, int resource, ArrayList<RequestedBloodDonationListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String city = getItem(position).getCity();
        String area = getItem(position).getArea();
        String bloodGroup = getItem(position).getBloodGroup();
        String units = getItem(position).getNumberOfBags();
        String hospital=getItem(position).getHospital();
        
        
        //Create the person object with the information
        RequestedBloodDonationListItem person = new RequestedBloodDonationListItem(city, area, bloodGroup, units, hospital);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.city = (TextView) convertView.findViewById(R.id.textView1);
            holder.area = (TextView) convertView.findViewById(R.id.textView2);
            holder.bloodGroup = (TextView) convertView.findViewById(R.id.textView3);
            holder.units = (TextView) convertView.findViewById(R.id.textView4);
            holder.hospital = (TextView) convertView.findViewById(R.id.textView5);


            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.bloodGroup.setText(person.getBloodGroup());
        holder.city.setText(person.getCity());
        holder.area.setText(person.getBloodGroup());
        holder.hospital.setText(person.getHospital());
        holder.units.setText(person.getNumberOfBags());



        return convertView;
    }
}
