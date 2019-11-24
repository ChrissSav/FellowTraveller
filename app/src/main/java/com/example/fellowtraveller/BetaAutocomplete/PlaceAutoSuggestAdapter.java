package com.example.fellowtraveller.BetaAutocomplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;

public class PlaceAutoSuggestAdapter extends ArrayAdapter implements Filterable {

    ArrayList<String> results;

    int resource;
    Context context;
    String key;
    PlaceApi placeApi=new PlaceApi();

    public PlaceAutoSuggestAdapter(Context context,int resId,String key){
        super(context,resId);
        this.context = context;
        this.key = key;
        this.resource = resId;

    }

    @Override
    public int getCount(){
        return results.size();

    }

    @Override
    public String getItem(int pos){
        return results.get(pos);
    }

    @Override
    public Filter getFilter(){
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint!=null){
                    results = placeApi.autoComplete(constraint.toString()+"&components=country:gr&language=el",key);
                    filterResults.values=results;
                    filterResults.count=results.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results!=null && results.count>0){
                    notifyDataSetChanged();
                }
                else{
                    notifyDataSetInvalidated();
                }

            }
        };
        return filter;
    }

}