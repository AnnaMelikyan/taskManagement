package am.itspase.taskmanager.repository;

import am.itspase.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

    User findOneByEmail(String email);
}
