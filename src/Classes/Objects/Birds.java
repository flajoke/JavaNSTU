package Classes.Objects;

import Interface.IBehavior;
import javax.swing.*;
import java.util.Random;

public abstract class Birds extends JFrame implements IBehavior {
    public ImageIcon imageObject;           // Изобраение объекта
    private String PathImage;               // Путь к изображению объекта
    public int x;
    public int y;
    public int id;
    public int bornTime;                    // время рождения объекта
    public int timeLive;                    // время которое должен прожить объект
    public int timeObject;                  // время прожитое объектом
    public int ValueObject = 0;             // Подсчет количество объектов
    public int Times;                       // Время создания объекта
    public int Probability;                 // Вероятность создания объекта
    public JLabel LayObject;                // Слой объекта который ложится на экран
    public boolean activeObject = false;
    public Birds(String path, int t, int p, int TL) {     // конструктор с параметрами объекта
        this.PathImage = path;
        this.Times = t;
        this.Probability = p;
        this.timeLive = TL;
        imageObject = new ImageIcon(this.PathImage);
        LayObject = new JLabel(imageObject);
    }
    public boolean Generate() {                      // генерация вероятности
        Random random = new Random();
        int a = random.nextInt(10)+1;
        if(a <= Probability){
            activeObject = true;
            return true;
        } else { return false; }
    }
    public void SetPosition(int x, int y) {          // задача позиции
        this.x = x;
        this.y = y;
        LayObject.setBounds(this.x, this.y, imageObject.getIconWidth(), imageObject.getIconHeight());
    }
    public int GetX(){
        return this.x;
    }
    public int GetY(){
        return this.y;
    }
}
