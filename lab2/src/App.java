import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class App extends JPanel implements ActionListener {

    private static int maxWidth;
    private static int maxHeight;

    Timer timer;

    private double angle = 0;
    private double scale = 1;
    private double delta = 0.01;

    public App() {
        timer = new Timer(10, this);
        timer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);

        g2d.setBackground(Color.BLUE);
        g2d.clearRect(0,0, maxWidth, maxHeight);

        drawBorder(g2d);

        // Set center
        g2d.translate(maxWidth/2, maxHeight/1.6);

        g2d.rotate(-angle, 110,-140);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)scale));

        // Rectangle
        GradientPaint gp = new GradientPaint(10, 100, Color.BLACK, 20, 2, Color.YELLOW, true);
        g2d.setPaint(gp);
        int[] xRect = new int[] {-100, 100, 100, -100};
        int[] yRect = new int[] {120, 120, -120, -120};
        Polygon rect = new Polygon(xRect, yRect,4);
        g2d.drawPolygon(rect);
        g2d.fillPolygon(rect);

        // Triangle
        g2d.setColor(Color.LIGHT_GRAY);
        
        Polygon triangle = new Polygon();
        triangle.addPoint( 55, -75);
        triangle.addPoint( 20, 180);
        triangle.addPoint( 300, 160);

        g2d.drawPolygon(triangle);
        g2d.fillPolygon(triangle);

        // Square
        GradientPaint gp2 = new GradientPaint(10, 200, Color.BLACK, 10, 2, Color.GREEN, true);
        g2d.setPaint(gp2);

        Polygon quadra = new Polygon();
        quadra.addPoint(5,0);
        quadra.addPoint(-180,-25);
        quadra.addPoint(-210,140);
        quadra.addPoint(-100, 200);

        g2d.drawPolygon(quadra);
        g2d.fillPolygon(quadra);

        // Lines
        g2d.setColor(Color.YELLOW);
        g2d.drawLine(-125, -140, 110,-140);
        g2d.drawLine(-125, -140, -125, -100);
        g2d.drawLine(110, -140, 110, -100);
    }

    private void drawBorder(Graphics2D g2d) {
        BasicStroke basicStroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(basicStroke);
        GradientPaint gp = new GradientPaint(5, 20, Color.RED, 20, 2, Color.WHITE, true);
        g2d.setPaint(gp);
        g2d.drawRect(10, 10, maxWidth - 20, maxHeight - 20);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("lab2");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(1200, 1000);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);

        jFrame.add(new App());
        jFrame.setVisible(true);

        Dimension size = jFrame.getSize();
        Insets insets = jFrame.getInsets();
        maxWidth = size.width - insets.left - insets.right - 1;
        maxHeight = size.height - insets.top - insets.bottom - 1;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if ( scale < 0.35 ) {
            delta = -delta;
        } else if (scale > 0.99) {
            delta = -delta;
        }

        scale += delta;
        angle += 0.02;
        repaint();
    }

}