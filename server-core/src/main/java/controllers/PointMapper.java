package controllers;

import dao.PointDAO;
import dto.Point;
import org.springframework.stereotype.Controller;

@Controller
public class PointMapper {

    public void pointDAOToPointDTO(PointDAO pointDAO, Point point) {
        point.setLat(pointDAO.getLat());
        point.setLon(pointDAO.getLon());
        point.setTime(pointDAO.getTime());
        point.setEle(pointDAO.getEle());
    }

    public void pointDTOToPointDAO(Point point, PointDAO pointDAO) {
        pointDAO.setLat(point.getLat());
        pointDAO.setLon(point.getLon());
        pointDAO.setTime(point.getTime());
        pointDAO.setEle(point.getEle());
    }

}
