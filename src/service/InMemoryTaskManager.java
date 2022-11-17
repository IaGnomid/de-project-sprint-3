package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;

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
    public Integer generatingId() {
        return id++;
    }

    @Override
    public Map<Integer, Task> getAllTasks() {
        return new HashMap<>(tasks);
    }

    @Override
    public Map<Integer, Epic> getAllEpics() {
        return new HashMap<>(epics);
    }

    @Override
    public Map<Integer, SubTask> getAllSubTasks() {
        return new HashMap<>(subTasks);
    }

    @Override
    public void delAllSubTasks() {
        if (!subTasks.isEmpty()) {
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
        Epic tempEpic = epics.get(subTask.getIdEpic());
        tempEpic.addList(subTask.getId());
        checkStatus(epics.get(subTask.getIdEpic()));
    }

    @Override
    public void createEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateTask(Task newTask) {
        if (tasks.containsKey(newTask.getId())) {
            tasks.put(newTask.getId(), newTask);
        }
    }

    @Override
    public void updateSubTask(SubTask newTask) {
        if (subTasks.containsKey(newTask.getId())) {
            subTasks.put(newTask.getId(), newTask);
            checkStatus(epics.get(newTask.getIdEpic()));
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        if (epics.containsKey(newEpic.getId())) {
            epics.put(newEpic.getId(), newEpic);
        }
    }

    @Override
    public boolean dellEpicById(int id) {
        if (epics.containsKey(id)) {
            List<Integer> listId = epics.get(id).getSubTasksList();
            for (Integer subTaskId : listId) {
                subTasks.remove(subTaskId);
            }
            epics.remove(id);
        } else {
            return false;
        }
        return true;
    }

    public boolean dellSubTaskById(int id) {
        if (subTasks.containsKey(id)) {
            int idEpic = subTasks.get(id).getIdEpic();
            List<Integer> list = epics.get(idEpic).getSubTasksList();
            for (Integer subTaskId : list) {
                if (subTaskId == id){
                    list.remove(subTaskId);
                }
            }
            subTasks.remove(id);
            checkStatus(epics.get(idEpic));
        } else {
            return false;
        }
        return true;
    }

    public boolean dellTaskById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else {
            return false;
        }
        return true;
    }

    @Override
    public List<SubTask> getSubtasksByEpicId(int epicID) {
        List<Integer> list = epics.get(epicID).getSubTasksList();
        List<SubTask> subTasksList = new ArrayList<>();
        for (Integer subTuskId : list) {
            subTasksList.add(subTasks.get(subTuskId));
        }
        return subTasksList;
    }

    private void checkStatus(Epic epic) {
        if (!epic.getSubTasksList().isEmpty()) {
            List<Integer> subTasksList = epic.getSubTasksList();
            int countNew = 0;
            int countDone = 0;
            for (int i = 0; i < subTasksList.size(); i++) {
                Task task = subTasks.get(subTasksList.get(i));
                switch (task.getStatus()) {
                    case NEW:
                        countNew++;
                        break;
                    case DONE:
                        countDone++;
                        break;
                }
            }
            if (countNew >= 1) {
                if (countDone >= 1) {
                    epic.setStatus(TaskStatus.IN_PROGRESS);
                } else {
                    epic.setStatus(TaskStatus.NEW);
                }
            } else {
                epic.setStatus(TaskStatus.DONE);
            }
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    }

    @Override
    public SubTask getSubTask(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public Epic getEpic(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Task getTask(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public void dellHistoryTask(int id){
        historyManager.remove(id);
    }
}
