package tracker.services;

import DTO.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Service
public class PushMessagesService {

    private static final Logger log = LoggerFactory.getLogger(PushMessagesService.class);

    // Накапливаемые данные
    private static final BlockingDeque<Point> localPoints =  new LinkedBlockingDeque<>(100);

    public Point getLast() {
        if (pointsReadyCount()>0) {
            return localPoints.pollFirst();
        }
        else {
            return null;
        }
    }

    public int pointsReadyCount() {
        return localPoints.size();
    }

    public int putPoint(Point point) {
        localPoints.add(point);
        log.info(point.toString());
        return localPoints.size();
    }
}
