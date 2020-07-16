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

public class EventDonationAdapter extends ArrayAdapter<EventDonationListItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView contactNumber;
        TextView details;
        TextView total;
        TextView username;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public EventDonationAdapter(Context context, int resource, ArrayList<EventDonationListItem> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String details=getItem(position).getDetails();
        String contactNumber = getItem(position).getContactNumber();
        String total=getItem(position).getTotalAmount();
        String userName=getItem(position).getUserName();

        //Create the person object with the information
        EventDonationListItem person = new EventDonationListItem(name,details, contactNumber,total, userName);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView);
            holder.details=(TextView)convertView.findViewById(R.id.textView1);
            holder.contactNumber = (TextView) convertView.findViewById(R.id.textView4);
            holder.total=(TextView)convertView.findViewById(R.id.textView2);
            holder.username=(TextView)convertView.findViewById(R.id.textView3);
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

        holder.name.setText(person.getName());
        holder.contactNumber.setText(person.getContactNumber());
        holder.total.setText(person.getTotalAmount());
        holder.details.setText(person.getDetails());
        holder.username.setText(person.getUserName());

        return convertView;
    }
}
