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
public class DeleteMenuServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long menuId = Long.decode(req.getParameter("menuId"));
        String guestbookName = req.getParameter("guestbookName");

        // Find parent key
        Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

        // Find key
        Key<Menu> menuKey = Key.create(theBook, Menu.class, menuId);

        // Delete
        ofy().delete().key(menuKey);

        resp.sendRedirect("/menu.jsp?guestbookName=" + guestbookName);
    }
}
