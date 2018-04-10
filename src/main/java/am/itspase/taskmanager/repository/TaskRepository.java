package am.itspase.taskmanager.repository;

import am.itspase.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Integer> {
    List<Task> findAllByUserId(int id);

    List<Task> findAllByDeadline(Date deadline);
}
