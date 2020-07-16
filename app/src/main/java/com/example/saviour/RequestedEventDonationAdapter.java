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

public class RequestedEventDonationAdapter extends ArrayAdapter<RequestedEventDonationListItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView details;
        TextView total;
        TextView collected;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public RequestedEventDonationAdapter(Context context, int resource, ArrayList<RequestedEventDonationListItem> objects) {
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
        String total=getItem(position).getTotalAmount();
        String collected=getItem(position).getCollectedAmount();

        //Create the person object with the information
        RequestedEventDonationListItem person = new RequestedEventDonationListItem(name,details,total, collected);

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
            holder.total=(TextView)convertView.findViewById(R.id.textView2);
            holder.collected=(TextView)convertView.findViewById(R.id.textView3);
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
        holder.total.setText(person.getTotalAmount());
        holder.details.setText(person.getDetails());
        holder.collected.setText(person.getCollectedAmount());

        return convertView;
    }
}
