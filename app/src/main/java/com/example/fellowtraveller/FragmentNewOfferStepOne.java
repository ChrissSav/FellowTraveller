package com.example.fellowtraveller;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;

import com.example.fellowtraveller.BetaAutocomplete.PlaceAutoSuggestAdapter;


public class FragmentNewOfferStepOne extends Fragment {

    private View view;
    private AutoCompleteTextView autoCompleteTextViewFrom,autoCompleteTextViewTo;



    public FragmentNewOfferStepOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_offer_step_one, container, false);
        String KEY = getString(R.string.api_key);

        autoCompleteTextViewFrom = view.findViewById(R.id.new_offer_autoComplete_editText_from);
        autoCompleteTextViewFrom.setAdapter(new PlaceAutoSuggestAdapter(getActivity(), android.R.layout.simple_list_item_1, KEY));
        autoCompleteTextViewTo = view.findViewById(R.id.new_offer_autoComplete_editText_to);
        autoCompleteTextViewTo.setAdapter(new PlaceAutoSuggestAdapter(getActivity(), android.R.layout.simple_list_item_1, KEY));
        return view;
    }


    public String toString(){
        return "stage1";
    }


    public boolean Check() {
        if (CheckFrom() & CheckTo())
            return true;
        else
            return false;
    }

    private boolean CheckFrom() {
        String d = autoCompleteTextViewFrom.getText().toString().trim();

        if (d.isEmpty()) {
            autoCompleteTextViewFrom.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            autoCompleteTextViewFrom.setError(null);
            return true;
        }
    }

    private boolean CheckTo() {
        String t = autoCompleteTextViewTo.getText().toString().trim();

        if (t.isEmpty()) {
            autoCompleteTextViewTo.setError("Υποχρεωτικό Πεδίο!");
            return false;
        } else {
            autoCompleteTextViewTo.setError(null);
            return true;

        }
    }
    public String GetFrom(){
        return autoCompleteTextViewFrom.getText().toString();
    }
    public String GetTo(){
        return autoCompleteTextViewTo.getText().toString();
    }
}
