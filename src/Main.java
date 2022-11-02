import model.Epic;
import model.SubTask;
import model.Task;
import service.InMemoryTaskManager;
import service.Managers;

import java.util.List;

public class Main {
    public static void main(String[] args) {


        InMemoryTaskManager manager = (InMemoryTaskManager) Managers.getDefault();


        manager.createEpic(new Epic("model.Epic name2", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name1", "Description epic1", manager.generatingId()));
        manager.createSubTask(new SubTask("Subtask name1", "Description subtask1", manager.generatingId(), 2));
        manager.createSubTask(new SubTask("Subtask2", "Description subtask", manager.generatingId(), 2));
        manager.createSubTask(new SubTask("Subtask2", "Description subtask", manager.generatingId(), 1));
        manager.createEpic(new Epic("model.Epic name3", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name4", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name5", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name6", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name7", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name8", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name9", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name10", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name11", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name12", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name13", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("model.Epic name14", "Description epic", manager.generatingId()));
        manager.createTask(new Task("Task1", "DescriptionTask1", manager.generatingId()));

        manager.updateSubTask(new SubTask("new Name", "new description", 3, 2));

        System.out.println("Обращаемся к таскам");
        for (int i = 0; i < 12; i++) {
            System.out.println(manager.getEpic(i));
            System.out.println(manager.getSubTask(i));
        }

        System.out.println("Печатаем историю обращений");
        List<Task> list = manager.getHistory();
        for (Task task : list) {
            System.out.println(task);
        }


    }
}
