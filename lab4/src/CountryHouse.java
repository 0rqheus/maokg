import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class CountryHouse implements ActionListener {
    private final float upperEyeLimit = 5.0f;
    private final float lowerEyeLimit = 1.0f;
    private final float farthestEyeLimit = 6.0f;
    private final float nearestEyeLimit = 8.0f;
    private TransformGroup treeTransformGroup;
    private final TransformGroup viewingTransformGroup;
    private final Transform3D treeTransform3D = new Transform3D();
    private final Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float eyeHeight;
    private float eyeDistance;
    private boolean descend = true;
    private boolean approaching = true;

    public static void main(String[] args) {
        new CountryHouse();
    }

    private CountryHouse() {
        Timer timer = new Timer(30, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        eyeHeight = upperEyeLimit;
        eyeDistance = farthestEyeLimit;
        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildHouseSkeleton();
        objRoot.addChild(treeTransformGroup);

        Background background = new Background(new Color3f(0.0f, 0.0f, 0.0f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 10000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);

        // lighting        
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }

    private void buildHouseSkeleton() {
        Box body1 = CountryHouseObject.getBody(0.05f, 2.1f, 1.6f);
        Transform3D body1T = new Transform3D();
        body1T.setTranslation(new Vector3f());
        TransformGroup body1TG = new TransformGroup();
        body1TG.setTransform(body1T);
        body1TG.addChild(body1);
        treeTransformGroup.addChild(body1TG);

        Box body2 = CountryHouseObject.getBody(1.0f, 1.9f, 1.5f);
        Transform3D body2T = new Transform3D();
        body2T.setTranslation(new Vector3f(.0f, .0f, 1.0f));
        TransformGroup body2TG = new TransformGroup();
        body2TG.setTransform(body2T);
        body2TG.addChild(body2);
        treeTransformGroup.addChild(body2TG);

        setRoof();
        setWindow(1);
        setWindow(-1);
        setDoor();
    }

    private void setRoof(){
        Cone roof = CountryHouseObject.getHouseRoof(2f, 2.8f);
        Transform3D roofCone = new Transform3D();
        roofCone.rotX(Math.PI/2);
        roofCone.setTranslation(new Vector3f(0,0,3));
        TransformGroup tg = new TransformGroup();
        tg.setTransform(roofCone);
        tg.addChild(roof);
        treeTransformGroup.addChild(tg);
    }

    private void setWindow(int yCoef) {
        Box window = CountryHouseObject.getWindow(0.05f, 0.4f, 0.4f);
        Transform3D windowBox = new Transform3D();
        windowBox.rotX(Math.PI/2);
        windowBox.setTranslation(new Vector3f(0.0f, 1.48f * yCoef, 1.2f));
        TransformGroup tg = new TransformGroup();
        tg.setTransform(windowBox);
        tg.addChild(window);
        treeTransformGroup.addChild(tg);
        
        setWindowLineVertical(0.4f, yCoef);
        setWindowLineVertical(0.0f, yCoef);
        setWindowLineVertical(-0.4f, yCoef);
        
        setWindowLineHorizontal(0.8f, yCoef);
        setWindowLineHorizontal(1.2f, yCoef);
        setWindowLineHorizontal(1.6f, yCoef);
    }

    private void setWindowLineVertical(float xPos, int yCoef){
    	Box winLineX = CountryHouseObject.getWindowLine(0.05f, 0.4f,  0.1f);
        Transform3D winLine = new Transform3D();
        winLine.rotY(Math.PI/2);
        winLine.setTranslation(new Vector3f(xPos, 1.49f * yCoef, 1.2f));
        TransformGroup tg = new TransformGroup();
        tg.setTransform(winLine);
        tg.addChild(winLineX);
        treeTransformGroup.addChild(tg);
    }

    private void setWindowLineHorizontal(float zPos, int yCoef){
    	Box winLineX = CountryHouseObject.getWindowLine(0.05f, 0.4f,  0.1f);
        Transform3D winLine = new Transform3D();
        winLine.setTranslation(new Vector3f(0.0f, 1.49f * yCoef, zPos));
        TransformGroup tg = new TransformGroup();
        tg.setTransform(winLine);
        tg.addChild(winLineX);
        treeTransformGroup.addChild(tg);
    }

    private void setDoor(){
        Box doorBox = CountryHouseObject.getDoor(0.05f, 0.8f, 0.4f);
        Transform3D door = new Transform3D();
        door.rotX(Math.PI/2);
        door.rotY(Math.PI/2);
        door.setTranslation(new Vector3f(-1.9f, 0, 1.0f));
        TransformGroup tg = new TransformGroup();
        tg.setTransform(door);
        tg.addChild(doorBox);
        treeTransformGroup.addChild(tg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        treeTransform3D.rotZ(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle -= delta;

        if (eyeHeight > upperEyeLimit){
            descend = true;
        }
        else if(eyeHeight < lowerEyeLimit){
            descend = false;
        }
        if (descend) {
            eyeHeight -= delta;
        }
        else {
            eyeHeight += delta;
        }

        if (eyeDistance > farthestEyeLimit) {
            approaching = true;
        }
        else if(eyeDistance < nearestEyeLimit) {
            approaching = false;
        }
        if (approaching) {
            eyeDistance -= delta;
        }
        else {
            eyeDistance += delta;
        }

        Point3d eye = new Point3d(eyeDistance, eyeDistance, eyeHeight);
        Point3d center = new Point3d(.0f, .0f ,0.5f);
        Vector3d up = new Vector3d(.0f, .0f, 5.0f);
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
