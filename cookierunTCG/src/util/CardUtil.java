package util;

import javax.swing.ImageIcon;

public class CardUtil {
	public static int LEVEL_MAX = 3;
	public static int COLOR_MAX = 5;
	public enum CardColor {
	    Red(0), Yellow(1), Green(2), Blue(3), Purple(4);
	    public final int value;
	    private CardColor(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	    
	    public String getName() {
	        switch(this){
		        case Red:
		        	return "��";
		        case Yellow:
		        	return "��";
		        case Green:
		        	return "��";
		        case Blue:
		        	return "��";
		        case Purple:
		        	return "��";
	        }
	        return null;
	    }

	    public static CardColor fromValue(int value) {
	        for (CardColor color : CardColor.values()) {
	            if (color.getValue() == value) {
	                return color;
	            }
	        }
	        // �p�G�S���������� enum�A�A�i�H��ܩߥX�@�Ӳ��`�A�Ϊ̪�^�q�{��
	        throw new IllegalArgumentException("No enum constant with value " + value);
	    }
	}

	public static int TYPE_MAX = 4;
	public enum CardType {
	    Cookie(0), Item(1), Trap(2), Stage(3);
	    private final int value;
	    private CardType(int value) {
	        this.value = value;
	    }

	    public int getValue() {
	        return value;
	    }
	}

	public static ImageIcon CardBack;
}
