package Classes.AI;

import Classes.Objects.bird;
import java.util.LinkedList;

public class BirdAI extends BaseAI {
    LinkedList<bird> birds;
    public BirdAI(LinkedList<bird> birds1) {
        active = false;
        id = 1;
        setPriority(id);
        plusX = SpeedMove;
        plusY = SpeedMove;
        moveTime = 0;
        birds = birds1;
    }
    @Override
    public void Move() throws InterruptedException {
        synchronized (this) {
            for(int a = 0;; a++) {
                if (active != true) {
                    wait();
                }
                if (birds.get(a).activeObject == true) {
                    sleep(22);
                    xB = birds.get(a).GetX() + plusX;
                    yB = birds.get(a).GetY() + plusY;
                    birds.get(a).SetPosition(xB, yB);
                }
                if (a == 49) {                               //возврат в начало списка
                    a = -1;
                }
            }
        }
    }
    public void SetList(LinkedList<bird> birds2){
        birds = birds2;
    }
}