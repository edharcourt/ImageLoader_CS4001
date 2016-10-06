package com.example.ehar.imageloader;


import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        Resources r  = getResources();

        // get the four images in the grid layout
        for (int i = 0; i < 4; i++) {
            int id = r.getIdentifier("i" + i,
                    "id", getActivity().getPackageName());

            ImageButton ib = (ImageButton) root.findViewById(id);
            ib.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Drawable d = ((ImageView) view).getDrawable();
                    ((MainActivity) getActivity()).showMainImage(d);
                }
            });
        }

        return root;
    }

}
