package com.oldbie.apflux.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oldbie.apflux.LoginActivity;
import com.oldbie.apflux.R;
import com.oldbie.apflux.adapter.MarkAdapter;
import com.oldbie.apflux.adapter.MarkDetailAdapter;
import com.oldbie.apflux.model.GetMark;
import com.oldbie.apflux.model.Mark;
import com.oldbie.apflux.model.ResponseGetMark;
import com.oldbie.apflux.model.ResponseMark;
import com.oldbie.apflux.network.NetworkAPI;
import com.oldbie.apflux.network.ServiceAPI;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMark extends Fragment {

    private ListView lvMark;
    private NetworkAPI api;
    private MarkAdapter adapter;
    private ArrayList<Mark> arrMark = new ArrayList<>();
    private int index = 0;

    //TEST..
    private ArrayList<GetMark> arrGetMark;
    //..

    public FragmentMark() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate( R.layout.fragment_mark, container, false );
        lvMark = view.findViewById( R.id.lvMark );
        api = ServiceAPI.userService( NetworkAPI.class );
        ShowMarkJSON(view);
        return view;
    }

    private void ShowMarkJSON(View view){
        view.getApplicationWindowToken();
        getActivity().runOnUiThread( new Runnable() {
            @Override
            public void run() {
            final String checkId = LoginActivity.arrSSR.get(0).getStudentId();
            final String token = LoginActivity.arrToken.get(0).getToken();
            Call<ResponseGetMark> call = api.getAllMark( checkId,index,token  );
            call.enqueue( new Callback<ResponseGetMark>() {
                @Override
                public void onResponse(Call<ResponseGetMark> call, Response<ResponseGetMark> response) {
                    if (response.body().getResult().equals( 1 )){
                        arrGetMark=response.body().getData();
                        for(int i=0;i<arrGetMark.size();i++){
//                            String toastTest = arrGetMark.get( i ).getmSubjectName();
//                            Toast.makeText( getContext(),toastTest,Toast.LENGTH_SHORT ).show();
                            adapter = new MarkAdapter( getContext(),arrGetMark );
                            lvMark.setAdapter( adapter );

                            lvMark.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    onClickItem(view,position);
                                }
                            } );
                        }
                    }else{
                        Toast.makeText( getContext(),"please check connection",Toast.LENGTH_SHORT ).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponseGetMark> call, Throwable t) {
                    Toast.makeText( getContext(),"please check connection",Toast.LENGTH_SHORT ).show();
                }
            } );
            }
        });
    }

    //.. EVENT CLICK ON ITEM ..//
    public void onClickItem(View view,int position){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(view.getContext());
        View viewMarkDetail = getLayoutInflater().inflate(R.layout.dialog_item_mark_detail, null);
        ArrayList<GetMark.GetMarkDetail> details = arrGetMark.get( position ).getArrayList();
        ListView listView = viewMarkDetail.findViewById(R.id.lvMarkDetail);

        MarkDetailAdapter adapter = new MarkDetailAdapter(view.getContext(), details);
        listView.setAdapter(adapter);

        alertDialog.setView(viewMarkDetail);
        alertDialog.setCancelable( false );
        alertDialog.setTitle( arrGetMark.get( position ).getmSubjectName() );
        alertDialog.setNegativeButton( "DONE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                dialog.dismiss();
            }
        } );
        AlertDialog dialog = alertDialog.create();
        dialog.show();

        ///////////////////////  ///////////////////////  ///////////////////////
//        lvMark.setOnItemClickListener( new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ArrayList<GetMark.GetMarkDetail> details = arrGetMark.get( position ).getArrayList();
//                String MarkType,MarkDetail,Perc;
//                for (int i=0;i<details.size();i++){
//                    MarkType = details.get( i ).getmMarkType();
//                    MarkDetail = details.get( i ).getmPercentage();
//                    Perc = details.get( i ).getmMarkDetail();
//
//                    if (details.isEmpty()){
//                        Toast.makeText( getContext(), "NULL",Toast.LENGTH_SHORT).show();
//
//                    }else{
////                        Toast.makeText( getContext(), MarkType+"\n"+ Perc+"\n"+MarkDetail,Toast.LENGTH_SHORT).show();
//                        DialogShow( position,MarkType,Perc,MarkDetail );
//
//                    }
//                }
//            }
//        } );
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
