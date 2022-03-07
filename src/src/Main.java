/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;
import src.DataRepository;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author samir
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //DataRepository newData = new DataRepository();
        Home home = new  Home();
        home.setVisible(true);
    }
}
