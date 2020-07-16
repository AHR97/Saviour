package com.example.saviour;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyDonatedFood extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donated_food);

        listView= (ListView) findViewById(R.id.foodListViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final ArrayList<FoodDonationListItem> fDonationList = new ArrayList<>();
        final ArrayList<FoodDonationListItem> fDonationListMax = new ArrayList<>();


        final FoodDonationAdapter adapter=new FoodDonationAdapter(this, R.layout.food_donation_listview,fDonationList);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot=dataSnapshot.child("My Donations").child(uid).child("Food Donations");
                Iterable<DataSnapshot> contactChildren=contactSnapshot.getChildren();



                for(DataSnapshot contact : contactChildren){
                    FoodDonationListItem item= contact.getValue(FoodDonationListItem.class);
                    fDonationListMax.add(item);

                    String city=item.getCity();
                    String area=item.getArea();
                    String number=item.getContactNumber();
                    String type=item.getReason();
                    String userName=item.getUserName();
                    String people=item.getNumberOfPeople();


                    FoodDonationListItem ob=new FoodDonationListItem(city, area,number,type,userName, people);

                    fDonationList.add(ob);


                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FoodDonationListItem b=fDonationListMax.get(position);

//                String city=b.getCity();
//                String area=b.getArea();
//                String contactNumber=b.getContactNumber();
//                String reason=b.getReason();
//                String place=b.getPlace();
//                String userName=b.getUserName();
//                String people=b.getNumberOfPeople();
//                String uid=b.getUid();
//
//                Bundle bundle=new Bundle();
//
//                bundle.putString("City",city);
//                bundle.putString("Area",area);
//                bundle.putString("Number",contactNumber);
//                bundle.putString("Reason",reason);
//                bundle.putString("Place",place);
//                bundle.putString("User",userName);
//                bundle.putString("People",people);
//                bundle.putString("Uid",uid);
//
//                Intent intent=new Intent(GiveAFoodDonation.this, DonateFood.class);
//                intent.putExtras(bundle);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

            }
        });




    }
}
