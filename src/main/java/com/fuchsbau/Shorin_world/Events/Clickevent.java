package com.fuchsbau.Shorin_world.Events;

import com.fuchsbau.Shorin_world.Settings.GameSettings;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Clickevent implements MouseListener {
    private int event;

    public Clickevent(int event) {
        this.event = event;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        switch (event) {
            case 0:
                GameSettings.getInstance().saveSettings();
                break;
            case 1:
                GameSettings.getInstance().loadSettings();
                break;
            case 2:
                GameSettings.getInstance().subfriends();
                break;
            case 3:
                GameSettings.getInstance().addenemy();
                break;
            case 4:
                GameSettings.getInstance().subenemy();
                break;
            case 5:
                GameSettings.getInstance().setMoral();
                break;

            default:
                System.out.println("Event doesnt exist:" + event);
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
