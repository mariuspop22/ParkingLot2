package org.example.parkinglot2;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.ejb.CarsBean;
import org.example.ejb.UsersBean;
import org.example.parkinglot.common.CarDto;
import org.example.parkinglot.common.UserDto;

import java.io.IOException;
import java.util.List;

@WebServlet(name="EditCar",value="/EditCar")
public class EditCar extends HttpServlet {

    @Inject
    UsersBean usersBean;
    @Inject
    CarsBean carsBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        List<UserDto> users=usersBean.findAllUsers();
        request.setAttribute("users",users);
            Long cardId=Long.parseLong(request.getParameter("id"));
            CarDto car=carsBean.findById(cardId);
            request.setAttribute("car",car);
        request.getRequestDispatcher("/WEB-INF/pages/editCar.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String licensePlate = request.getParameter("license_plate");
        String parkingSpot = request.getParameter("parking_spot");
        Long userId = Long.parseLong(request.getParameter("owner_id"));
        Long carId = Long.parseLong(request.getParameter("car_id"));

        carsBean.updateCar(carId, licensePlate, parkingSpot, userId);

        response.sendRedirect(request.getContextPath() + "/Cars");
    }


}
