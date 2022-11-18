package service;

import model.Task;
import service.HistoryManager;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    Map<Integer, Node> nodeMap = new HashMap<>();
    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    public void add(Task task){
        if(nodeMap.containsKey(task.getId())){
            customLinkedList.removeNode(nodeMap.get(task.getId()));
        }
            Node newNode = customLinkedList.linkLast(task);
            nodeMap.put(task.getId(), newNode);

    }
    public List<Task> getHistory(){
        //List<Task> listHistory = new ArrayList<>();
        return customLinkedList.getTasks();

    }
    @Override
    public void remove(int id) {
        customLinkedList.removeNode(nodeMap.get(id));
    }
}
class CustomLinkedList<T>{
    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;
    public Node linkLast(Task element){
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(element, tail, null);

        tail = newNode;
        if (oldTail == null)
            head = newNode;
        else
            oldTail.next = newNode;
            newNode.prev = oldTail;
            newNode.next = null;
        size++;
        return newNode;
        //метод add, порядок которого должна запомнить программа
    }
    public List<Task> getTasks(){
        List<Task> list = new ArrayList<>();
        Node<Task> temp = head;
        while(temp != null){
            list.add(temp.data);
            temp = temp.next;
        }
        return list;
    }

    public void removeFirst(){
        if(head.next == null){
            tail = null;
        }
        else {
            head.next.prev = null;
            head = head.next;
        }
    }

    public void removeLast(){
        if(head.next == null){
            head = null;
        }else{
            tail.prev.next = null;
            tail = tail.prev;
        }
    }


    public void removeNode(Node key){
        Node cur = head;
        while(cur.data != key.data){
            cur = cur.next;
            if(cur == null){
                return;
            }
        }
        if(key.data == head.data){
            removeFirst();
        }else if (key.data == tail.data){
            removeLast();
        }else {
            cur.next.prev = cur.prev;
            cur.prev.next = cur.next;
        }
        size--;
    }
}
