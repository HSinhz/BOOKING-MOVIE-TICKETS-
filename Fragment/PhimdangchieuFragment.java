package com.example.project_1.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.project_1.Adapter.ManagerFilmAdapter;
import com.example.project_1.Database.CreateAccount;
import com.example.project_1.Model.Film;
import com.example.project_1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhimdangchieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhimdangchieuFragment extends Fragment {

    private ListView listView;
    CreateAccount createAccount;
    ArrayList<Film> arrayList;
    ManagerFilmAdapter managerFilmAdapter;
    public PhimdangchieuFragment() {
        // Required empty public constructor
    }


    public static PhimdangchieuFragment newInstance() {
        PhimdangchieuFragment fragment = new PhimdangchieuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view =  inflater.inflate(R.layout.fragment_phimdangchieu, container, false);

        listView = (ListView) view.findViewById(R.id.listfilm);
        createAccount = new CreateAccount(getContext(), "Cinema.sqlite" , null , 1);
        arrayList = new ArrayList<>();
        managerFilmAdapter = new ManagerFilmAdapter(getContext(), R.layout.row_film_mgr, arrayList);
        listView.setAdapter(managerFilmAdapter);

        Bundle bundle = getArguments();
        int data = 0;
        if( bundle != null ){
           data = bundle.getInt("key");
        }

        if( data == 1) {
            Cursor dataFilm = createAccount.GetData("SELECT * FROM Phim WHERE TRANGTHAI == 1");
            while ( dataFilm.moveToNext() ){
                arrayList.add( new Film(
                        dataFilm.getInt(0),
                        dataFilm.getString(1),
                        dataFilm.getString(2),
                        dataFilm.getString(3),
                        dataFilm.getString(4),
                        dataFilm.getString(5),
                        dataFilm.getDouble(6),
                        dataFilm.getBlob(7)
                ));
            }
            managerFilmAdapter.notifyDataSetChanged();
        } else if( data == 2) {
            Cursor dataFilm = createAccount.GetData("SELECT * FROM Phim WHERE TRANGTHAI == 0");
            while ( dataFilm.moveToNext() ){
                arrayList.add( new Film(
                        dataFilm.getInt(0),
                        dataFilm.getString(1),
                        dataFilm.getString(2),
                        dataFilm.getString(3),
                        dataFilm.getString(4),
                        dataFilm.getString(5),
                        dataFilm.getDouble(6),
                        dataFilm.getBlob(7)
                ));
            }
            managerFilmAdapter.notifyDataSetChanged();
        }

        return view;
    }
}