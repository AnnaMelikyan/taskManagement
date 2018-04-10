package am.itspase.taskmanager.rest;

import am.itspase.taskmanager.model.Task;
import am.itspase.taskmanager.model.User;
import am.itspase.taskmanager.repository.TaskRepository;
import am.itspase.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    SimpleDateFormat dt = new SimpleDateFormat("yyyy-mm-dd");

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private  UserRepository userRepository;

    @GetMapping
    public List<Task> allTasks(){
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public  Task getTaskById(@PathVariable(name = "id") int id){
       Task task = taskRepository.findOne(id);
        return task;
    }

    @PostMapping
    public ResponseEntity addTask(@RequestBody Task task)  {
        try {
           if(userRepository.exists(task.getUserId())){
            Date date = dt.parse(task.getDeadline());
            task.setDeadline(dt.format(date));
            taskRepository.save(task);}
        } catch (ParseException e) {
            e.printStackTrace();
        }
            return ResponseEntity.ok("Created");
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity delTaskById(@PathVariable(name = "id") int id){
        try{
            taskRepository.delete(id);
            return ResponseEntity.ok("Deleted");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Task with " + id + " does not exist");
        }
    }

    @PutMapping
    public ResponseEntity updateTask(@RequestBody Task task){
        if (taskRepository.exists(task.getId())){
            taskRepository.save(task);
            return ResponseEntity.ok("Updated");
        } else
            return ResponseEntity.badRequest().body("Task with " + task.getId() + " does not exist");
    }

    @GetMapping("/byUserId/{userId}")
    public  List<Task> getTaskByUserId(@PathVariable(name = "userId") int userId){
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        return tasks;
    }

    @GetMapping("/byDeadline/{date}")
    public  List<Task> getTaskByDeadline(@PathVariable(name = "deadline") String deadline) throws ParseException {
            Date date = dt.parse(deadline);
            List<Task> tasks = taskRepository.findAllByDeadline(date);
        return tasks;

    }
}
