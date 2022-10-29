import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
//private Map<Integer, Task> tasks = new HashMap<>();
private Map<Integer, Task> subTasks = new HashMap<>();
private Map<Integer, Task> epics = new HashMap<>();
InMemoryHistoryManager historyManager = Managers.getDefaultHistory();

int id = 1;

@Override
public Integer generatingId(){
    return id++;
    }
    @Override
    public Map<Integer, Task> getAll() {
        Map<Integer, Task> allTasks = new HashMap<>();
        //allTasks.putAll(tasks);
        allTasks.putAll(epics);
        allTasks.putAll(subTasks);
        return allTasks;
    }
//    @Override
//    public Map<Integer, Task> getAllTasks(){
//    return tasks;
//    }
    @Override
    public Map<Integer, Task> getAllEpics(){
        return epics;
    }
    @Override
    public Map<Integer, Task> getAllSubTasks(){
        return subTasks;
    }
    @Override
    public void delAllSubTasks() {
        subTasks.clear();
    }
    @Override
    public void delAllEpics() {
        epics.clear();
    }
//    @Override
//        public void delAllTasks() {
//    tasks.clear();
//    }
    @Override
    public Task getById(int id) { //Переделать использую методы (getEpic, getSubtask)
    Task value = null;
    if(epics.containsKey(id)){
            value = getEpic(id);
        }else if(subTasks.containsKey(id)){
            value = getSubTask(id);
        }
    return value;
    }
//    @Override
//    public void createTask(Task task) {
//        tasks.put(task.getId(), task);
//    }
    @Override
    public void createSubTask(SubTask subTask) {
    subTasks.put(subTask.getId(), subTask);
    Epic tempEpic = (Epic) epics.get(subTask.getIdEpic());
    tempEpic.addList(subTask.getId());//Нужно найти нужный эпик по id в мапе и добавить в его Лист с айдишнками сабтасков...
    }
    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }
    @Override
    public void updateTask(Task newTask){
        if(epics.containsKey(newTask.getId())){
            epics.put(newTask.getId(), newTask);
        }else if(subTasks.containsKey(newTask.getId())){
            subTasks.put(newTask.getId(), newTask);
        }
    }
    @Override
    public void dellById(int id){
if(epics.containsKey(id)){
    epics.remove(id);
}else if(subTasks.containsKey(id)){
    subTasks.remove(id);
}

    }
    @Override
    public ArrayList<Integer> getTasks(Epic epic) {
    return epic.getSubTasksList();
    }
    @Override
    public void checkStatus(Epic epic) {
        if (!epic.getSubTasksList().isEmpty()) {
            ArrayList<Integer> subTasksList = epic.getSubTasksList();
            int countNew = 0;
            int countDone = 0;
            for (int i = 0; i < subTasksList.size(); i++) {
                Task task = subTasks.get(subTasksList.get(i));
                switch (task.getStatus()){
                    case NEW : countNew++;
                    break;
                    case DONE : countDone++;
                    break;
                }
            }
            if (countNew >= 1){
                if(countDone >= 1){
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                }else {
                    epic.setStatus(TaskStatus.NEW);
                }
            }else {
                epic.setStatus(TaskStatus.DONE);
            }
        }else {
            epic.setStatus(TaskStatus.NEW);
        }
    }
    @Override
    public void updateSubTask(SubTask newTask, TaskStatus status){
    newTask.setStatus(status);
        if(subTasks.containsKey(newTask.getId())){
            subTasks.put(newTask.getId(), newTask);
            checkStatus((Epic) epics.get(newTask.getIdEpic()));//Написать метод который вычисляет новый статус эпика, проверяя статусы сабтасков. Вызвать его тут.
        }else{
            System.out.println("Ошибка! Нет такой задачи");
        }
    }

    @Override
    public Task getSubTask(int id){
        historyManager.addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Task getEpic(int id){
    historyManager.addHistory(epics.get(id));
    return epics.get(id);
    }
}
