import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface TaskManager {

    Integer generatingId();

    public Map<Integer, Task> getAll();
    //public Map<Integer, Task> getAllTasks();

    public Map<Integer, Task> getAllEpics();

    public Map<Integer, Task> getAllSubTasks();

    public void delAllSubTasks();

    public void delAllEpics();

    //public void delAllTasks();

    public Task getById(int id);

    //public void createTask(Task task);

    public void createSubTask(SubTask subTask);

    public void createEpic(Epic epic);

    public void updateTask(Task newTask);

    public void dellById(int id);

    public ArrayList<Integer> getTasks(Epic epic);

    public void checkStatus(Epic epic);

    public void updateSubTask(SubTask newTask, TaskStatus status);

    public Task getEpic(int id);

    public Task getSubTask(int id);

}
