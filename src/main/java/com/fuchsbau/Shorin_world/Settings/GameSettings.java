package com.fuchsbau.Shorin_world.Settings;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class GameSettings {

    private static GameSettings gameSettings;
    public float scrollspeed = 80.0f;
    public int enemyhealth;
    public int friendhealth;
    public TextField allySoldier, enemySoldier, moralSet;
    public JTextArea moralText, deaths;
    private int remvalFr = 0, addvalFr = 0;
    private int remvalEn = 0, addvalEn = 0;
    public int moral;
    Color c = Color.BLUE;


    private GameSettings() {
        moral = 100;
        enemyhealth = 0;
        friendhealth = 0;

    }

    public static GameSettings getInstance() {
        if (gameSettings == null) {
            gameSettings = new GameSettings();
        }
        return gameSettings;
    }

    public void addfriends() {
        addvalFr = Integer.parseInt(allySoldier.getText());
        if (addvalFr > 10000) {
            addvalFr = 10000;
        }
    }

    public void subfriends() {
        remvalFr = Integer.parseInt(allySoldier.getText());
        if (remvalFr > 10000) {
            remvalFr = 10000;
        }
    }

    public void addenemy() {
        addvalEn = Integer.parseInt(enemySoldier.getText());
        if (addvalEn > 10000) {
            addvalEn = 10000;
        }
    }

    public void subenemy() {
        remvalEn = Integer.parseInt(enemySoldier.getText());
        if (remvalEn > 10000) {
            remvalEn = 10000;
        }
    }

    public void setMoral() {
        moral = Integer.parseInt(moralSet.getText());
    }


    public void saveSettings() {
        FileWriter file = null;
        JSONObject obj = new JSONObject();
        obj.put("Name", "Crunchify.com");
        obj.put("Author", "App Shah");
        JSONArray company = new JSONArray();
        company.add("Company: Facebook");
        company.add("Company: PayPal");
        company.add("Company: Google");
        obj.put("Company List", company);
        try {
            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter("crunchify.txt");
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void loadSettings() {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("crunchify.txt")) {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj.get("Author");
            JSONArray test = (JSONArray) obj.get("test");
            test.get(1);
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    private static void parseEmployeeObject(JSONObject employee) {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }


    public void changecolour() {

        if (c.equals(Color.BLUE)) {
            c = Color.green;
        } else if (c.equals(Color.GREEN)) {
            c = Color.red;
        } else if (c.equals(Color.red)) {
            c = Color.cyan;
        } else if (c.equals(Color.cyan)) {
            c = Color.BLUE;
        }
    }

    public Color getColour() {
        return c;
    }

}
