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

public class MyDonatedCloth extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donated_cloth);

        listView= (ListView) findViewById(R.id.clothListViewId);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final ArrayList<ClothDonationListItem> cDonationList = new ArrayList<>();
        final ArrayList<ClothDonationListItem> cDonationListMax = new ArrayList<>();

        final ClothDonationAdapter adapter=new ClothDonationAdapter(this, R.layout.cloth_donation_listview,cDonationList);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot contactSnapshot=dataSnapshot.child("My Donations").child(uid).child("Cloth Donations");
                Iterable<DataSnapshot> contactChildren=contactSnapshot.getChildren();



                for(DataSnapshot contact : contactChildren){
                    ClothDonationListItem item= contact.getValue(ClothDonationListItem.class);
                    cDonationListMax.add(item);

                    String city=item.getCity();
                    String area=item.getArea();
                    String number=item.getContactNumber();
                    String type=item.getCategory();
                    String userName=item.getUserName();


                    ClothDonationListItem ob=new ClothDonationListItem(city, area,number,type,userName);

                    cDonationList.add(ob);


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
                ClothDonationListItem b=cDonationListMax.get(position);

//                String city=b.getCity();
//                String area=b.getArea();
//                String contactNumber=b.getContactNumber();
//                String type=b.getCategory();
//                String place=b.getPlace();
//                String userName=b.getUserName();
//                String uid=b.getUid();
//
//                Bundle bundle=new Bundle();
//
//                bundle.putString("City",city);
//                bundle.putString("Area",area);
//                bundle.putString("Number",contactNumber);
//                bundle.putString("Type",type);
//                bundle.putString("Place",place);
//                bundle.putString("User",userName);
//                bundle.putString("Uid",uid);
//
//
//                Intent intent=new Intent(GiveAClothDonation.this, DonateCloth.class);
//                intent.putExtras(bundle);
//                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);

            }
        });



    }
}
