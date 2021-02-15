package dao.repo;

import dao.PointDAO;
import org.springframework.data.repository.CrudRepository;

public interface PointRepository extends CrudRepository<PointDAO, Integer> {
}