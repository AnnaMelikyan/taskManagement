package am.itspase.taskmanager.rest;

import am.itspase.taskmanager.model.User;
import am.itspase.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.swing.BakedArrayList;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> allUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public  User getUserById(@PathVariable(name = "id") int id){
        User user = userRepository.findOne(id);
        return user;

    }

    @PostMapping
    public ResponseEntity  addUser(@RequestBody User user){
        if (userRepository.findOneByEmail(user.getEmail()) == null){
            userRepository.save(user);
            return ResponseEntity.ok("Created");
        } return ResponseEntity.badRequest().body("User with " + user.getEmail() + " already exist");
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity delUserById(@PathVariable(name = "id") int id){
        try{
            userRepository.delete(id);
            return ResponseEntity.ok("Deleted");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("user with " + id + " does not exist");
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user){
        if (userRepository.exists(user.getId())){
            userRepository.save(user);
            return ResponseEntity.ok("Updated");
        } else
            return ResponseEntity.badRequest().body("User with " + user.getId() + " does not exist");
    }


}
