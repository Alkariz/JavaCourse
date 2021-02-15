package tracker.services;

import dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PushMessagesService {

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Накапливаемые данные
    private static final BlockingDeque<Point> LOCAL_POINT_DTOS =  new LinkedBlockingDeque<>(100);

    public Point getLast() {
        if (pointsReadyCount()>0) {
            return LOCAL_POINT_DTOS.pollFirst();
        }
        else {
            return null;
        }
    }

    public int pointsReadyCount() {
        return LOCAL_POINT_DTOS.size();
    }

    public int putPoint(Point point) {
        LOCAL_POINT_DTOS.add(point);
        log.info(point.toString());
        return LOCAL_POINT_DTOS.size();
    }
}
