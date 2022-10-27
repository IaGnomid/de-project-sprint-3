import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Manager {
private Map<Integer, Task> tasks = new HashMap<>();
private Map<Integer, Task> subTasks = new HashMap<>();
private Map<Integer, Task> epics = new HashMap<>();

int id = 1;

public Integer generatingId(){
    return id++;
    }

    public Map<Integer, Task> getAll() {
        Map<Integer, Task> allTasks = new HashMap<>();
        allTasks.putAll(tasks);
        allTasks.putAll(epics);
        allTasks.putAll(subTasks);
        return allTasks;
    }
    public Map<Integer, Task> getAllTasks(){
    return tasks;
    }

    public Map<Integer, Task> getAllEpics(){
        return epics;
    }

    public Map<Integer, Task> getAllSubTasks(){
        return subTasks;
    }

    public void delAllSubTasks() {
        subTasks.clear();
    }

    public void delAllEpics() {
        epics.clear();
    }

        public void delAllTasks() {
    tasks.clear();
    }

    public Task getById(int id) {
        Map<Integer, Task> allTasks = new HashMap<>();
        allTasks = getAll();
        Task value = null;
        for (Integer key : allTasks.keySet()) {
            if (key == id) {
                value = allTasks.get(key);
                break;
            }
        }
        return value;
    }

    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void createSubTask(SubTask subTask) {
    subTasks.put(subTask.getId(), subTask);
    Epic tempEpic = (Epic) epics.get(subTask.getIdEpic());
    tempEpic.addList(subTask.getId());//Нужно найти нужный эпик по id в мапе и добавить в его Лист с айдишнками сабтасков...
    }

    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    public void updateTask(Task newTask){
        if(tasks.containsKey(newTask.getId())){
            tasks.put(newTask.getId(), newTask);
        }else if(epics.containsKey(newTask.getId())){
            epics.put(newTask.getId(), newTask);
        }else if(subTasks.containsKey(newTask.getId())){
            subTasks.put(newTask.getId(), newTask);
        }
    }

    public void dellById(int id){
if(tasks.containsKey(id)){
    tasks.remove(id);
}else if(epics.containsKey(id)){
    epics.remove(id);
}else if(subTasks.containsKey(id)){
    subTasks.remove(id);
}

    }

    public ArrayList<Integer> getTasks(Epic epic) {
    return epic.getSubTasksList();
    }

    public void checkStatus(Epic epic) {
        if (!epic.getSubTasksList().isEmpty()) {
            ArrayList<Integer> subTasksList = epic.getSubTasksList();
            int countNew = 0;
            int countDone = 0;
            for (int i = 0; i < subTasksList.size(); i++) {
                Task task = subTasks.get(subTasksList.get(i));
                switch (task.getStatus()){
                    case "NEW" : countNew++;
                    break;
                    case "DONE" : countDone++;
                    break;
                }
            }
            if (countNew >= 1){
                if(countDone >= 1){
                    epic.setStatus("IN_PROGRESS");
                }else {
                    epic.setStatus("NEW");
                }
            }else {
                epic.setStatus("DONE");
            }
        }else {
            epic.setStatus("NEW");
        }
    }

    public void updateSubTask(SubTask newTask, String status){
    newTask.setStatus(status);
        if(tasks.containsKey(newTask.getId())){
            tasks.put(newTask.getId(), newTask);
        }else if(subTasks.containsKey(newTask.getId())){
            subTasks.put(newTask.getId(), newTask);
            checkStatus((Epic) epics.get(newTask.getIdEpic()));//Написать метод который вычисляет новый статус эпика, проверяя статусы сабтасков. Вызвать его тут.
        }else{
            System.out.println("Ошибка! Нет такой задачи");
        }
    }


}
