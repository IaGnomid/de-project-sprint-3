public class Main {
    public static void main(String[] args) {
Manager manager = new Manager();
manager.createEpic(new Epic("Epic1", "Description epic", manager.generatingId()));
manager.createEpic(new Epic("Epic name1","Description epic1", manager.generatingId()));
manager.createSubTask(new SubTask("Subtask name1","Description subtask1", manager.generatingId(), 2));
manager.createSubTask(new SubTask("Subtask2","Description subtask", manager.generatingId(), 2));
manager.createSubTask(new SubTask("Subtask2","Description subtask", manager.generatingId(), 1));

        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubTasks());

        manager.updateSubTask(new SubTask("new Name", "new description", 3, 2), "DONE");

        System.out.println(manager.getAllEpics());


    }
}
