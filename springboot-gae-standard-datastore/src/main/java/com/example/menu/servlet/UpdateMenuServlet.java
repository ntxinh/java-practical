package com.example.menu.servlet;

import com.example.guestbook.Guestbook;
import com.example.menu.entity.Menu;
import com.googlecode.objectify.Key;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by xinhnguyen on 31/08/2017.
 */
public class UpdateMenuServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String guestbookName = req.getParameter("guestbookName");
        Long menuId = Long.decode(req.getParameter("menuId"));
        String name = req.getParameter("name");
        String ingredient = req.getParameter("ingredient");
        String recipe = req.getParameter("recipe");
        String category = req.getParameter("category");

        // Find parent key
        Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

        // Find greet key
        Key<Menu> menuKey = Key.create(theBook, Menu.class, menuId);

        // Load
        Menu menu = ofy().load().key(menuKey).now();
        menu.name = name;
        menu.ingredient = ingredient;
        menu.recipe = recipe;
        menu.category = category;
        ofy().save().entity(menu).now();

        resp.sendRedirect("/menu.jsp?guestbookName=" + guestbookName);
    }
}
