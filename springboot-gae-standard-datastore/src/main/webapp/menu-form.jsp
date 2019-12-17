<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%@ page import="com.example.menu.entity.Menu" %>
<%@ page import="com.example.guestbook.Guestbook" %>
<%@ page import="com.googlecode.objectify.Key" %>
<%@ page import="static com.googlecode.objectify.ObjectifyService.ofy" %>
<%-- //[END imports]--%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- Latest compiled and minified JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>

<body>
<div class="container">
    <%
        String guestbookName = request.getParameter("guestbookName");
        Long menuId = Long.decode(request.getParameter("menuId"));
        if (guestbookName == null) {
            guestbookName = "default";
        }
        pageContext.setAttribute("guestbook_name", guestbookName);
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();
        if (user != null) {
            pageContext.setAttribute("user", user);
    %>
    <p>Hello, ${fn:escapeXml(user.nickname)}! (You can
        <a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">sign out</a>.)</p>
    <%
    } else {
    %>
    <p>Hello!
        <a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
        to include your name with greetings you post.</p>
    <%
        }
    %>

    <%-- //[START datastore]--%>
    <%
        // Create the correct Ancestor key
        Key<Guestbook> theBook = Key.create(Guestbook.class, guestbookName);

        // Run an ancestor query to ensure we see the most up-to-date
        // view of the Greetings belonging to the selected Guestbook.

        Key<Menu> menuKey = Key.create(theBook, Menu.class, menuId);
        Menu menu = ofy().load().key(menuKey).now();

        if (menu == null) {
    %>
    <p>Guestbook '${fn:escapeXml(guestbook_name)}' has no messages.</p>
    <%
    } else {
    %>
    <p>Messages in Guestbook '${fn:escapeXml(guestbook_name)}'.</p>

    <%
        pageContext.setAttribute("menu_id", menu.id);
        pageContext.setAttribute("menu_name", menu.name);
        pageContext.setAttribute("menu_ingredient", menu.ingredient);
        pageContext.setAttribute("menu_recipe", menu.recipe);
        pageContext.setAttribute("menu_category", menu.category);
    %>

    <%-- //[END datastore]--%>

    <div class="row">
        <div class="col-md-12">
            <form action="/update" method="post">
                <div class="form-group">
                    <label>Name:</label>
                    <textarea name="name" rows="3" cols="60" placeholder="Name" class="form-control">${fn:escapeXml(menu_name)}</textarea>
                </div>
                <div class="form-group">
                    <label>Ingredient:</label>
                    <textarea name="ingredient" rows="3" cols="60" placeholder="Ingredient"
                              class="form-control">${fn:escapeXml(menu_ingredient)}</textarea>
                </div>
                <div class="form-group">
                    <label>Recipe:</label>
                    <textarea name="recipe" rows="3" cols="60" placeholder="Recipe"
                              class="form-control">${fn:escapeXml(menu_recipe)}</textarea>
                </div>
                <div class="form-group">
                    <label>Select category:</label>
                    <select name="category" class="form-control" value="${fn:escapeXml(menu_category)}">
                        <option>Thịt heo</option>
                        <option>Thịt gà</option>
                        <option>Thịt bò</option>
                        <option>Cá</option>
                        <option>Trứng</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-default">Post Greeting</button>
                <input type="hidden" name="menuId" value="${fn:escapeXml(menu_id)}"/>
                <input type="hidden" name="guestbookName" value="${fn:escapeXml(guestbook_name)}"/>
            </form>
        </div>
    </div>

</div>
<%
    }
%>
</body>
</html>
<%-- //[END all]--%>
