package com.example.bottomnavapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class LibFragment extends Fragment {

    private String creature;
    private ImageSlider imageSlider;

    public LibFragment()
    {
    }

    public LibFragment(String param)
    {
       creature = param;
    }

    public static thirdFragment newInstance(String param1, String param2) {
        thirdFragment fragment = new thirdFragment();
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
        View view;
        view = inflater.inflate(R.layout.minotaur_frag, container, false);
        ArrayList<SlideModel> slideModels;
        switch (creature){
            case "Minotaur":
                view = inflater.inflate(R.layout.minotaur_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderMino);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.mino,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.mino2,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.mino3,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.mino4,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);

                break;
            case "Meduza":
                view = inflater.inflate(R.layout.medusa_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderMedu);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.medusa,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.medusa2,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.medusa3,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.medusa4,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);


                break;
            case "Centaur":
                view = inflater.inflate(R.layout.centaur_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderCent);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.centaur,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.centaur2,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.centaur3,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.centaur4,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);


                break;
            case "Pegasus":
                view = inflater.inflate(R.layout.pegasus_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderPega);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.pega,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.pegas2,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.pegas3,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.pegas4,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                break;
            case "Cerberus":
                view = inflater.inflate(R.layout.cerberus_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderPega);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.cerberus10,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.cerberus11,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.cerberus12,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.cerberus13,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                break;
            case "Dragon":
                view = inflater.inflate(R.layout.dragon_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderPega);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.dragon5,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.dragon8,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.dragon15,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.dragon16,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                break;
            case "Hidra":
                view = inflater.inflate(R.layout.hidra_frag, container, false);
                imageSlider = view.findViewById(R.id.imgSliderPega);

                slideModels = new ArrayList<>();

                slideModels.add(new SlideModel(R.drawable.hidra4,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.hidra13,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.hidra14,ScaleTypes.FIT));
                slideModels.add(new SlideModel(R.drawable.hidra18,ScaleTypes.FIT));

                imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                break;
        }

        return view;
    }
}
