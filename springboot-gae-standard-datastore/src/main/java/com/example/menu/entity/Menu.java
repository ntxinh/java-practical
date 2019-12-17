/**
 * Copyright 2014-2015 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

//[START all]
package com.example.menu.entity;

import com.example.guestbook.Guestbook;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.lang.String;
import java.util.Date;

@Entity
public class Menu {
    @Parent
    Key<Guestbook> theBook;
    @Id
    public Long id;

    public String author_email;
    public String author_id;
    public String name;       // Ten mon an
    public String ingredient; // Nguyen lieu
    public String recipe;     // Cong thuc
    public String category;   // Loai
    @Index
    public Date date;

    /**
     * Simple constructor just sets the date
     **/
    public Menu() {
        date = new Date();
    }

    /**
     * A convenience constructor
     **/
    public Menu(String book, String name, String ingredient, String recipe, String category) {
        this();
        if (book != null) {
            theBook = Key.create(Guestbook.class, book);  // Creating the Ancestor key
        } else {
            theBook = Key.create(Guestbook.class, "default");
        }
        this.name = name;
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.category = category;
    }

    /**
     * Takes all important fields
     **/
    public Menu(String book, String name, String ingredient, String recipe, String id, String email, String category) {
        this(book, name, ingredient, recipe, category);
        author_email = email;
        author_id = id;
    }

}
//[END all]
