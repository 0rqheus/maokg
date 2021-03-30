import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.vecmath.Color3f;
import java.awt.*;

public class CountryHouseObject {
    public static Box getBody(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getBodyAppearence());
    }

    public static Box getWindow(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getWindowAppearence());
    }

    public static Cone getHouseRoof(float height, float radius){
        TransformGroup tg = new TransformGroup();
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cone(radius, height, primflags, getRoofAppearence());
    }

    public static Box getWindowLine(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getWindowFrameAppearence());
    }

    public static Box getDoor(float height, float width, float length) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Box(width, length, height, primflags, getDoorAppearence());
    }

    private static Appearance getBodyAppearence() {
        TextureLoader loader = new TextureLoader("resources\\whitewashed-wall.jpg", new Container());
        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.REPLACE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        return ap;
    }

    private static Appearance getWindowAppearence() {
        Appearance ap = new Appearance();
        Color3f emissive = new Color3f(new Color(0,0, 0));
        Color3f diffuse = new Color3f(new Color(255, 230, 90));
        Color3f ambient = new Color3f(new Color(255,255, 0));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }
    
    private static Appearance getWindowFrameAppearence() {
    	TextureLoader loader = new TextureLoader("resources\\window-wood.jpg", new Container());
        Texture texture = loader.getTexture();
        
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.REPLACE);
    	
        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        
        return ap;
    }

    private static Appearance getDoorAppearence() {
    	
    	TextureLoader loader = new TextureLoader("resources\\door.png", new Container());
        Texture texture = loader.getTexture();
        
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
        
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.REPLACE);
    	
        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        
        return ap;
    }

    private static Appearance getRoofAppearence() {
        TextureLoader loader = new TextureLoader("resources\\straw.jpg", new Container());
        Texture texture = loader.getTexture();

        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.REPLACE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        
        return ap;
    }
}