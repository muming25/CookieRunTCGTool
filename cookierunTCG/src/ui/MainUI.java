package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

import dataStructure.Card;
import dataStructure.CardList;
import dataStructure.CardLoader;
import dataStructure.Deck;

import javax.swing.JPanel;
import java.awt.Panel;
import java.util.List;
import java.awt.ScrollPane;
import java.awt.TextField;

import ui.ClickableCardLabel.CardListCallBack;
import util.CardUtil.CardColor;
import util.CardUtil.CardType;
import util.Constant;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.JButton;

public class MainUI implements CardListCallBack{

	private static boolean DEBUG = false;
    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");

    	
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainUI window = new MainUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private JPanel mCardsPane, mDeckPane;
    private JCheckBox cb_color_red;
    private JCheckBox cb_color_yellow;
    private JCheckBox cb_color_green;
    private JCheckBox cb_type_cookie;
    private JCheckBox cb_type_item;
    private JCheckBox cb_type_trap;
    private JCheckBox cb_type_stage;
    private Deck mDeck;
    private ScrollPane scrollPane;
    private Panel mCardDetailPane;
    private Panel panel;
    private TextField textField;
    private JButton loadBtn, saveBtn, selectBtn;
    private JButton mClearDeckBtn;
    private JLabel mCardCountTxt, mFlipCountTxt;
    private JLabel label_2;
    private JCheckBox cb_flip;
    private void initialize() {
        
/*        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());*/
        
        mDeck = new Deck();
        frame = new JFrame();
        frame.setTitle("薑餅人組牌系統   V "+Constant.VERSION);
        frame.setBounds(100, 100, 1000, 540);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        Panel SearchPane = new Panel();
        SearchPane.setBounds(10, 10, 149, 485);
        frame.getContentPane().add(SearchPane);
        SearchPane.setLayout(null);

        
        
        // ========================= color ==================================
        JLabel label = new JLabel("顏色");
        label.setBounds(9, 10, 64, 22);
        SearchPane.add(label);
        
        cb_color_red = new JCheckBox("紅");
        cb_color_red.setFont(new Font("新細明體", Font.PLAIN, 12));
        cb_color_red.setBounds(9, 38, 41, 22);
        SearchPane.add(cb_color_red);
        
        cb_color_yellow = new JCheckBox("黃");
        cb_color_yellow.setBounds(51, 38, 41, 22);
        SearchPane.add(cb_color_yellow);
        
        cb_color_green = new JCheckBox("綠");
        cb_color_green.setBounds(97, 38, 41, 22);
        SearchPane.add(cb_color_green);

        
        
        // ========================= type ==================================
        JLabel label_1 = new JLabel("卡片類型");
        label_1.setBounds(9, 66, 64, 22);
        SearchPane.add(label_1);
        
        cb_type_cookie = new JCheckBox("餅乾");
        cb_type_cookie.setBounds(9, 94, 52, 22);
        SearchPane.add(cb_type_cookie);
        
        cb_type_item = new JCheckBox("物品");
        cb_type_item.setBounds(62, 94, 52, 22);
        SearchPane.add(cb_type_item);
        
        cb_type_trap = new JCheckBox("陷阱");
        cb_type_trap.setBounds(9, 122, 52, 22);
        SearchPane.add(cb_type_trap);
        
        cb_type_stage = new JCheckBox("場景");
        cb_type_stage.setBounds(62, 122, 52, 22);
        SearchPane.add(cb_type_stage);
        
        
        JButton button_search = new JButton("搜尋");
        button_search.setBounds(1, 241, 60, 25);
        button_search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getSelectCards();
            }
        });
        
        SearchPane.add(button_search);
        
        JButton button_clean = new JButton("清除");
        button_clean.setBounds(86, 241, 60, 25);
        SearchPane.add(button_clean);
        
        label_2 = new JLabel("特殊");
        label_2.setBounds(9, 153, 64, 22);
        SearchPane.add(label_2);
        
        cb_flip = new JCheckBox("Flip");
        cb_flip.setBounds(9, 181, 52, 22);
        SearchPane.add(cb_flip);
        
        
        // ==== 卡組
        mDeckPane = new JPanel();
        mDeckPane.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        
        JScrollPane scrollDeckPane = new JScrollPane(mDeckPane);
        scrollDeckPane.setBounds(165, 10, 415, 220);
        scrollDeckPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDeckPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        frame.getContentPane().add(scrollDeckPane);
        
        
        // ==== 卡片列表
        mCardsPane = new JPanel();
        mCardsPane.setLayout(new GridLayout(0, 6, 5, 5));
        
        JScrollPane scrollCardsPane = new JScrollPane(mCardsPane);
        scrollCardsPane.setBackground(new Color(255, 255, 255));
        scrollCardsPane.setBounds(165, 266, 415, 230);
        scrollCardsPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollCardsPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.getContentPane().add(scrollCardsPane);
        
        mCardDetailPane = new Panel();
        mCardDetailPane.setLayout(new BorderLayout());
        mCardDetailPane.setBounds(586, 10, 390, 438);
        frame.getContentPane().add(mCardDetailPane);
        
        
        // ===== 檔案
        panel = new Panel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBounds(586, 455, 390, 38);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        textField = new TextField();
        textField.setText("Deck1");
        textField.setBounds(9, 5, 147, 22);
        panel.add(textField);
        
        loadBtn = new JButton("讀取");
        loadBtn.setBounds(256, 2, 60, 25);
        panel.add(loadBtn);
        loadBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mDeck = CardLoader.loadDeck(textField.getText());
                mDeck.sort();
                updateDeck();
            }
        });
        
        saveBtn = new JButton("儲存");
        saveBtn.setBounds(320, 2, 60, 25);
        panel.add(saveBtn);
        saveBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLoader.saveDeck(textField.getText(), mDeck);
            }
        });
        
        selectBtn = new JButton("選擇檔案");
        selectBtn.setActionCommand("選擇檔案");
        selectBtn.setBounds(162, 2, 90, 25);
        panel.add(selectBtn);
        selectBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("resources/deck/"));
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION)
				{ 
					File selectedFile = fileChooser.getSelectedFile();
					String filename = selectedFile.getName();
					System.out.println(selectedFile.getName());
					textField.setText(filename.substring(0, filename.length() - 4));
				} 
            }
        });
        
        mClearDeckBtn = new JButton("清除卡組");
        mClearDeckBtn.setBounds(495, 233, 85, 23);
        frame.getContentPane().add(mClearDeckBtn);
        
        mCardCountTxt = new JLabel("0/60");
        mCardCountTxt.setBounds(453, 233, 36, 22);
        frame.getContentPane().add(mCardCountTxt);
        
        mFlipCountTxt = new JLabel("0/16");
        mFlipCountTxt.setBounds(411, 233, 36, 22);
        frame.getContentPane().add(mFlipCountTxt);
        mClearDeckBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mDeck.clear();
                updateDeck();
            }
        });
        
        prepareCards();
    }
    
    private void prepareCards() {
        CardList list = CardList.getInstance();
        System.out.println("========== start prepareCards =============");
        for (Card card: list.getAllCards()) {
        	String path = "resources/cards/"+card.getPack()+"/"+card.getId()+".png";
        	if (DEBUG) {
        		card.dump();
                System.out.println("read : "+path);
        	}
            ImageIcon cardIcon = new ImageIcon(path);
            
            Image image = cardIcon.getImage().getScaledInstance(60, 84,  java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(image);
            ClickableCardLabel cardLabel = new ClickableCardLabel(cardIcon, card);
            cardLabel.addClickListener(this);
            mCardsPane.add(cardLabel);
        }
    }
    
    private void getSelectCards() {
        System.out.println("========== start getSelectCards =============");
        CardList list = CardList.getInstance();
        list.setColor(CardColor.Red.getValue(), cb_color_red.isSelected());
        list.setColor(CardColor.Yellow.getValue(), cb_color_yellow.isSelected());
        list.setColor(CardColor.Green.getValue(), cb_color_green.isSelected());
        list.setType(CardType.Cookie.getValue(), cb_type_cookie.isSelected());
        list.setType(CardType.Item.getValue(), cb_type_item.isSelected());
        list.setType(CardType.Trap.getValue(), cb_type_trap.isSelected());
        list.setType(CardType.Stage.getValue(), cb_type_stage.isSelected());
        list.setFlip(cb_flip.isSelected());
        mCardsPane.removeAll();
        mCardsPane.setLayout(new GridLayout(0, 6, 5, 5));
        List<Card> cardList = list.getSelectCards();
        for (Card card: cardList) {
            String path = "resources/cards/"+card.getPack()+"/"+card.getId()+".png";
        	if (DEBUG) {
        		card.dump();
                System.out.println("read : "+path);
        	}
            ImageIcon cardIcon = new ImageIcon(path);
            
            Image image = cardIcon.getImage().getScaledInstance(60, 84,  java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(image);
            ClickableCardLabel cardLabel = new ClickableCardLabel(cardIcon, card);
            cardLabel.addClickListener(this);
            mCardsPane.add(cardLabel);
        }
        for(int i=cardList.size(); i<13; i++) {
            String path = "resources/cards/empty.png";
            ImageIcon cardIcon = new ImageIcon(path);
            Image image = cardIcon.getImage().getScaledInstance(60, 84,  java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(image);
            JLabel cardLabel = new JLabel(cardIcon);
            mCardsPane.add(cardLabel);
        }
        mCardsPane.revalidate();
        mCardsPane.repaint();
    }
    
    private void updateDeck() {
        mDeckPane.removeAll();
        mDeckPane.setLayout(new GridLayout(0, 6, 5, 5));
        System.out.println("========== start updateDeck =============");
        for (Card card: mDeck.getAllCards()) {
        	String path = "resources/cards/"+card.getPack()+"/"+card.getId()+".png";
        	if (DEBUG) {
        		card.dump();
                System.out.println("read : "+path);
        	}
            ImageIcon cardIcon = new ImageIcon(path);
            
            Image image = cardIcon.getImage().getScaledInstance(60, 84,  java.awt.Image.SCALE_SMOOTH);
            cardIcon = new ImageIcon(image);
            ClickableCardLabel cardLabel = new ClickableCardLabel(cardIcon, card);
            cardLabel.addClickListener(this);
            mDeckPane.add(cardLabel);
        }
        mDeckPane.revalidate();
        mDeckPane.repaint();
        mCardCountTxt.setText(mDeck.getCardCount()+"/60");
        if (mDeck.getCardCount() > 60) {
        	mCardCountTxt.setForeground(Color.RED);
        } else {
        	mCardCountTxt.setForeground(Color.BLACK);
        }
        
        mFlipCountTxt.setText(mDeck.getFlipCount()+"/16");
        if (mDeck.getFlipCount() > 16) {
        	mFlipCountTxt.setForeground(Color.RED);
        } else {
        	mFlipCountTxt.setForeground(Color.BLACK);
        }
    }

    @Override
    public void addCard(Card card) {
        System.out.println("addCard : "+card.getName());
        if (mDeck.addCard(card)) {
            mDeck.sort();
            updateDeck();
        }
    }

    @Override
    public void removeCard(Card card) {
        System.out.println("removeCard : "+card.getName());
        if (mDeck.removeCard(card)) {
            mDeck.sort();
            updateDeck();
        }
    }

    @Override
    public void showCard(Card card) {
        mCardDetailPane.removeAll();
        ImageIcon cardIcon = new ImageIcon("resources/cards/"+card.getPack()+"/"+card.getId()+".png");
            
        Image image = cardIcon.getImage().getScaledInstance(342, 469, java.awt.Image.SCALE_SMOOTH);
        cardIcon = new ImageIcon(image);
        ClickableCardLabel cardLabel = new ClickableCardLabel(cardIcon, card);
        mCardDetailPane.add(cardLabel, BorderLayout.CENTER);
        mCardDetailPane.revalidate();
        mCardDetailPane.repaint();
    }
}
