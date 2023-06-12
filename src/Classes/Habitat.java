package Classes;

import Classes.AI.BaseAI;
import Classes.AI.BirdAI;
import Classes.AI.ChickAI;
import Classes.Objects.bird;
import Classes.Objects.chick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.*;

public class Habitat implements KeyListener {
    // окно приложения
    private JFrame Window;                      // класс для создания окна
    // деление окна на 2 части
    private JPanel firstPanel;                  // Первая рабочая область
    private JPanel secondPanel;                 // Вторая рабочая область
    // текст статистики
    private JLabel LabelTime;                   // Информация о прошедшем времени
    private JLabel LabelChick;                  // информация для вывод о количестве созданных птенцов
    private JLabel LabelBird;                   // Информация для вывода о количестве созданных взрослых
    // списки объектов
    private LinkedList<bird> Bird;              // Создаем список объектов наследника
    private LinkedList<chick> Chick;            // Создаем список объектов наследника
    //
    private TreeSet<Integer> idBird;            // список с идедификаторами взрослых
    private TreeSet<Integer> idChick;           // список с идендификаторами птенцов
    //
    private HashMap<Integer, bird> bornLiveBird;    // список с временем появления взрослых
    private HashMap<Integer, chick> bornLiveChick;  // список со временем появления птенцов
    // кнопки для включения выключения симуляции
    private JButton buttonActiveApp;            // кнопка для включения симуляции
    private JButton buttonDeactiveApp;          // кнопка для включения симуляции
    // радиокнопки для активации статистики
    private JRadioButton buttonActiveInfo;      // Радиокнопка активации статистики
    private JRadioButton buttonDeactiveInfo;    // Радиокнопка деактивации статистики
    // чекбокс для дилогового окна
    private JCheckBox checkBoxActiveDialog;     // Кнопка для включения/отключения диалогового окна
    // текст для диалогового окна
    private TextArea text;                      // текст для для дилогового окна
    // текстовые поля для ввода времени
    private JTextField periodBird;              // текстовое поле для периода создания объекта
    private JTextField periodChick;             // текстовое поле для периода создания объекта
    //текстовые поля для ввода времени
    private JTextField TimeLiveBird;            //текстовое поле для времени жизни объекта
    private JTextField TimeLiveChick;           //текстовоее поле для время жизни объекта
    // кнопка submit для обработки информации
    private JButton submit;                     // кнопка для обновления параметров объекта
    // комбобоксы для вероятности
    private JComboBox<String> setProbabilityBird;   // комбобокс для вероятности взрослых
    private JComboBox<String> setProbabilityChick;  // комбобокс для вероятнсти птенцов
    // главное меню
    // сам менюбар
    private JMenuBar menuBar;                   // главное меня  с дублированием пользовательского интерфейса
    // вкладка и вложеные вкладки главного
    private JMenu main;                        // главное содержит в себе запуск и остановку
    private JMenuItem statrItem;                // кнопка главного меню старт симуляции
    private JMenuItem stopItem ;                // кнопка главного меню остановка симуляции
    // вкладка и вложенные вкладки статитстики
    private JMenu info;
    private JMenuItem ItemInfoActive;
    private JMenuItem ItemInfoDeactive;
    // вкладка и вложенные вкладки управления диологовым окном
    private JMenu control;
    private JMenuItem ItemActiveDialog;
    //вызов дилогового окна с со списком объектов
    private JButton showList;                   //диалоговое окно со списком объектов


    //кнопки управления потокамии анимацией
    private JComboBox ButtonSetPriorityBird;        // приортет потока для взрослых
    private JComboBox ButtonSetPriorityChick;       // приоритет потока для птенцов
    private JButton buttonActiveAnim;               // кнопка активации анимации
    private JButton buttonStopAnim;                 // кнока деактивации анимации


