package com.resline.cubanacan.ui.model;

/**
 * Created by David on 26/04/2016.
 */
public class CheckBoxTag {

    private int roomPosition;
    private int roomTypePosition;

    public CheckBoxTag(int roomPosition, int roomTypePosition) {
        this.roomPosition = roomPosition;
        this.roomTypePosition = roomTypePosition;
    }

    public int getRoomPosition() {
        return roomPosition;
    }

    public void setRoomPosition(int roomPosition) {
        this.roomPosition = roomPosition;
    }

    public int getRoomTypePosition() {
        return roomTypePosition;
    }

    public void setRoomTypePosition(int roomTypePosition) {
        this.roomTypePosition = roomTypePosition;
    }
}
