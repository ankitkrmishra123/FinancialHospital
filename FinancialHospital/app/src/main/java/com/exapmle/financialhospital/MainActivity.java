package com.exapmle.financialhospital;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation mView;
    EditText DOP, investmentFor;
    ImageButton chatButton;
    final Calendar myCalendar = Calendar.getInstance();
    ImageView yourInfo, kyb, kyr, your_family;
    Dialog settingsDialog;
    TextView othersText;

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(DOP, myCalendar);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
        {
            actionBar.setTitle("Goals");
        }
        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        yourInfo = findViewById(R.id.yourInfo);
        kyb = findViewById(R.id.kyb);
        kyr = findViewById(R.id.kyr);
        your_family = findViewById(R.id.your_family);


        mView = findViewById(R.id.customBottomBar);
        mView.add(new MeowBottomNavigation.Model(1, R.drawable.profile_24));
        mView.add(new MeowBottomNavigation.Model(2, R.drawable.setting_all_functionality_24));
        mView.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_dashboard_24));
        mView.show(2, true);

        setOnClickListenerForImages();

        mView.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                //bottom navigation is clicked
                if (model.getId() == 1) {
                    Intent i = new Intent(MainActivity.this, Profile_Page.class);
                    startActivity(i);
                }
                if (model.getId() == 3) {
                    Intent i = new Intent(MainActivity.this, Dashboard.class);
                    startActivity(i);
                }
                return null;
            }
        });
    }

    private void dialogBoxForCLick(String layoutName, String otherTextToSHow) {

        settingsDialog = new Dialog(this);

        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(layoutName.equals("default")) {
            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.default_tab, null));

            DOP = settingsDialog.findViewById(R.id.DOP);
            investmentFor  = settingsDialog.findViewById(R.id.investmentFor);
            chatButton = settingsDialog.findViewById(R.id.chatButton);

            DOP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getCalenderView();
                }
            });

            investmentFor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupWindow popUp = popupWindowsort(investmentFor);
                    popUp.showAsDropDown(v, 120, 0); // show popup like dropdown list
                }
            });

            chatButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, ChatActivity.class);
                    startActivity(i);

                }
            });
        }
        else{
            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.others, null));
            othersText = settingsDialog.findViewById(R.id.othersText);
            if(otherTextToSHow.equals("kyb")){
                othersText.setText("KNOW YOU BETTER");
            } else if(otherTextToSHow.equals("kyr")){
                othersText.setText("KNOW YOUR RISK");
            }else if(otherTextToSHow.equals("your_family")){
                othersText.setText("YOUR FAMILY");
            }
        }



        /***
         * display a popup windows with transparent background image
         */
        settingsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));

//        /***
//         * Changing position of the Dialog on screen android
//         */
//        WindowManager.LayoutParams wlp = settingsDialog.getWindow().getAttributes();
//        wlp.gravity = Gravity.TOP;
        settingsDialog.getWindow().setLayout(795, 1450);

        WindowManager.LayoutParams layoutParams = settingsDialog.getWindow().getAttributes();
        layoutParams.y = 180; // bottom margin
        settingsDialog.getWindow().setAttributes(layoutParams);

        settingsDialog.setCancelable(true);
        settingsDialog.show();

    }

    private void setOnClickListenerForImages() {
        yourInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsDialog!=null && settingsDialog.isShowing()){
                    settingsDialog.dismiss();
                }else {
                    dialogBoxForCLick("default", null);
                }
            }
        });
        kyb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsDialog!=null && settingsDialog.isShowing()){
                    settingsDialog.dismiss();
                }else {
                    dialogBoxForCLick("others", "kyb");
                }
            }
        });
        kyr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsDialog!=null && settingsDialog.isShowing()){
                    settingsDialog.dismiss();
                }else {
                    dialogBoxForCLick("others", "kyr");
                }
            }
        });
        your_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingsDialog!=null && settingsDialog.isShowing()){
                    settingsDialog.dismiss();
                }else {
                    dialogBoxForCLick("others", "your_family");
                }
            }
        });
    }

    private void getCalenderView() {
        new DatePickerDialog(MainActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel(EditText DOP, Calendar myCalendar) {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DOP.setText(sdf.format(myCalendar.getTime()));
    }


    private PopupWindow popupWindowsort(final EditText editText) {
        final PopupWindow popupWindow = new PopupWindow(this);//or we can use auto complete text view and adapter

        ArrayList<String> sortList = new ArrayList<>();
        sortList.add("Self");
        sortList.add("Wife");
        sortList.add("Family");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.simple_dropdown_item_1line,
                sortList);
        final ListView listViewSort = new ListView(this);

        listViewSort.setAdapter(adapter);

        popupWindow.setFocusable(true);
        popupWindow.setWidth(350);
        popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.color.colorPrimary));//actually we have to put image in background here, but color can also be put in drawable
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    editText.setText((String) listViewSort.getAdapter().getItem(position)); //or we can Direct set value...........//or //listViewSort.getAdapter().getItem(position).toString()
                else if(position == 1)
                    editText.setText(listViewSort.getAdapter().getItem(position).toString());
                popupWindow.dismiss();
            }
        });

        return popupWindow;
    }
}