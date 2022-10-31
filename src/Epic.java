import java.util.ArrayList;

public class Epic extends Task{
    private ArrayList<Integer> subTasksList = new ArrayList<>();

    public void addList(int id){
        subTasksList.add(id);
    }

    public void clearList(){
        subTasksList.clear();
    }

    public ArrayList<Integer> getSubTasksList() {
        return subTasksList;
    }
    public Epic(String name, String description, int id) {
        super(name, description, id);
    }
    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                '}';
    }
}
