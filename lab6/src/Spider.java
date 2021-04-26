import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Hashtable;

public class Spider extends JFrame {
    private final Canvas3D canvas;
    private Hashtable roachNamedObjects;

    private Spider() throws FileNotFoundException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse simpUniv = new SimpleUniverse(canvas);
        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);
        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(canvas);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Spider");
        setSize(960, 540);
        getContentPane().add("Center", canvas);
        setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Spider();
    }

    private void createSceneGraph(SimpleUniverse su) throws FileNotFoundException {
        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene bugScene = f.load("resources/black_widow.obj");

        roachNamedObjects = bugScene.getNamedObjects();
        TransformGroup sceneGroup = new TransformGroup();
        BranchGroup scene = new BranchGroup();

        Transform3D scaling = new Transform3D();
        scaling.setScale(0.17);
        Transform3D tfBug = new Transform3D();
        tfBug.rotX(3 * Math.PI / 2);
        tfBug.mul(scaling);
        tfBug.setTranslation(new Vector3d(0.3f, 0.1f, 0.1f));
        TransformGroup tgBug = new TransformGroup(tfBug);

        TransformGroup tgBody = new TransformGroup();
        Shape3D blkwBody = (Shape3D) roachNamedObjects.get("blkw_body");
        Appearance app = new Appearance();
        Color3f black = new Color3f(Color.black);
        app.setMaterial(new Material(black, black, black, black, 0f));
        blkwBody.setAppearance(app);
        tgBody.addChild(blkwBody.cloneTree());
        sceneGroup.addChild(tgBody.cloneTree());

        BoundingSphere bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
        Transform3D legRotAxis = new Transform3D();
        legRotAxis.rotZ(Math.PI / 2);
        Transform3D leg2RotAxis = new Transform3D();
        int noRotHour = 1000;
        int timeRotationHour = 300;

        sceneGroup.addChild(getAnimatedLeg("leg1", noRotHour, timeRotationHour, legRotAxis, bs, (float) Math.PI / 8, 100));
        sceneGroup.addChild(getAnimatedLeg("leg2", noRotHour, timeRotationHour, legRotAxis, bs, (float) Math.PI / 8, 200));
        sceneGroup.addChild(getAnimatedLeg("leg3", noRotHour, timeRotationHour, legRotAxis, bs, (float) Math.PI / 8, 300));
        sceneGroup.addChild(getAnimatedLeg("leg4", noRotHour, timeRotationHour, legRotAxis, bs, (float) Math.PI / 8, 300));
        sceneGroup.addChild(getAnimatedLeg("leg5", noRotHour, timeRotationHour, leg2RotAxis, bs, -(float) Math.PI / 8, 100));
        sceneGroup.addChild(getAnimatedLeg("leg6", noRotHour, timeRotationHour, leg2RotAxis, bs, -(float) Math.PI / 8, 200));
        sceneGroup.addChild(getAnimatedLeg("leg7", noRotHour, timeRotationHour, leg2RotAxis, bs, -(float) Math.PI / 8, 300));
        sceneGroup.addChild(getAnimatedLeg("leg8", noRotHour, timeRotationHour, leg2RotAxis, bs, -(float) Math.PI / 8, 200));


        Transform3D tCrawl = new Transform3D();
        tCrawl.rotY(Math.PI / 2);

        long crawlTime = 20000;
        Alpha crawlAlpha = new Alpha(1, Alpha.INCREASING_ENABLE, 0, 0, crawlTime, 0, 0, 0, 0, 0);

        float crawlDistance = 20.0f;
        PositionInterpolator posICrawl = new PositionInterpolator(crawlAlpha, sceneGroup, tCrawl, -3.0f, crawlDistance);

        posICrawl.setSchedulingBounds(bs);
        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneGroup.addChild(posICrawl);

        tgBug.addChild(sceneGroup);
        scene.addChild(tgBug);

        addImageBackground("resources/web2.jpg", scene);
        scene.compile();

        su.addBranchGraph(scene);
    }

    private void addImageBackground(String imagePath, BranchGroup root) {
        TextureLoader t = new TextureLoader(imagePath, canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    private void addLight(SimpleUniverse su) {
        BranchGroup bgLight = new BranchGroup();
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f lightDir = new Vector3f(-1.0f, 0.0f, -0.5f);
        DirectionalLight light = new DirectionalLight(lightColour, lightDir);
        light.setInfluencingBounds(bounds);
        bgLight.addChild(light);
        su.addBranchGraph(bgLight);
    }

    private TransformGroup getAnimatedLeg(String elementName, int noRotHour, long timeRotationHour, Transform3D legRotAxis, Bounds bs, float v, int l) {
        Alpha legRotAlpha = new Alpha(noRotHour, Alpha.INCREASING_ENABLE, l, 0, timeRotationHour, 0, 0, 0, 0, 0);

        Shape3D leg = (Shape3D) roachNamedObjects.get(elementName);
        TransformGroup tgLeg = new TransformGroup();
        tgLeg.addChild(leg.cloneTree());

        RotationInterpolator legRotation = new RotationInterpolator(legRotAlpha, tgLeg, legRotAxis, v, 0.0f);
        legRotation.setSchedulingBounds(bs);
        tgLeg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        tgLeg.addChild(legRotation);

        return tgLeg;
    }
}