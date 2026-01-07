package org.example.parkinglot2;

import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.example.ejb.CarsBean;
import org.example.parkinglot.common.CarPhotoDto;

import java.io.IOException;

@WebServlet(name = "CarPhotos", value = "/CarPhotos")
public class CarPhotos extends HttpServlet {
    @Inject
    CarsBean carsBean;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        Integer carId=Integer.parseInt(request.getParameter("id"));
        CarPhotoDto photo=carsBean.findPhotoByCarId(carId);
        if(photo!=null){
            response.setContentType(photo.getFileType());
            response.setContentLength(photo.getFileContent().length);
            response.getOutputStream().write(photo.getFileContent());
        }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }


    }


}