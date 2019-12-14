package com.example.fellowtraveller.BetaActivity;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fellowtraveller.R;



public class BottomFragment extends Fragment {
    private Button chat,home,notification;

    public BottomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        chat = view.findViewById(R.id.BottomFragment_button_chat);
        home = view.findViewById(R.id.BottomFragment_button_home);
        notification = view.findViewById(R.id.BottomFragment_button_notification);
        return view;
    }

    
    public void ChangeStatusOfBottonChat(Boolean flag){
        if(flag){
            chat.setSelected(true);
        }
        else{
            chat.setSelected(false);
        }
    }

    public void ChangeStatusOfBottonHome(Boolean flag){
        if(flag){
            home.setSelected(true);
        }
        else{
            home.setSelected(false);
        }
    }
    public void ChangeStatusOfBottonNotification(Boolean flag){
        if(flag){
            notification.setSelected(true);
        }
        else{
            notification.setSelected(false);
        }
    }
}
