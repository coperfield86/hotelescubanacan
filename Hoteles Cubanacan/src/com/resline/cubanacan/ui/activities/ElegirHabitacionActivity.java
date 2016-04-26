package com.resline.cubanacan.ui.activities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.resline.cubanacan.R;
import com.resline.cubanacan.src.controllers.AppController;
import com.resline.cubanacan.src.ws.WSClass.Models.AvailableRoomTypeVO;
import com.resline.cubanacan.src.ws.WSClass.Models.HotelAvailabilitySearchResultVO;
import com.resline.cubanacan.src.ws.WSClass.Reservation.BookedRoom;
import com.resline.cubanacan.src.ws.WSClass.Reservation.Rooming;
import com.resline.cubanacan.ui.activities.api.BaseActivity;
import com.resline.cubanacan.ui.model.CheckBoxTag;
import com.resline.cubanacan.ui.views.RoomCheckBox;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by David on 23/04/2016.
 */
public class ElegirHabitacionActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    LinearLayout llRooms;
    CardView[] listCard;
    RelativeLayout[] relativeLayout;
    TextView[] textView;
    LinearLayout[] linearLayouts, linearLayoutsRooms;

    CardView[][] cardRoomTypeList;
    LinearLayout[][] rlRoomType;
    ImageView[][] imageViews;

    HotelAvailabilitySearchResultVO hotelSelected = null;
    Long hotelId = null;

    TextView totalPrice;
    double totalPriceValue = 0;

    CheckBox[][] checkBox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolBar();
        loadViews();
    }

    private void loadViews(){

        Bundle bundle = getIntent().getExtras();


        //Creando las habitaciones
        llRooms = (LinearLayout)findViewById(R.id.llRooms);
        totalPrice = (TextView)findViewById(R.id.tvAPagar);

        if(bundle != null)
            hotelId = bundle.getLong("idHotel");

        boolean hotelFind = false;
        if(hotelId != null) {
            for (HotelAvailabilitySearchResultVO hotel : AppController.getCurrentSearchResult().getHotelsAvaibility()) {
                if (hotel.getId() == hotelId) {
                    hotelSelected = hotel;
                    hotelFind = true;
                    break;
                }
            }

            if (hotelFind) {
                int countRooms = AppController.getRoomReservationRequest().getRooms().getBookedRoom().size();

                listCard = new CardView[countRooms];
                textView = new TextView[countRooms];
                linearLayouts = new LinearLayout[countRooms];
                linearLayoutsRooms = new LinearLayout[countRooms];
                cardRoomTypeList = new CardView[countRooms][];
                checkBox = new CheckBox[countRooms][];

                for (int i = 0; i < countRooms; i++) {
                    listCard[i] = new CardView(this);
                    listCard[i].setId(i);
                    CardView.LayoutParams layoutParams = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(10, 10, 10, 10);
                    listCard[i].setLayoutParams(layoutParams);

                    linearLayoutsRooms[i] = new LinearLayout(this);
                    linearLayoutsRooms[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    linearLayoutsRooms[i].setOrientation(LinearLayout.VERTICAL);

                    textView[i] = new TextView(this);
                    CardView.LayoutParams layoutParamsTextView = new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT);
                    textView[i].setLayoutParams(layoutParamsTextView);
                    textView[i].setTextSize(18);
                    textView[i].setText("Habitación " + (i + 1));
                    textView[i].setPadding(10, 10, 10, 10);
                    textView[i].setGravity(Gravity.CENTER);
                    textView[i].setTextColor(Color.BLACK);

                    linearLayouts[i] = new LinearLayout(this);
                    linearLayouts[i].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    linearLayouts[i].setOrientation(LinearLayout.VERTICAL);

                    linearLayoutsRooms[i].addView(textView[i]);

                    inflateRoomTypes(linearLayouts[i], i);

                    linearLayoutsRooms[i].addView(linearLayouts[i]);
                    listCard[i].addView(linearLayoutsRooms[i]);
                    llRooms.addView(listCard[i]);
                }
            }
        }
    }

    private void inflateRoomTypes(LinearLayout content, int position){
        List<AvailableRoomTypeVO> rooms = hotelSelected.getRoomAvailabilitySearchResults().get(position).getAvailableRoomTypes();

        int cont = 0;
        int roomingCount = rooms.size();
        cardRoomTypeList[position] = new CardView[roomingCount];
        checkBox[position] = new CheckBox[roomingCount];
        for(AvailableRoomTypeVO room : rooms) {

            LayoutInflater inflater = LayoutInflater.from(this);
            int id = R.layout.card_room_type;
            CardView cardView = (CardView) inflater.inflate(id, null, false);

            TextView roomCode = (TextView) cardView.findViewById(R.id.tvTitleOne);
            roomCode.setText(room.getCode()+String.valueOf(position));

            TextView roomPrice = (TextView) cardView.findViewById(R.id.tvSubTitleOne);
            String price = String.format("%s %s", String.valueOf(room.getPrice()), room.getCurrencyName());
            roomPrice.setText(price);

            ImageView roomImage = (ImageView) cardView.findViewById(R.id.ivRoomImage);
            Picasso.with(this)
                    .load(AppController.getHotels().get(hotelId).getImages().getImage().get(0).getImageUrl())
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.ic_launcher)
                    .into(roomImage);

            checkBox[position][cont] = (CheckBox) (cardView.findViewById(R.id.cbRoom));
            if(cont == 0){
                totalPriceValue += room.getPrice();
                setTotalPrice(String.valueOf(totalPriceValue), room.getCurrencyName());
                checkBox[position][cont].setChecked(true);
            }
            checkBox[position][cont].setTag(new CheckBoxTag(position, cont));
            checkBox[position][cont++].setOnCheckedChangeListener(this);

            content.addView(cardView);
        }
    }

    private void setTotalPrice(String totalPrice, String currency){
        String price = String.format("%s %s", totalPrice, currency);
        this.totalPrice.setText(price);
    }

    @Override
    protected int getLayoutResourceIdentifier() {
        return R.layout.activity_elegir_hab;
    }

    @Override
    protected String getTitleToolBar() {
        return mResources.getString(R.string.pick_room);
    }

    @Override
    protected Toolbar getLayoutResourceToolBar() {
        return (Toolbar) findViewById(R.id.screen_default_toolbar);
    }

    private void fragmentTransaction(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.llRooms, fragment)
                    .commit();
        }
    }

    private void setToolBar() {
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        /*switch (v.getId()) {
            case R.id.btnPrice:
                if (!btnPrice.isSelected())
                    btnPrice.setSelected(true);

                setFilterList(Filter.PRICE_FILTER);

                btnName.setSelected(false);
                btnCategory.setSelected(false);
                break;
            case R.id.btnName:
                if (!btnName.isSelected())
                    btnName.setSelected(true);

                setFilterList(Filter.NAME_FILTER);

                btnPrice.setSelected(false);
                btnCategory.setSelected(false);
                break;
            case R.id.btnCategory:
                // cambias el bundle aqui en dependencia del caso
                if (!btnCategory.isSelected())
                    btnCategory.setSelected(true);

                setFilterList(Filter.CATEGORY_FILTER);

                btnName.setSelected(false);
                btnPrice.setSelected(false);
                break;
        }
        Fragment fragment = new HotelesListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("filter", filter.ordinal());
        fragment.setArguments(bundle);
        fragmentTransaction(fragment);*/
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        CheckBoxTag tag = (CheckBoxTag) buttonView.getTag();
        int roomPosition = tag.getRoomPosition();
        int roomTypePosition = tag.getRoomTypePosition();
        boolean isUnique = true;

        //Algoritmo para que solo este marcado un check dentro de cada habitacion solicitada
        //Si lo que voy a hacer es deseleccionar no lo dejo hacer
        if(!isChecked)
            if(checkBox[roomPosition].length == 1)
                checkBox[roomPosition][roomTypePosition].setChecked(true);
        else{
            //Si selecciono alguna otra opcion entonces deselecciono la que etsaba marcada
            for(int i=0; i<checkBox[roomPosition].length; i++)
                if(checkBox[roomPosition][i].isChecked() && roomTypePosition != i)
                    checkBox[roomPosition][i].setChecked(false);
        }
    }
}
