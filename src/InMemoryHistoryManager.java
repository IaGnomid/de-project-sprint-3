import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static List<Task> history = new ArrayList<>();

    @Override
    public List<Task> getHistory(){
        return history;
    }

    @Override
    public void addHistory(Task task){
        history.add(task);
        if (history.size() > 10){
            history.remove(0);
        }
    }
}
