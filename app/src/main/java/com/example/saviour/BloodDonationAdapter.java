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

public class BloodDonationAdapter extends ArrayAdapter<BloodDonationListItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;
    ArrayList<BloodDonationListItem>bDonationList;
    List<BloodDonationListItem>list;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView contactNumber;
        TextView bloodGroup;
    }


    /**
     * Default constructor for the BloodDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */




    public BloodDonationAdapter(Context context, int resource, ArrayList<BloodDonationListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getUserName();
        String contactNumber = getItem(position).getContactNumber();
        String bloodGroup = getItem(position).getBloodGroup();

        //Create the person object with the information
        BloodDonationListItem person = new BloodDonationListItem(bloodGroup,name,contactNumber);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView2);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.textView3);
            holder.bloodGroup = (TextView) convertView.findViewById(R.id.textView1);

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
        holder.name.setText(person.getUserName());
        holder.contactNumber.setText(person.getContactNumber());



        return convertView;
    }
}
