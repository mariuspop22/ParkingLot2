package org.example.parkinglot2.cars;

import jakarta.annotation.security.DeclareRoles;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ejb.CarsBean;
import org.example.parkinglot.common.CarDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@DeclareRoles({"READ_CARS", "WRITE_CARS"})
@ServletSecurity(value = @HttpConstraint(rolesAllowed = {"READ_CARS"}),
        httpMethodConstraints = {@HttpMethodConstraint(value = "POST", rolesAllowed = {"WRITE_CARS"})})
@WebServlet(name = "Cars", value = "/Cars")
public class Cars extends HttpServlet {
    @Inject
    CarsBean carsBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {


        List<CarDto> cars= carsBean.findAllCars();
        request.setAttribute("cars", cars);
        int nr_of_free_parking_spots=carsBean.get_number_of_free_parkingspots(10);
        request.setAttribute("numberOfFreeParkingSpots", nr_of_free_parking_spots);
        request.getRequestDispatcher("/WEB-INF/pages/cars/cars.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String carIdsAsString[] = request.getParameterValues("car_ids");
        if (carIdsAsString != null) {
            List<Long>carIds = new ArrayList<Long>();
            for (String carIdAsString :carIdsAsString){
                carIds.add(Long.parseLong(carIdAsString));

            }
            carsBean.deleteCarsByIds(carIds);

        }

        String nr_matricol=request.getParameter("search");
        if(nr_matricol!=null){
            carsBean.gaseste_masina_dupa_nr_inmatriculare(nr_matricol);
        }
        response.sendRedirect(request.getContextPath()+"/Cars");

    }

}