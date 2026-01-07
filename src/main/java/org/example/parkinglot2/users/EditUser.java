package org.example.parkinglot2.users;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ejb.UsersBean;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.common.UserDto;

import java.io.IOException;
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"WRITE_USERS"}))
@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {
    @Inject
    UsersBean usersBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        Long userId=Long.parseLong(request.getParameter("id"));
        UserDto user=usersBean.findUserByUserId(userId);
        request.setAttribute("user",user);


        request.getRequestDispatcher("/WEB-INF/pages/users/editUser.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String user_id=request.getParameter("user_id");
        Long user_id2=Long.parseLong(user_id);
        String email=request.getParameter("email");
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        usersBean.updateUser(user_id2,username,email,password);
        response.sendRedirect(request.getContextPath() + "/Users");



    }
}