//[START all]
package com.example.menu.servlet;

import com.example.menu.entity.Menu;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class CreateMenuServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String guestbookName = req.getParameter("guestbookName");

        Menu menu;

        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();  // Find out who the user is.

        String name = req.getParameter("name");
        String ingredient = req.getParameter("ingredient");
        String recipe = req.getParameter("recipe");
        String category = req.getParameter("category");
        if (user != null) {
            menu = new Menu(guestbookName, name, ingredient, recipe, category, user.getUserId(), user.getEmail());
        } else {
            menu = new Menu(guestbookName, name, ingredient, recipe, category);
        }

        // Use Objectify to save the greeting and now() is used to make the call synchronously as we
        // will immediately get a new page using redirect and we want the data to be present.
        ofy().save().entity(menu).now();

        resp.sendRedirect("/menu.jsp?guestbookName=" + guestbookName);
    }
}
//[END all]
