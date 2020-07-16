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

public class DonatedEventAdapter extends ArrayAdapter<DonatedEventListItem> {

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView givenAmount;
        TextView details;
        TextView total;
        TextView userName;
    }

    /**
     * Default constructor for the MedicalDonationAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public DonatedEventAdapter(Context context, int resource, ArrayList<DonatedEventListItem> objects) {
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
        String givenAmount = getItem(position).getGivenAmount();
        String total=getItem(position).getTotalAmount();
        String user=getItem(position).getUserName();

        //Create the person object with the information
        DonatedEventListItem person = new DonatedEventListItem(name,details,total,user, givenAmount);

        //create the view result for showing the animation
        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.details=(TextView)convertView.findViewById(R.id.textView2);
            holder.givenAmount = (TextView) convertView.findViewById(R.id.textView4);
            holder.total=(TextView)convertView.findViewById(R.id.textView3);
            holder.userName=(TextView)convertView.findViewById(R.id.textView);
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
        holder.givenAmount.setText(person.getGivenAmount());
        holder.total.setText(person.getTotalAmount());
        holder.details.setText(person.getDetails());
        holder.userName.setText(person.getUserName());

        return convertView;
    }
}