    // булевы переменные
    private boolean activeApp = false;          // Переменная активации приложения
    private boolean stopSim = false;            // Переменая оставки приложения
    private boolean activeInfo = false;         // Переменная активации статистики
    private boolean Delete = false;             // Переменная отчистки списков
    private boolean activeDialog = false;       // Активация диалогового окна
    private boolean showDialogActive = false;   // активация диалогового окна
    // целочисленные переменные
    private int timeBird = 2;                   // переменная которая хранит в себе время объекта
    private int timeChick = 2;                  // переменная которая хранит в себе время объекта
    private int probabilityBird = 10;           // переменная хранит в себе верятность создания взрослых
    private int probabilityChick = 10;          // переменная хранит в себе вероятность создания птенцов
    private int timeLiveBird = 30;               //переменная храенения времени жизни взрослого
    private int timeLiveChick = 30;              //переменная хранит время жизни птенца


    private BirdAI birdAI;
    private ChickAI chickAI;

    private SQL sql;
    private JMenu database;         // вкладка сохранения в БД
    private JMenuItem loadDB;       // загрузка БД
    private JMenuItem saveDB;       // сохранение БД

    public void CreateWindow() throws InterruptedException  // Метод для создания окна
    {
        // раздел создания окна
        Window = new JFrame();                  // Создаем новое окно
        Window.setTitle("Lab 3");               // задача названия
        Window.setSize(1920, 1000); // задача размеров окна
        Window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);      // завершение программы по закрытию окна
        //Window.setExtendedState(JFrame.MAXIMIZED_BOTH);             // Открыть окно в развернутом режиме
        MainMenuCreate();
        // раздел создания 2 разделов окна
        firstPanel = new JPanel();              // Создание нового объекта окна статистики
        secondPanel = new JPanel();             // Создание нового объекта окно изображений
        firstPanel.setLayout(null);             // Сделать свободное наложение блоков 1на1
        // secondPanel.setBackground(Color.red);//цвет залицки второй области
        firstPanel.setBounds(0,0,300,1080);         // позиция первой области
        secondPanel.setBounds(300,0,1720,1080);     // позиция второй области
        Window.add(firstPanel);
        Window.add(secondPanel);
        // раздел интерфейса статистики
        // Лэйблы или контейнеры
        LabelTime = new JLabel("Прошло времени: " );                // Информация о прошедшем времени
        LabelBird = new JLabel("Кол-во В: " );                      // Информация о количестве взрослых
        LabelChick = new JLabel("Кол-во Ц: " );                     // Информация о количестве птенцов
        //
        Window.addKeyListener(this);             // Подключение обработки наэатия клавиш
        Window.setVisible(true);                    // отображение окна
    }

    public Habitat() throws InterruptedException                    // Конструктор класса Habitat
    {
        CreateWindow();
        CreateObject(50);
        sql = new SQL();
        birdAI = new BirdAI(Bird);          // создает новый объект класса BirdAI, который используется для управления объектом Bird
        birdAI.start();
        chickAI = new ChickAI(Chick);
        chickAI.start();
        CreateButton();
        WorkWindow();
    }
    public void WorkWindow() throws InterruptedException            // Метод для работы с параметром
    {
        for(int time=0, i=0, interval1=0,interval2=0;;){
            if(i >= 49){
                i = 0;
            }
            if(time == 0 && Delete == false){               // при рестарте приложения запускаем поток по новой
                birdAI.setActive();
                chickAI.setActive();
            }
            if(activeApp == true) {
                if(chickAI.moveTime == 2) {
                    SetFinalPos();
                    chickAI.moveTime = 0;
                }
                chickAI.moveTime++;
                chickAI.ClearTime();
                //

                if(sql.act == true) {
                    sql.save(Bird, Chick);
                }
                if(sql.act2 == true) {
                    sql.load(Bird, Chick);
                }

                birdAI.moveTime++;              // расчет времени смены позиции
                birdAI.ClearTime();             // обнуление времени секундомера
                time++;
                interval1++;
                interval2++;
                if(Delete == true){
                    CreateObject(50);
                    birdAI.SetList(Bird);       // перезаписываем объекты из наших других классов
                    chickAI.SetList(Chick);
                    birdAI.setActive();
                    chickAI.setActive();
                    Delete = false;
                }
                if(Bird.get(0).ValueObject > 15 || Chick.get(0).ValueObject > 15){
                    activeApp = false;
                    stopSim = true;
                    Delete = true;
                    continue;
                }
                if (interval1 == Bird.get(i).Times) {               // интервал время рождения = интервал
                    Window.add(secondPanel);
                    if (Bird.get(i).Generate() == true)             // Проверка на попадание объекта Bird под вероятность
                    {
                        Random rand = new Random();
                        Bird.get(i).SetPosition(rand.nextInt(1280) + 301, rand.nextInt(800) + 1);
                        secondPanel.add(Bird.get(i).LayObject);
                        Bird.get(0).ValueObject++;
                        Bird.get(i).id = rand.nextInt(50)+1;
                        Bird.get(i).bornTime = time;
                        idBird.add(Bird.get(i).id);
                        bornLiveBird.put((Bird.get(0).ValueObject-1), Bird.get(i));     // добавляем в карту HashMap
                        i++;
                    }
                    interval1 = 0;
                }
                if (interval2 == Chick.get(i).Times){
                    if (Chick.get(i).Generate() == true )           //проверка на попадание объекта Chick под вероятность
                    {
                        Random rand = new Random();
                        Chick.get(i).SetPosition(rand.nextInt(1280) + 301, rand.nextInt(800) + 1);
                        secondPanel.add(Chick.get(i).LayObject);
                        Chick.get(0).ValueObject++;
                        Chick.get(i).id = rand.nextInt(50)+1;
                        Chick.get(i).bornTime = time;
                        idChick.add(Chick.get(i).id);                                       // добавляем в коллекцию TreeSet
                        bornLiveChick.put((Chick.get(0).ValueObject-1), Chick.get(i));      // добавляем в карту HashMap
                        i++;
                    }
                    interval2 = 0;
                }
                if (activeInfo == true) {
                    StatisticsOut(time, Bird.get(0).ValueObject, Chick.get(0).ValueObject);
                }
                for (int ttime = 0; ttime < 50; ttime++) {          // if time this object = time live AND active => delete
                    if (Bird.get(ttime).timeObject == Bird.get(ttime).timeLive && Bird.get(ttime).activeObject == true) {
                        Bird.get(0).ValueObject--;
                        secondPanel.remove(Bird.get(ttime).LayObject);
                        Bird.get(ttime).activeObject = false;
                    } else if (Bird.get(ttime).activeObject == true) {
                        Bird.get(ttime).timeObject++;               // else time + 1
                    }
                    if (Chick.get(ttime).timeObject == Chick.get(ttime).timeLive && Chick.get(ttime).activeObject == true) {
                        Chick.get(0).ValueObject--;
                        secondPanel.remove(Chick.get(ttime).LayObject);
                        Chick.get(ttime).activeObject = false;
                    } else if (Chick.get(ttime).activeObject == true) {
                        Chick.get(ttime).timeObject++;
                    }
                }
            }
            if (stopSim == true) {
                if (activeDialog == true) {
                    Object[] messageButton = {"Ok", "Cancel"};
                    text = new TextArea();
                    text.setText("Кол-во взрослых: " + Bird.get(0).ValueObject + ", Кол-во птенцов: " + Chick.get(0).ValueObject + ", Прошло времени: " +time+".");
                    int pane = showOptionDialog(Window, text, "Info", YES_NO_OPTION, QUESTION_MESSAGE, null, messageButton, messageButton[0]);
                    if (pane == JOptionPane.YES_OPTION) {
                        Delete = true;
                    } else if (pane == NO_OPTION) {
                        activeApp = true;
                        activeInfo = true;
                    }
                }
                if (activeDialog == false && stopSim == true){
                    Delete = true;
                }
                if (Delete == true) {
                    CreateObject(50);
                    for (int k = 0; k < 50; k++) {
                        secondPanel.removeAll();
                        Window.remove(secondPanel);
                        Window.repaint();
                    }
                    i=0;
                    time =0;
                    interval1=0;
                    interval2=0;
                    Thread.sleep(4000);
                    Bird.clear();
                    Chick.clear();
                }
                stopSim = false;
                Delete = true;
            }
            UpDateScreen();
            WorkMenu();
            WorkButton();
            if(showDialogActive == true){
                ShowList();
            }
            Thread.sleep(1000);
            firstPanel.remove(LabelTime);       // clear info in the left window
            firstPanel.remove(LabelBird);
            firstPanel.remove(LabelChick);
            Window.repaint();
        }
    }
    public void CreateObject(int ValueObject)                       //метод создания объектов
    {
        Bird = new LinkedList<>();
        Chick = new LinkedList<>();
        idBird = new TreeSet<>();
        idChick = new TreeSet<>();
        bornLiveBird = new HashMap<>();
        bornLiveChick = new HashMap<>();
        for(int i=0; i<ValueObject; i++) {
            Bird.add(i, new bird("img/bird.jpg", timeBird, probabilityBird, timeLiveBird));
            Chick.add(i, new chick("img/chick.jpg", timeChick, probabilityChick, timeLiveChick));
        }
    }
    public void StatisticsOut(int time,int fInf,int sInf)           // Вывод статистики на экран
    {
        Font arial = new Font("arial",Font.BOLD,20);
        // работа с контейнером
        LabelTime.setText("Время: " + time);
        LabelBird.setText("Кол-во В: " + fInf);
        LabelChick.setText("Кол-во Ц: " + sInf);
        //
        LabelTime.setForeground(Color.green);                           // задача цвета
        LabelBird.setForeground(Color.blue);                            // задача цвета
        LabelChick.setForeground(Color.pink);                           // задача цвета
        //
        LabelTime.setFont(arial);                                       // задача шрифта
        LabelBird.setFont(arial);                                       // задача шрифта
        LabelChick.setFont(arial);                                      // задача шрифта
        //
        LabelTime.setBounds(10, 10, 150, 30);           // позицировние
        LabelBird.setBounds(10, 30, 150, 30);           // позицирования
        LabelChick.setBounds(10, 50, 150, 30);          // позицирование
        //
        firstPanel.add(LabelTime);                                      // Добавляем метку на контейнер
        firstPanel.add(LabelBird);                                      // Добавляем метку на контейнер
        firstPanel.add(LabelChick);                                     // Добавляем метку на контейнер
        //
        Window.repaint();
    }
    public void CreateButton()                                          // Метод для создания кнопок и отрисвки в окне
    {
        Font arial = new Font("arial",Font.BOLD,15);

        buttonActiveAnim = new JButton();
        buttonStopAnim = new JButton();
        buttonActiveAnim.setFocusable(false);
        buttonActiveAnim.setFont(arial);
        buttonStopAnim.setFocusable(false);
        buttonStopAnim.setFont(arial);
        //задача приоритета потока
        String[] itemsPriority = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ButtonSetPriorityBird = new JComboBox<>(itemsPriority);
        ButtonSetPriorityChick = new JComboBox<>(itemsPriority);


        //Создание кнопок
        showList = new JButton();
        showList.setFocusable(false);
        //Текстовые поля для времени жизни
        TimeLiveBird = new JTextField();
        TimeLiveChick = new JTextField();
        // комбобоксы
        String[] items = {"10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%", "100%"};
        setProbabilityBird = new JComboBox<String>(items);
        setProbabilityChick = new JComboBox<String>(items);
        // текстовые поля
        periodBird = new JTextField();
        periodChick = new JTextField();
        // кнопка submit
        submit = new JButton();
        submit.setFocusable(false);                                 // снимаем приоритет
        // чекБокс для дилогового окна
        checkBoxActiveDialog = new JCheckBox();
        checkBoxActiveDialog.setFocusable(false);                   // снимаем приоритет
        // радиокнопки
        buttonActiveInfo = new JRadioButton();
        buttonDeactiveInfo = new JRadioButton();
        buttonActiveInfo.setFocusable(false);                       // снимаем приоритет
        buttonDeactiveInfo.setFocusable(false);                     // снимаем приоритет
        // кнопки старта и останвки симуляции
        buttonActiveApp = new JButton();                            // кнопка старта
        buttonDeactiveApp = new JButton();                          // кнопка закончить
        buttonActiveApp.setFocusable(false);                        // снимаем приоритет
        buttonDeactiveApp.setFocusable(false);                      // снимаем приоритет
        // текст для элементов


        buttonActiveAnim.setText("Atctive Animation");
        buttonStopAnim.setText("Deactive Animation");


        showList.setText("Show List");
        TimeLiveBird.setText(String.valueOf(Bird.get(0).timeLive));     // целый -> стринг и в текстовое поле
        TimeLiveChick.setText(String.valueOf(Chick.get(0).timeLive));
        periodBird.setText(String.valueOf(Bird.get(0).Times));
        periodChick.setText(String.valueOf((Chick.get(0).Times)));
        //
        submit.setText("Submit");
        //
        checkBoxActiveDialog.setText("Active/Deactive Dialog Window");      // кнопка активации деактивации диалогового окна
        //
        buttonActiveInfo.setText("Active info");                            // кнопка активации статистики
        buttonDeactiveInfo.setText("Deactive info");                        // кнопка деактивации статистики
        //
        buttonActiveApp.setText("Start");                                   // кнопка старта
        buttonDeactiveApp.setText("End");                                   // кнопка остановки
        // Положение кнопок


        buttonActiveAnim.setBounds(10,240,100,50);
        buttonStopAnim.setBounds(110,240,100,50);
        //
        ButtonSetPriorityBird.setBounds(10,300,100,30);
        ButtonSetPriorityChick.setBounds(110,300,100,30);


        showList.setBounds(40, 360, 100, 50);
        //
        TimeLiveBird.setBounds(40, 420, 100, 30);
        TimeLiveChick.setBounds(40, 460, 100, 30);
        setProbabilityBird.setBounds(40,500,100,30);
        setProbabilityChick.setBounds(40,540,100,30);
        //
        periodBird.setBounds(40,580,100,30);
        periodChick.setBounds(40,610,100,30);
        //
        submit.setBounds(40,650,150,50);                    // поменять размер кнопки
        submit.setForeground(Color.blue);                                       // цвет кнопки
        submit.setFont(arial);                                                  // задача шрифта
        //
        checkBoxActiveDialog.setBounds(40,720,200,30);
        //
        buttonActiveInfo.setBounds(40,760,100,30);
        buttonDeactiveInfo.setBounds(140,760,100,30);
        //
        buttonActiveApp.setBounds(40,820,100,50);
        buttonDeactiveApp.setBounds(140,820,100,50);
        //
        buttonActiveApp.setFont(arial);
        buttonActiveApp.setForeground(Color.green);
        // активаность элементов


        ButtonSetPriorityBird.setEnabled(false);
        ButtonSetPriorityBird.setSelectedIndex(birdAI.id-1);
        ButtonSetPriorityChick.setSelectedIndex(chickAI.id-1);
        ButtonSetPriorityChick.setEnabled(false);


        TimeLiveBird.setEnabled(false);                     // неактивное состояние
        TimeLiveChick.setEnabled(false);
        periodBird.setEnabled(false);
        periodChick.setEnabled(false);
        setProbabilityBird.setSelectedIndex((probabilityBird-1));
        setProbabilityChick.setSelectedIndex((probabilityChick-1));
        setProbabilityBird.setEnabled(false);
        setProbabilityChick.setEnabled(false);
        // отрисовка кнопок
        //
        firstPanel.add(buttonActiveAnim);
        firstPanel.add(buttonStopAnim);
        //
        firstPanel.add(ButtonSetPriorityBird);
        firstPanel.add(ButtonSetPriorityChick);
        //
        firstPanel.add(showList);
        //
        firstPanel.add(TimeLiveBird);
        firstPanel.add(TimeLiveChick);
        //
        firstPanel.add(setProbabilityBird);
        firstPanel.add(setProbabilityChick);
        //
        firstPanel.add(periodBird);
        firstPanel.add(periodChick);
        //
        firstPanel.add(submit);
        //
        firstPanel.add(checkBoxActiveDialog);
        //
        firstPanel.add(buttonActiveInfo);
        firstPanel.add(buttonDeactiveInfo);
        //
        firstPanel.add(buttonActiveApp);
        firstPanel.add(buttonDeactiveApp);
    }

    public void WorkButton()                //метод обратботки событий кнопок
    {


        //обработка кнопок активации\деактивации анимации
        buttonActiveAnim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeApp == true && birdAI.active == false && chickAI.active == false) {
                    birdAI.active = true;
                    chickAI.active = true;
                    birdAI.setActive();
                    chickAI.setActive();
                }
            }
        });

        buttonStopAnim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeApp==true && birdAI.active == true && chickAI.active == true) {
                    birdAI.active = false;
                    chickAI.active = false;
                }
            }
        });
        //Обработка кнопки ShowList
        showList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDialogActive = true;
            }
        });
        //Обработка кнопки Submit и текстовых полей
        submit.addActionListener(new ActionListener() { // переопределяем методы и пишем свой код
            @Override
            public void actionPerformed(ActionEvent e) {
                if(periodBird.isEnabled() == false && periodChick.isEnabled() == false) {


                    ButtonSetPriorityBird.setEnabled(true);
                    ButtonSetPriorityChick.setEnabled(true);


                    periodBird.setEnabled(true);
                    periodChick.setEnabled(true);
                    setProbabilityBird.setEnabled(true);
                    setProbabilityChick.setEnabled(true);
                    TimeLiveBird.setEnabled(true);
                    TimeLiveChick.setEnabled(true);
                } else if(periodBird.isEnabled() == true && periodChick.isEnabled() == true){


                    int idPriorityBird = ButtonSetPriorityBird.getSelectedIndex();
                    int idPriorityChick = ButtonSetPriorityChick.getSelectedIndex();


                    int timeB = Integer.parseInt(periodBird.getText());
                    int timeC = Integer.parseInt(periodChick.getText());
                    int probablilityB = setProbabilityBird.getSelectedIndex();
                    int probablilityC = setProbabilityChick.getSelectedIndex();
                    int TimeLiveB = Integer.parseInt(TimeLiveBird.getText());
                    int TimeLiveC = Integer.parseInt(TimeLiveChick.getText());
                    if(timeB <= 0 || timeC <= 0 || timeB > 15 || timeC > 15) {              // период появления
                        JOptionPane.showMessageDialog(Window,"Нельзя ввести эти параметры");
                        periodBird.setText(String.valueOf(Bird.get(0).Times));
                        periodChick.setText(String.valueOf((Chick.get(0).Times)));
                        activeApp = false;
                        stopSim = true;
                        Delete = true;
                    } else {


                        birdAI.SetPriority(idPriorityBird+1);           // задача приоритета потока
                        chickAI.SetPriority(idPriorityChick+1);


                        timeBird = timeB;
                        timeChick = timeC;
                        probabilityBird = probablilityB + 1;
                        probabilityChick= probablilityC + 1;
                        timeLiveBird = TimeLiveB;
                        timeLiveChick = TimeLiveC;
                        CreateObject(50);

                        birdAI.SetList(Bird);
                        chickAI.SetList(Chick);
                    }


                    ButtonSetPriorityBird.setEnabled(false);
                    ButtonSetPriorityChick.setEnabled(false);


                    periodBird.setEnabled(false);
                    periodChick.setEnabled(false);
                    setProbabilityBird.setEnabled(false);
                    setProbabilityChick.setEnabled(false);
                    TimeLiveBird.setEnabled(false);
                    TimeLiveChick.setEnabled(false);
                }
            }
        });
        //Обработка CheckBox для дилогового окна
        if(activeDialog == true) {
            checkBoxActiveDialog.setSelected(true);
        } else if(activeDialog == false) {
            checkBoxActiveDialog.setSelected(false);
        }
        checkBoxActiveDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxActiveDialog.isSelected()){
                    activeDialog = true;
                } else {
                    activeDialog = false;
                }
            }
        });
        // обработка радиокнопок
        ButtonGroup radioGroup = new ButtonGroup();//Создаем группу что бы связать радиокнопки
        radioGroup.add(buttonActiveInfo);
        radioGroup.add(buttonDeactiveInfo);
        if(activeInfo == true){
            buttonDeactiveInfo.setEnabled(true);
            buttonActiveInfo.setEnabled(false);
        }
        if(activeInfo == false){
            buttonDeactiveInfo.setEnabled(false);
            buttonActiveInfo.setEnabled(true);
        }
        buttonActiveInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeInfo = true;
            }
        });
        buttonDeactiveInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeInfo = false;
            }
        });
        // Обработка кнопок Start и Stop
        if(activeApp == false){
            buttonDeactiveApp.setEnabled(false);
            buttonActiveApp.setEnabled(true);
        } else if(activeApp == true){
            buttonDeactiveApp.setEnabled(true);
            buttonActiveApp.setEnabled(false);
        }
        buttonActiveApp.addActionListener(new ActionListener()  // проверка нажатия на клавишу активации симуляции
        {
            @Override
            public void actionPerformed(ActionEvent e) {        // понятие процесса и потока, их различия. Общая разделяемая память.
                activeApp = true;


                birdAI.active = true;
                chickAI.active = true;


                stopSim = false;
            }
        });
        buttonDeactiveApp.addActionListener(new ActionListener() // проверка нажатия на клавишу активации симуляции
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeApp = false;


                birdAI.active= false;
                chickAI.active = false;


                stopSim = true;
            }
        });
    }

    public void MainMenuCreate()//метод создания главной панели меню
    {
        menuBar = new JMenuBar();

        database = new JMenu("Data Base");
        loadDB = new JMenuItem("Load");
        saveDB = new JMenuItem("Save");

        loadDB.setEnabled(true);
        saveDB.setEnabled(true);

        database.add(loadDB);
        database.addSeparator();
        database.add(saveDB);

        // Создание вкладок
        // вкладка главное
        main = new JMenu("Main");               // главное содержит в себе запуск и остановку
        statrItem = new JMenuItem("Start");
        stopItem = new JMenuItem("Stop");
        statrItem.setEnabled(true);
        stopItem.setEnabled(false);
        main.add(statrItem);
        main.addSeparator();
        main.add(stopItem);
        // вкладка Info
        info = new JMenu("Info");               // статистика
        ItemInfoActive = new JMenuItem("Active");
        ItemInfoDeactive = new JMenuItem("Deactive");
        info.add(ItemInfoActive);
        info.addSeparator();
        info.add(ItemInfoDeactive);
        // вкладка Control
        control = new JMenu("Window Dialog");
        ItemActiveDialog = new JMenuItem("Active/Deactive Dialog");
        ItemActiveDialog.setSelected(false);
        control.add(ItemActiveDialog);
        // Добавление вкладок в меню

        menuBar.add(database);

        menuBar.add(main);
        menuBar.add(info);
        menuBar.add(control);
        Window.setJMenuBar(menuBar);
    }

    public void WorkMenu()
    {

        saveDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql.act = true;
            }
        });

        loadDB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql.act2 = true;
            }
        });

        // Обработка вкладки main
        if(activeApp == false) {
            stopItem.setEnabled(false);
            statrItem.setEnabled(true);
        } else if(activeApp == true) {
            stopItem.setEnabled(true);
            statrItem.setEnabled(false);
        }
        statrItem.addActionListener(new ActionListener() // вкладка старт
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeApp = true;
                stopSim = false;
            }
        });
        stopItem.addActionListener(new ActionListener() //вкладка стоп
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeApp = false;
                stopSim = true;
            }
        });
        // обработка вклладок info
        if(activeInfo == true) {
            ItemInfoActive.setEnabled(false);
            ItemInfoDeactive.setEnabled(true);
        } else if(activeInfo == false) {
            ItemInfoActive.setEnabled(true);
            ItemInfoDeactive.setEnabled(false);
        }
        ItemInfoActive.addActionListener(new ActionListener()   // вкладка active
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeInfo = true;
            }
        });
        ItemInfoDeactive.addActionListener(new ActionListener() //вкладка deactive
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeInfo = false;
            }
        });
        // обработка вкладки control
        ItemActiveDialog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeDialog == false) {
                    activeDialog = true;
                }
                else if(activeDialog == true) {
                    activeDialog= false;
                }
                if(ItemActiveDialog.isSelected() == false) {
                }
            }
        });
    }

    public void ShowList ()//открытие диалогового со списком объектов
    {
        activeApp = false;                              // надо чтобы можно было закрыть окно
        JDialog dialogShowList = new JDialog();
        dialogShowList.setModal(true);
        JTextArea infoText = new JTextArea();
        dialogShowList.setTitle("List Object");
        dialogShowList.setSize(500, 800);
        dialogShowList.setLocationRelativeTo(null);
        List<JLabel> textInfo = new ArrayList<>();
        int num = 0;
        for (int s1 = 0; s1 < bornLiveBird.size(); s1++) {
            showList.setEnabled(false);
            if (bornLiveBird.get(s1).activeObject == true) {        //
                String infotext = new String();
                int bTime = bornLiveBird.get(s1).bornTime;
                int idO = bornLiveBird.get((s1)).id;
                infotext = " Bird: " + s1 + " id: " + idO + " born time: " + bTime;
                textInfo.add(new JLabel(infotext));
                textInfo.get(num).setBounds(0, num * 10, 300, 15);
                dialogShowList.add(textInfo.get(num));
                num++;
            }
        }
        for (int s1 = 0; s1 < bornLiveChick.size(); s1++) {
            String infotext = new String();
            int bTime = bornLiveChick.get(s1).bornTime;
            int idO = bornLiveChick.get((s1)).id;
            infotext = " Chick: " + s1 + " id: " + idO + " born time: " + bTime;
            textInfo.add(new JLabel(infotext));
            textInfo.get(num).setBounds(0, num * 10, 300, 15);
            dialogShowList.add(textInfo.get(num));
            num++;
            if(s1 == (bornLiveChick.size()-1)) {        // for fixing problem with more space
                textInfo.add(new JLabel());
                textInfo.get((num)).setBounds(0,(num)*10,300,15);
                dialogShowList.add(textInfo.get(num));
            }
        }
        dialogShowList.setVisible(true);
        showDialogActive = false;
        activeApp = true;
        showList.setEnabled(true);
    }

    public void SetFinalPos(){ // смена направления движения птиц
        Random ran = new Random();
        int finalX = ran.nextInt(1800) + 300;
        int finalY = ran.nextInt(900) + 1;
        int buffX, buffY;
        for(int k = 0; k < 49; k++) {
            if(Bird.size() != 0) {
                if (Bird.get(k).activeObject == true) {
                    buffX = finalX - Bird.get(k).GetX();
                    buffY = finalY - Bird.get(k).GetY();
                    if (buffX > 1) {
                        if (birdAI.plusX > 1) {
                        } else if (birdAI.plusX < 1) {
                            birdAI.plusX = birdAI.plusX * (-1);
                            chickAI.plusX = birdAI.plusX * (-1);
                        }
                    } else if (buffX < 1) {
                        if (birdAI.plusX > 1) {
                            birdAI.plusX = birdAI.plusX * (-1);
                            chickAI.plusX = birdAI.plusX * (-1);
                        } else if (birdAI.plusX < 1) {

                        }
                    }
                    if (buffY > 1) {
                        if (birdAI.plusY > 1) {
                        } else if (birdAI.plusY < 1) {
                            birdAI.plusY = birdAI.plusY * (-1);
                            chickAI.plusY = birdAI.plusY * (-1);
                        }
                    } else if (buffY < 1) {
                        if (birdAI.plusY > 1) {
                            birdAI.plusY = birdAI.plusY * (-1);
                            chickAI.plusY = birdAI.plusY * (-1);
                        } else if (birdAI.plusY < 1) {
                        }
                    }
                }
            }
        }
    }

    public void UpDateScreen() // метод для обновление экрана после зарузки
    {
        for(int i=0;i<Bird.size();i++){
            if(Bird.get(i).activeObject == true){
                secondPanel.add(Bird.get(i).LayObject);

            }
            else if(Chick.get(i).activeObject == true){
                secondPanel.add(Chick.get(i).LayObject);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}             // нажал

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_B)         // при нажатие на клавишу
        {
            if(activeApp == false){
                activeApp = true;
                stopSim = false;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_E){
            if(activeApp == true){
                activeApp = false;
                stopSim = true;
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_T){
            if(activeInfo == false){
                activeInfo = true;
            } else {
                activeInfo = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}          // отпустил
}
