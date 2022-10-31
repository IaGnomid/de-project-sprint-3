import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private final static int HISTORY_SIZE = 10;
    private List<Task> history = new ArrayList<>();

    public List<Task> getHistory() {
        return List.copyOf(history);
    }

    @Override
    public void addHistory(Task task){
        if(!(task == null)){
            history.add(task);
            if (history.size() > HISTORY_SIZE){
                history.remove(0);
            }
        }
    }
}
