package servlet;

import manager.BookManager;
import model.Book;
import model.User;
import util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Created by Hasmik on 26.06.2017.
 */
public class AddBookServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String price = req.getParameter("price");
        String description = req.getParameter("description");

        String errMessage = "";
        if (Validator.isEmpty(name)) {
            errMessage += "Name is empty";
        }
        if (Validator.isEmpty(author)) {
            errMessage += "Author is empty";
        }
        if (Validator.isEmpty(price)) {
            errMessage += "Price is empty";
        }
        if (Validator.isEmpty(description)) {
            errMessage += "Description is empty";
        }
        BookManager bookManager = new BookManager();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (errMessage.equals("")) {
            Book book = new Book();
            book.setName(name);
            book.setAuthor(author);
            book.setPrice(Double.parseDouble(price));
            book.setDescription(description);
            book.setUserId(user.getId());
            bookManager.addBook(book);
            req.setAttribute("info", "Book is added.");
            req.setAttribute("userBooks", bookManager.getBooksByUserId(user.getId()));
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } else {
            req.setAttribute("userBooks", bookManager.getBooksByUserId(user.getId()));
            req.setAttribute("errMessage", errMessage);
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }
    }
}
