import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {
private Map<Integer, Task> tasks = new HashMap<>();
private Map<Integer, SubTask> subTasks = new HashMap<>();
private Map<Integer, Epic> epics = new HashMap<>();
private HistoryManager historyManager = Managers.getDefaultHistory();

private int id = 1;

    @Override
    public Integer generatingId(){
    return id++;
    }

    @Override
    public Map<Integer, Task> getAllTasks(){
        HashMap<Integer, Task> copyTasks = new HashMap<Integer, Task>(tasks);
        return copyTasks;
    }
    @Override
    public Map<Integer, Epic> getAllEpics(){
        HashMap<Integer, Epic> copyEpics = new HashMap<Integer, Epic>(epics);
        return copyEpics;
    }
    @Override
    public Map<Integer, SubTask> getAllSubTasks(){
        HashMap<Integer, SubTask> copySubTask = new HashMap<Integer, SubTask>(subTasks);
        return copySubTask;
    }
    @Override
    public void delAllSubTasks() {
        if(!subTasks.isEmpty()) {
            for (Map.Entry<Integer, Epic> pair : epics.entrySet()) {
                pair.getValue().clearList();
                pair.getValue().setStatus(TaskStatus.NEW);
            }
            subTasks.clear();
        }
    }
    @Override
    public void delAllEpics() {
        epics.clear();
        subTasks.clear();

    }
    @Override
        public void delAllTasks() {
    tasks.clear();
    }

    @Override
    public void createTask(Task task) {
        tasks.put(task.getId(), task);
    }
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
        if(tasks.containsKey(newTask.getId())){
            //newTask.setStatus(status);
            tasks.put(newTask.getId(), newTask);
        }
    }
    @Override
    public void updateSubTask(SubTask newTask){
        //newTask.setStatus(status);
        if(subTasks.containsKey(newTask.getId())){
            subTasks.put(newTask.getId(), newTask);
            checkStatus(epics.get(newTask.getIdEpic()));
        }
    }
    @Override
    public void updateEpic(Epic newEpic){
        if(epics.containsKey(newEpic.getId())){
            epics.put(newEpic.getId(), newEpic);
        }
    }
    @Override
    public boolean dellEpicById(int id){
        if(epics.containsKey(id)) {
            ArrayList<Integer> listId = epics.get(id).getSubTasksList();
            for (int i = 0; i < listId.size(); i++){
                subTasks.remove(listId.get(i));
            }
            epics.remove(id);
        }else{
            return false;
        }
        return true;
    }

    public boolean dellSubTaskById(int id){
        if(subTasks.containsKey(id)) {
            subTasks.remove(id);
        }else{
            return false;
        }
        return true;
    }

    public boolean dellTaskById(int id){
        if(tasks.containsKey(id)) {
            tasks.remove(id);
        }else{
            return false;
        }
        return true;
    }

    @Override
    public List<SubTask> getTasks(Epic epic) {
    List<SubTask> list = new ArrayList<>();
    ArrayList<Integer> listId = epic.getSubTasksList();
    for (int i = 0; i < listId.size(); i++){
        list.add(subTasks.get(listId.get(i)));
    }
        return list;
    }
    private void checkStatus(Epic epic) {
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
    public SubTask getSubTask(int id){
        historyManager.addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpic(int id){
    historyManager.addHistory(epics.get(id));
    return epics.get(id);
    }

    @Override
    public List<Task> getHistory(){
        return historyManager.getHistory();
    }
}
