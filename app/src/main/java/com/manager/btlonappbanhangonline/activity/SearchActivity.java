
package com.manager.btlonappbanhangonline.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.manager.btlonappbanhangonline.R;
import com.manager.btlonappbanhangonline.adapter.PhoneAdapter;
import com.manager.btlonappbanhangonline.model.NewProduct;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    EditText edtsearch;
    PhoneAdapter adapterDt;
    List<NewProduct> sanPhamMoiList;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.i("activity: ", "Main");
        db = FirebaseFirestore.getInstance();

        initView();
    }

    private void initView() {
        sanPhamMoiList = new ArrayList<>();
        edtsearch=findViewById(R.id.edtsearch);
        recyclerView=findViewById(R.id.recycleview_search);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==0){
                    sanPhamMoiList.clear();
                    adapterDt = new PhoneAdapter(getApplicationContext(), sanPhamMoiList);
                    recyclerView.setAdapter(adapterDt);
                }else{
                    getDataSearch(s.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void getDataSearch(String s) {
        sanPhamMoiList.clear();
        db.collection("items").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("onEvent: ", error.getMessage());
                    return;
                }
                try {
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            sanPhamMoiList.add(dc.getDocument().toObject(NewProduct.class));
                        }
                    }
                } catch (Exception e) {
                    Log.i("error when getting data:", e.toString());
                }
                adapterDt = new PhoneAdapter(getApplicationContext(), sanPhamMoiList);
                recyclerView.setAdapter(adapterDt);
                adapterDt.notifyDataSetChanged();
            }
        });
    }

    /*private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }*/

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}