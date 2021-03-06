package tracker.controllers;

import dao.PointDAO;
import dao.repo.PointRepository;
import dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tracker.main.TrackerCoreApplication;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PointService {

    private static final Logger log = LoggerFactory.getLogger(TrackerCoreApplication.class);
    private static List<PointDAO> all = new ArrayList<>();

    @Autowired
    private PointMapper pointMapper;

    @Autowired
    private PointRepository pointRepository;


    public void create(Point point)
    {
        PointDAO pointDAO = new PointDAO();
        pointMapper.pointDTOToPointDAO(point, pointDAO);
        pointRepository.save(pointDAO);
    }

    public PointDAO create(float lat, float lon, float ele, long time) {
        PointDAO pointDAO = new PointDAO();
        pointDAO.setEle(ele);
        pointDAO.setLon(lon);
        pointDAO.setLat(lat);
        pointDAO.setTime(time);
        return pointRepository.save(pointDAO);
    }

    public void read() {
        all = (List<PointDAO>) pointRepository.findAll();

        if (all.size() == 0) {
            log.info("NO RECORDS");
        } else {
            all.stream().forEach(Point -> log.info(Point.toString()));
        }
    }

    private void update(Integer id, float lat, float lon, float ele, long time) {
        PointDAO pointDAO = pointRepository.findById(id).orElse(null);
        if (pointDAO==null) {return;}
        pointDAO.setEle(ele);
        pointDAO.setLon(lon);
        pointDAO.setLat(lat);
        pointDAO.setTime(time);
        pointRepository.save(pointDAO);
    }

    public void delete(Integer id) {
        PointDAO pointDAO = pointRepository.findById(id).orElse(null);
        if (pointDAO==null) {return;}
        pointRepository.delete(pointDAO);
    }
}
