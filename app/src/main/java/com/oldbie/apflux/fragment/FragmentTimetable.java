package com.oldbie.apflux.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.oldbie.apflux.LoginActivity;
import com.oldbie.apflux.R;
import com.oldbie.apflux.adapter.TimeTableAdapter;
import com.oldbie.apflux.model.ResponseTimeTable;
import com.oldbie.apflux.model.TimeTable;
import com.oldbie.apflux.network.NetworkAPI;
import com.oldbie.apflux.network.ServiceAPI;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTimetable extends Fragment {
    private String TAG = "TimeTable";
    private ListView lvMain;
    private ArrayList<TimeTable> arrTimeTable;
    private NetworkAPI api;
    private TimeTableAdapter adapter;

    public FragmentTimetable() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate( R.layout.fragment_timetable, container, false );
        lvMain = view.findViewById( R.id.lvMain );
        api = ServiceAPI.userService( NetworkAPI.class );
        lvMain.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // toggle clicked cell state
                ((FoldingCell) view).toggle(false);
                // register in adapter that state for selected cell is toggled
                adapter.registerToggle(position);
            }
        });
        ShowDataJSON();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ShowDataJSON();
    }

    private void ShowDataJSON(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
            final String checkId = LoginActivity.arrSSR.get(0).getStudentId();
            final String token = LoginActivity.arrToken.get(0).getToken();
            Call<ResponseTimeTable> call = api.getAllData(checkId, token);
            call.enqueue(new Callback<ResponseTimeTable>() {
                @Override
                public void onResponse(Call<ResponseTimeTable> call, Response<ResponseTimeTable> response) {
                    if(response.body().getResult()==1){
                        arrTimeTable = response.body().getData();
                        for(int i=0;i<arrTimeTable.size();i++){
                            adapter = new TimeTableAdapter(getContext(),arrTimeTable);
                            lvMain.setAdapter(adapter);
                        }
                    } else {
                        Toast.makeText(getContext(),"Errors:...",Toast.LENGTH_SHORT).show();
                        Log.e( TAG,"Errors: ..."+ response.body().getResult());
                    }
                }

                @Override
                public void onFailure(Call<ResponseTimeTable> call, Throwable t) {

                }
            });
            }
        });
    }
}
