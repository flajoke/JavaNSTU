package Classes.AI;
                                                        // наследуется от Thread -> там он задает поток приоритетов
public abstract class BaseAI extends Thread {           // изменение приоритета потоков, если поток больше, то он выполнится в приоритете после того, у которого поток ниже
    public boolean active = false;
    public int id;
    int SpeedMove = 2;                            // скорость движения
    int TimeMove = 2;                             // время смены направления
    public int moveTime;                        // пройденное время с момента рождения\движения
    public int xB, plusX;                       // хранение новой позиции по x
    public int yB, plusY;                       // хранение новой позиции по y

    @Override
    public void run() {                         // внут функция класса потоков для движения
        try {
            Move();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void Move() throws InterruptedException {    // она будет переопределена в наследниках
    }
    public void SetPriority(int id) {                   // задает приоритет потока, внутренняя функция класса потоков
        setPriority(id);
    }
    public synchronized void setActive(){               // возобновление после wait
        notify();
    }
    public void ClearTime() {                           // очищает внутренний счетчик времени в наследнике (время для изменения направления)
        if(moveTime == (TimeMove + 1)){                 // если он достиг времени движения и на +1 он обнуляется
            moveTime = 0;
        }
    }
}
