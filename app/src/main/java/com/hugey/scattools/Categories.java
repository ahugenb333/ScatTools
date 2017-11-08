package com.hugey.scattools;

import android.text.TextUtils;
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
            Log.d("Random number: " + d, "asdf");


            return getListByID(d);
        }


}

//- (instancetype)initListWithIdentifier:(NSInteger)identifier {
//
//        self = [self init];
//
//        if (!self) { return nil; }
//
//
//
//        _identifier = identifier;
//
//
//
//        NSInteger multiplier = identifier;
//
//        NSMutableArray* identifiers = [NSMutableArray array];
//
//        NSInteger startMod = categories.count - 1;
//
//        while (identifiers.count < 12) {
//
//        NSNumber* nextIdentifier = @(multiplier % startMod);
//
//        if (![identifiers containsObject:nextIdentifier]) {
//
//                [identifiers addObject:nextIdentifier];
//
//                }
//
//                startMod--;
//
//                if (startMod <= 0) {
//
//                startMod = categories.count - 1;
//
//                multiplier = multiplier + startMod;
//
//                }
//
//                }
//
//
//
//                _items = [identifiers bk_map:^id(NSNumber* obj) {
//
//                return categories[obj.integerValue];
//
//                }];
//
//
//
//                return self;
//
//                }
