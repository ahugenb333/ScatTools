package com.hugey.scattools.Category;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by austin on 12/19/16.
 */

public class Categories {
        @Expose
        @SerializedName("categories")
        private ArrayList<Category> categories;

        public Categories() {

        }

        public ArrayList<Category> getCategories() {
            return this.categories;
        }

        public ArrayList<Category> getListByID(int id) {
            ArrayList<Category> toReturn = new ArrayList<>();

            int mult = id;

            int startMod = categories.size() - 1;

            while (toReturn.size() < 12) {
                int nextId = mult % startMod;

                if (!toReturn.contains(categories.get(nextId))) {
                    toReturn.add(categories.get(nextId));
                }

                startMod--;
                if (startMod <= 0) {
                    startMod = categories.size() - 1;
                }
                mult = mult + startMod;
            }
            return toReturn;
        }

        public ArrayList<Category> getRandomizedCategories() {
            int d = (int)Math.floor(Math.random() * categories.size());

            return getListByID(d);
        }
}