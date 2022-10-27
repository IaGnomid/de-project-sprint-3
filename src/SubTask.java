public class SubTask extends Task{
    int idEpic;

    public SubTask(String name, String description, int id, int epicID) {
        super(name, description, id);
        this.idEpic = epicID;
    }
    public int getIdEpic() {
        return idEpic;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", status='" + getStatus() + '\'' +
                "idEpic=" + idEpic +
                '}';
    }
}
