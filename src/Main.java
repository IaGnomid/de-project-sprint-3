import java.util.List;

public class Main {
    public static void main(String[] args) {


InMemoryTaskManager manager = Managers.getDefault();
InMemoryHistoryManager historyManager = Managers.getDefaultHistory();




manager.createEpic(new Epic("Epic name2", "Description epic", manager.generatingId()));
manager.createEpic(new Epic("Epic name1","Description epic1", manager.generatingId()));
manager.createSubTask(new SubTask("Subtask name1","Description subtask1", manager.generatingId(), 2));
manager.createSubTask(new SubTask("Subtask2","Description subtask", manager.generatingId(), 2));
manager.createSubTask(new SubTask("Subtask2","Description subtask", manager.generatingId(), 1));
manager.createEpic(new Epic("Epic name3", "Description epic", manager.generatingId()));
manager.createEpic(new Epic("Epic name4", "Description epic", manager.generatingId()));
manager.createEpic(new Epic("Epic name5", "Description epic", manager.generatingId()));
manager.createEpic(new Epic("Epic name6", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name7", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name8", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name9", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name10", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name11", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name12", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name13", "Description epic", manager.generatingId()));
        manager.createEpic(new Epic("Epic name14", "Description epic", manager.generatingId()));

        //System.out.println(manager.getAllEpics());
        //System.out.println(manager.getAllSubTasks());

        manager.updateSubTask(new SubTask("new Name", "new description", 3, 2), TaskStatus.DONE);

        //System.out.println(manager.getAllEpics());
        System.out.println("Обращаемся к таскам через метод getById");
            for(int i = 0; i < 12; i++){
                System.out.println(manager.getById(i));
            }

        System.out.println("Печатаем историю обращений");
        List<Task> list = historyManager.getHistory();
        for (Task task : list) {
            System.out.println(task);
        }



    }
}
