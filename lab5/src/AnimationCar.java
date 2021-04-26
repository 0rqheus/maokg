import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AnimationCar implements ActionListener, KeyListener {
    private Button go;
    private TransformGroup wholeCar;
    private Transform3D translateTransform;

    private JFrame mainFrame;

    private float a = 0.0f;
    private float zoom = 0.6f;
    private float xloc = 0.0f;
    private float yloc = 0.0f;
    private float zloc = 0.0f;
    private Timer timer;

    public AnimationCar (TransformGroup wholeCar, Transform3D trans, JFrame frame) {
        go = new Button("Go");

        this.wholeCar = wholeCar;
        this.mainFrame = frame;

        translateTransform = trans;

        zoom = (float) trans.getScale();

        Car.canvas.addKeyListener(this);
        timer = new Timer(5, this);

        Panel p = new Panel();
        p.add(go);
        mainFrame.add("North", p);
        go.addActionListener(this);
        go.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // start timer when button is pressed
        if (e.getSource() == go) {
            if (!timer.isRunning()) {
                timer.start();
                go.setLabel("Stop");
            }
            else {
                timer.stop();
                go.setLabel("Go");
            }
        }
        else {
            Move();
            translateTransform.setScale(new Vector3d(zoom, zoom, zoom));
            translateTransform.setTranslation(new Vector3f(xloc, yloc, zloc));
            wholeCar.setTransform(translateTransform);
        }
    }

    private void Move() {
        yloc += (0.055 * a);
        zloc -= (0.5 * a);

        if (zloc <= -31f) {
            a = 0.0f;
            zloc = -31f;
            yloc = 3.43f;
        }
        else if(zloc >= 2.84f) {
        	a = 0.0f;
            zloc = 2.84f;
            yloc = -0.32f;
        }

        if(a >= 0.6f) {
            a = 0.6f;
        }
        if(a <= -0.3f) {
            a = -0.3f;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //Invoked when a key has been typed.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()){
            case 'w':
                a += 0.1;
                break;
            case 's':
                a -= 0.06;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Invoked when a key has been released.
    }
}