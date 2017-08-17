package servlet;

import manager.UserManager;
import model.Gender;
import model.User;
import util.Validator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hasmik on 25.06.2017.
 */
public class RegisterServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");
        String password = req.getParameter("password");

        String errMessage = "";
        UserManager userManager = new UserManager();

        if (Validator.isEmpty(name)) {
            errMessage += "Name is empty. <br>";
        }
        if (Validator.isEmpty(surname)) {
            errMessage += "Surname is empty. <br>";
        }
        if (Validator.isEmpty(email)) {
            errMessage += "Email is empty.<br>";
        } else if (userManager.isEmailExists(email)) {
            errMessage += "Email already exists.Try another one.";
        }
        if (Validator.isEmpty(gender)) {
            errMessage += "Gender is empty. <br>";
        }
        if (Validator.isEmpty(password)) {
            errMessage += "Password id empty.<br>";
        }

        if (errMessage.equals("")) {
            User user = new User(name, surname, email, Gender.valueOf(gender), password);
            userManager.addUser(user);
            resp.sendRedirect("index.jsp");
        }
        else {
            req.setAttribute("errMessage", errMessage);
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }

    }
}
