package views.components;

import model.Card;
import views.CardView;
import views.components.custom.BackgroundPanel;

import java.awt.*;

public class CardPanel extends BackgroundPanel implements CardView {
    private static final Dimension PREFERRED_SIZE_HORIZONTAL = new Dimension(125, 175);
    private static final Dimension PREFERRED_SIZE_VERTICAL = new Dimension(175, 125);

    String suiteString, faceString;
    Color color;

    private boolean isRevealed;
    private Card card;

    public CardPanel(Card card, boolean isHorizontal, boolean isRevealed) {
        super(isHorizontal ? "views/res/cardBGHorizontal.png" : "views/res/cardBGVertical.png");
        Dimension preferredSize = isHorizontal ? PREFERRED_SIZE_HORIZONTAL : PREFERRED_SIZE_VERTICAL;
        setPreferredSize(preferredSize);
        this.card = card;
        this.isRevealed = isRevealed;

        suiteString ="";
        faceString = "";
        Card.Suit suit = card.getSuit();
        switch (suit)
        {
            case SPADE:
                suiteString = "\u2660";
                color = Color.BLACK;
                break;
            case CLUB:
                suiteString = "\u2663";
                color = Color.BLACK;
                break;
            case DIAMOND:
                suiteString = "\u25C6";
                color = Color.RED;
                break;
            case HEART:
                suiteString = "\u2665";
                color = Color.RED;
                break;
        }
        Card.Face face = card.getFace();
        switch (face)
        {
            case ACE:
                faceString = "A";
                break;
            case KING:
                faceString = "K";
                break;
            case QUEEN:
                faceString = "Q";
                break;
            case JACK:
                faceString = "J";
                break;
            default:
                faceString = Integer.toString(face.getValue());
        }

    }

    public CardPanel(Card card, boolean isHorizontal) {
        this(card, isHorizontal, false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (isRevealed) {
            Graphics2D g2 = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            g2.setColor(Color.WHITE);
            g2.fillRect(0, 0, width, height);

            // Suite
            g2.setFont(new Font("Symbola",Font.PLAIN,height/3));
            g2.setColor(color);
            g2.drawString(suiteString, 0, height/4);

            // Card face
            g2.setFont(new Font("Symbola",Font.BOLD,height/3));
            g2.drawString(faceString, width/3, height*2/3);

        }
    }

    @Override
    public void isRevealed(boolean isRevealed) {
        this.isRevealed = isRevealed;
        validate();
    }
}
