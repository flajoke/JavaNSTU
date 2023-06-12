package Classes.AI;

import Classes.Objects.chick;
import java.util.LinkedList;

public class ChickAI extends BaseAI {
    LinkedList<chick> chicks;
    public ChickAI(LinkedList<chick> chicks1) {
        active = false;                                 // булево для движения
        id = 1;                                         // приоритет потока
        setPriority(id);
        plusX = SpeedMove;                              // шаг движения
        plusY = SpeedMove;
        moveTime = 0;                                   // начальное время для таймера
        chicks = chicks1;                               // чтобы список с класса приравнялся к классу из хабитата
    }
    @Override
    public void Move() throws InterruptedException {
        synchronized (this) {
            for(int a = 0;; a++) {
                if (active != true) {
                    wait();
                }
                if (chicks.get(a).activeObject == true) {
                    sleep(22);
                    xB = chicks.get(a).GetX() + plusX;      // шаг движения на plusX
                    yB = chicks.get(a).GetY() + plusY;
                    chicks.get(a).SetPosition(xB, yB);
                }
                if (a == 49) {                           // возврат в начало списка
                    a = -1;
                }
            }
        }
    }
    public void SetList(LinkedList<chick> chicks2) {        // для обновления списка нашего класса
        chicks = chicks2;
    }
}
