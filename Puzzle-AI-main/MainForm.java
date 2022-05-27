package puzzle;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class MainForm extends javax.swing.JFrame implements MouseListener, KeyListener, Runnable {
    private static final long serialVersionUID = 1L;
    protected int Size = 3;
    private int Length = 9;
    private State state;
    private JumbledImage Ju;
    private ViewImage Vi;
    private int[] Value;
    private Solve solve;
    protected int count = 0;
    private int times = 0;
    protected int typegame = 0;
    protected boolean issolu = false;
    protected boolean win = false;
    private boolean playtime = false;
    private Image image;
    protected Color ColorEBox = Color.white;
    protected Color ColorBoxs = Color.GRAY;
    protected int speed = 400;
    private MainForm JF;
    private GameWon Gw;
    private JProgressBar progressBar;
    public int dee;
    private int H;
    private javax.swing.JComboBox size;
    private javax.swing.JComboBox Kieu;
    private javax.swing.JComboBox Ham;
    private javax.swing.JComboBox Heu;
    private javax.swing.JComboBox HuongDan;

    public MainForm() {
        initComponents();
        progressBar = new JProgressBar();
        progressBar.setLocation(135, 430);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        progressBar.setBorderPainted(true);
        progressBar.setSize(100, 20);
        add(progressBar);
        addKeyListener(this);
        JF = this;
        this.NewGame();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            throw new RuntimeException("Could not set default look and feel");
        }
        setFocusable(true);
    }

    public void Init() {
        issolu = false;
        Value = new int[Size * Size];
        state = new State(Size);
        solve = new Solve();
    }

    public void NewGame() {
        Init();
        Length = Size * Size;
        win = false;
        if (Ju != null)
            this.remove(Ju);
        if (Vi != null)
            this.remove(Vi);
        if (Gw != null) {
            Gw = null;
            this.removeMouseListener(this);
        }
        count = 0;
        times = 0;
        playtime = false;
        time.setText("  Time: " + " 00 : 00");
        display.setText("");
        moveTextField.setText("  Move:  0");
        if (Size < 4) {
            do {
                Value = state.createArrayRandom();
            } while (!state.Test(Value));
        } else
            Value = state.ArrayTronHinh();
        InitFileImage();
        Ju = new JumbledImage(image, Size, Value, typegame, ColorEBox, ColorBoxs);
        Ju.setLocation(380, 110);
        Ju.setSize(Ju.getWidth(), Ju.getHeight());
        Ju.addMouseListener(this);
        this.add(Ju);
        this.repaint();
        solveButton.setEnabled(true);
        solveButton.setText("Solve");
        JF.requestFocus();
    }

    public void ChangeView() {
        if (Ju != null)
            this.remove(Ju);
        if (Vi != null)
            this.remove(Vi);
        InitFileImage();
        Ju = new JumbledImage(image, Size, Value, typegame, ColorEBox, ColorBoxs);
        if (typegame == 0)
            Ju.setLocation(380, 110);
        else {
            int x1 = 370 + (430 - Ju.getWidth()) / 2;
            int y1 = 96 + (430 - Ju.getHeight()) / 2;
            Ju.setLocation(x1, y1);
        }
        Ju.setSize(Ju.getWidth(), Ju.getHeight());
        Ju.addMouseListener(this);
        this.add(Ju);
        this.repaint();
        JF.requestFocus();
    }

    public void InitFileImage() {
        try {
            image = (new ImageIcon(getClass().getResource("/images/aaa.png"))).getImage();
        } catch (Exception e) {
        }
        if (image != null && typegame > 0) {
            Vi = new ViewImage(image);
            Vi.setLocation(100, 250);
            Vi.setSize(Vi.w, Vi.h);
            this.add(Vi);
            this.repaint();
        }
    }

    private void Disable() {
        if (Ju != null) {
            Ju.setEnabled(false);
        }
    }

    private void initComponents() {
        state.heuristic = 1;
        size = new javax.swing.JComboBox();
        size.setFont(new java.awt.Font("Tahoma", 0, 14));
        size.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3 x 3", "4 x 4", "5 x 5", "6 x 6" }));
        size.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sizeButtonPerformed(event);
            }
        });

        Kieu = new javax.swing.JComboBox();
        Kieu.setFont(new java.awt.Font("Tahoma", 0, 14));
        Kieu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Number", "Number-Image", "Image" }));
        Kieu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                KieuButtonPerformed(event);
            }
        });

        Ham = new javax.swing.JComboBox();
        Ham.setFont(new java.awt.Font("Tahoma", 0, 14));
        Ham.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Astar", "IDAstar", "BFS", "DLS", "IDS" }));
        Ham.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                HamButtonPerformed(event);
            }
        });

        Heu = new javax.swing.JComboBox();
        Heu.setFont(new java.awt.Font("Tahoma", 0, 14));
        Heu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Heuristic1", "Heuristic2", "Heuristic3" }));
        Heu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                HeuButtonPerformed(event);
            }
        });
        Panel2 = new javax.swing.JPanel();
        HuongDanButton = new javax.swing.JButton();
        newGameButton = new javax.swing.JButton();
        solveButton = new javax.swing.JButton();
        time = new javax.swing.JTextField();
        moveTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("N-Puzzle Game");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage((new ImageIcon(getClass().getResource("/images/Puzzle.png"))).getImage());

        HuongDanButton.setText("Huong Dan");
        HuongDanButton.setFont(new java.awt.Font("Tahoma", 0, 14));
        HuongDanButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                helpMenuMousePressed(evt);
            }
        });

        newGameButton.setText("New Game");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });

        solveButton.setText("Solve");
        solveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solveButtonActionPerformed(evt);
            }
        });

        time.setEditable(false);
        time.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        moveTextField.setEditable(false);
        moveTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
                Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Panel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(Panel2Layout.createSequentialGroup()
                                                .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(66, 66, 66)
                                                .addComponent(Kieu, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(66, 66, 66)
                                                .addComponent(Ham, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(66, 66, 66)
                                                .addComponent(Heu, javax.swing.GroupLayout.PREFERRED_SIZE, 91,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(66, 66, 66)
                                                .addComponent(HuongDanButton, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        105, javax.swing.GroupLayout.PREFERRED_SIZE)))));
        Panel2Layout.setVerticalGroup(
                Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(Panel2Layout.createSequentialGroup()
                                                .addGroup(Panel2Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING,
                                                                false)
                                                        .addComponent(size, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(Kieu, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(Ham, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(Heu, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(20, 20, 20)
                                                        .addComponent(HuongDanButton,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 28,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))));

        display.setEditable(false);
        display.setFont(new java.awt.Font("Tahoma", 0, 13));
        display.setRows(5);
        display.setColumns(30);
        display.setRequestFocusEnabled(false);
        jScrollPane1.setViewportView(display);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(Panel2, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(100, 100, 100))
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGap(70, 70, 70))
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(newGameButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(solveButton, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGap(50, 50, 50))
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(time, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(moveTextField, javax.swing.GroupLayout.Alignment.LEADING,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 85,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout
                                                .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)))

                        ));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Panel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(90, 90, 90)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(moveTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(solveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(260, 260, 260)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                                javax.swing.GroupLayout.PREFERRED_SIZE))));
        pack();
    }

    private void helpMenuMousePressed(java.awt.event.MouseEvent evt) {
        JOptionPane.showMessageDialog(null,
                " N-Puzzle Game \n + Sử dụng chuột/Phím di chuyển ô trống: DOWN, UP, LEFT, RIGHT."
                        + "\n + Sắp xếp các ô theo thứ tự : blank,1,2,3,... hoặc hoàn thành bức tranh. "
                        + "\n + Nhấn Solve để giải quyết bài toán.\n + Nhấn Stop để dừng tìm kiếm/chạy"
                        + "\n + Chúc bạn thành công !",
                "Help", 1, new ImageIcon(getClass().getResource("/images/Puzzle.png")));
    }

    private void solveButtonActionPerformed(java.awt.event.ActionEvent evt) {
        if (issolu) {
            issolu = false;
            solveButton.setText("Solve");
            return;
        }
        playtime = false;
        times = 0;
        time.setText("  Time: " + " 00 : 00");
        moveTextField.setText("");
        int[] initarray = Ju.getValue();
        int[] goalarray = new int[Length];
        for (int i = 0; i < Length; i++)
            goalarray[i] = i;

        State initsta = new State(initarray, Size);
        State goalsta = new State(goalarray, Size);

        solve.initialnode = new Node(initsta, 0);
        solve.goalnode = new Node(goalsta, 0);

        progressBar.setVisible(true);
        progressBar.setString("Please wait ...");
        progressBar.setIndeterminate(true);
        issolu = true;
        solveButton.setText("Stop");
        JF.requestFocus();
    }

    private void sizeButtonPerformed(ActionEvent event) {
        JComboBox Ham = (JComboBox) event.getSource();
        int kt = Ham.getSelectedIndex();
        Size = kt + 3;
        this.NewGame();
    }

    private void HeuButtonPerformed(ActionEvent event) {
        JComboBox Heu = (JComboBox) event.getSource();
        int kt = Heu.getSelectedIndex();
        State.heuristic = kt + 1;
    }

    private void KieuButtonPerformed(ActionEvent event) {
        JComboBox Kieu = (JComboBox) event.getSource();
        int kt = Kieu.getSelectedIndex();
        typegame = kt;
        this.ChangeView();
    }

    private void HamButtonPerformed(ActionEvent event) {
        JComboBox Ham = (JComboBox) event.getSource();
        int kt = Ham.getSelectedIndex();
        H = kt;
        if (kt == 3) {
            String m = JOptionPane.showInputDialog(null, "Nhap độ sâu!",
                    "DLS", JOptionPane.INFORMATION_MESSAGE);
            dee = Integer.parseInt(m);
            solve.deep = dee;
        }
        if (kt == 2 || kt == 3 || kt == 4) {
            Heu.setEnabled(false);
        } else {
            Heu.setEnabled(true);
        }
    }

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
        this.NewGame();
        JF.requestFocus();
    }

    public void solution() {
        playtime = true;
        Thread t1 = new Thread() {
            public synchronized void start() {
                super.start();
            }

            public void run() {
                CountTime();
                super.run();
            }
        };
        t1.start();
        if (H == 0) {
            display.append("+ Astar Algorithm\n");
            display.append(" Heuristic " + State.heuristic + "\n");
            solve.solveAstar();
        } else if (H == 1) {
            display.append("+ IDAstar Algorithm\n");
            display.append(" Heuristic " + State.heuristic + "\n");
            solve.solveIDAstar();
        } else if (H == 2) {
            display.append("+ BFS Algorithm\n");
            solve.solveBFS();
        } else if (H == 3) {
            display.append("+ DLS Algorithm\n");
            solve.deep = dee;
            display.append("+ Deep: " + dee + " \n");
            solve.solveDLS();
        } else if (H == 4) {
            display.append("+ IDS Algorithm\n");
            solve.solveIDS();
        }

        playtime = false;
        if (solve.Stop != null || !issolu) {
            issolu = false;
            if (solve.Stop != "stop")
                display.append(solve.Stop);
            display.append(" Done!\n\n");
            solveButton.setText("Solve");
            solve.Stop = null;
            return;
        }

        display.append(" Nodes already evaluated: " + solve.count + "\n");
        display.append(" Nodes in a tree: " + solve.total_nodes + "\n");
        display.append(" Move: " + (solve.KQ.size() - 1) + "\n");
        display.append(" Time: " + solve.time_solve + "ms\n\n");

        int numStates = solve.KQ.size();
        int k = numStates - 1;
        int auto = JOptionPane.showConfirmDialog(null,
                " + Tìm thấy lời giải trong " + k + " bước." + "\n + Trong thời gian " + solve.time_solve + "ms"
                        + "\n + Bạn có muốn tự động chạy không ?",
                "Autorun", 0, 1, new ImageIcon(getClass().getResource("/images/Puzzle.png")));
        if (auto == 1) {
            issolu = false;
            solveButton.setText("Solve");
            return;
        }

        for (int i = 0; i < numStates - 1; i++) {
            if (!issolu) {
                if (Length == Size * Size)
                    solveButton.setEnabled(true);
                return;
            }
            int j = 0, m = 0;
            j = solve.KQ.elementAt(i).Blank();
            m = solve.KQ.elementAt(i + 1).Blank();
            if (j == m + 1) {
                Ju.LEFT();
                this.repaint();
            } else if (j == m - 1) {
                Ju.RIGHT();
                this.repaint();
            } else if (j == m + Size) {
                Ju.UP();
                this.repaint();
            } else if (j == m - Size) {
                Ju.DOWN();
                this.repaint();
            }
            count++;
            moveTextField.setText("  Move:  " + count + "/" + k);
            if (Ju.checkWin()) {
                win = true;
                this.Disable();
                this.repaint();
                int t = JOptionPane.showConfirmDialog(null, "       Game finished \n   You want to play again ? ",
                        "N-Puzzle", 0, 1, new ImageIcon(getClass().getResource("/images/finish.png")));
                if (t == 0) {
                    this.NewGame();
                    return;
                } else {
                    solveButton.setText("Slove");
                    solveButton.setEnabled(false);
                    issolu = false;
                    return;
                }
            }
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
            }
        }
        issolu = false;
    }

    public void GameWon() {
        Gw = new GameWon();
        Gw.setLocation(400, 280);
        Gw.setVisible(true);
        Gw.setResizable(false);
        this.addMouseListener(this);
        this.setEnabled(false);
    }

    public void ExitGame() {
        System.exit(0);
    }

    public int getsize() {
        return Size;
    }

    public int getlength() {
        return Length;
    }

    public void CountTime() {
        int munites = 0;
        int seconds = 0;
        for (;;) {
            if (win || !playtime) {
                playtime = false;
                times = 0;
                break;
            }
            if (times < 60) {
                seconds = times;
                munites = 0;
            } else {
                munites = times / 60;
                seconds = times % 60;
            }
            if (munites < 10) {
                if (seconds < 10)
                    time.setText("  Time: " + " 0" + munites + " : 0" + seconds);
                else
                    time.setText("  Time: " + " 0" + munites + " : " + seconds);
            } else {
                if (seconds < 10)
                    time.setText("  Time: " + " " + munites + " : 0" + seconds);
                else
                    time.setText("  Time: " + " " + munites + " : " + seconds);
            }
            times++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            ;
        }
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            if (Gw != null && Gw.isClosed) // Khi táº¯t Frame Game Won thÃ¬ tráº£ láº¡i tráº¡ng thÃ¡i ban Ä‘áº§u cho
                                           // MainForm
            {
                this.setVisible(true);
                this.setEnabled(true);
                this.removeMouseListener(this);
                Gw = null;
            }
            if (issolu) {
                solution();
                progressBar.setIndeterminate(false);
                progressBar.setVisible(false);
            } else if (playtime)
                CountTime();
            else {
                if (playtime || issolu)
                    break;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                ;
            }
        }
    }

    private javax.swing.JPanel Panel2;
    private javax.swing.JButton HuongDanButton;
    private javax.swing.JTextArea display;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField moveTextField;
    private javax.swing.JButton newGameButton;
    private javax.swing.JButton solveButton;
    private javax.swing.JTextField time;

    public void mouseClicked(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {

        if (issolu || !Ju.isEnabled())
            return;
        playtime = true;
        int x = (me.getX() - 4) / Ju.getCw();
        int y = (me.getY() - 4) / Ju.getCh();
        int pos = y * Size + x;
        if (Ju.checkWin()) {
            win = true;
            this.Disable();
            solveButton.setEnabled(false);
            this.GameWon();
            return;
        } else {
            if (Ju.blank >= Size && Ju.blank == pos + Size) {
                Ju.UP();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (Ju.blank < Length - Size && Ju.blank == pos - Size) {
                Ju.DOWN();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (Ju.blank % Size != 0 && Ju.blank == pos + 1) {
                Ju.LEFT();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (Ju.blank % Size != Size - 1 && Ju.blank == pos - 1) {
                Ju.RIGHT();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            }

            if (Ju.checkWin()) {
                win = true;
                this.Disable();
                solveButton.setEnabled(false);
                this.GameWon();
                return;
            }
        }
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE) {
            issolu = false;
            return;
        }

        if (issolu || !Ju.isEnabled())
            return;

        if (Ju.checkWin()) {
            win = true;
            JF.requestFocus();
            this.Disable();
            solveButton.setEnabled(false);
            this.GameWon();
            return;
        } else {
            if (ke.getKeyCode() == KeyEvent.VK_DOWN && Ju.blank >= Size) {
                playtime = true;
                Ju.UP();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (ke.getKeyCode() == KeyEvent.VK_UP && Ju.blank < Length - Size) {
                playtime = true;
                Ju.DOWN();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT && Ju.blank % Size != 0) {
                playtime = true;
                Ju.LEFT();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            } else if (ke.getKeyCode() == KeyEvent.VK_LEFT && Ju.blank % Size != Size - 1) {
                playtime = true;
                Ju.RIGHT();
                count++;
                moveTextField.setText("  Move:  " + count);
                this.repaint();
            }
            if (Ju.checkWin()) {
                win = true;
                JF.requestFocus();
                this.Disable();
                solveButton.setEnabled(false);
                this.GameWon();
                return;
            }
        }
    }
}
