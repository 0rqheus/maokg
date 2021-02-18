import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.*;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 800, 500);

        scene.setFill(Color.BLUE);

        Rectangle rectangle = new Rectangle(180, 50, 300, 330);
        root.getChildren().add(rectangle);
        rectangle.setFill(Color.WHITE);

        // Polyline polyline = new Polyline(144, 80, 144, 25, 492, 25, 492, 80);
        // polyline.setStrokeWidth(8);
        // polyline.setStrokeLineCap(StrokeLineCap.ROUND);
        // polyline.setStroke(Color.YELLOW);

        Line mainLine = new Line(144, 25, 492, 25);
        configureLine(mainLine);

        Line firstDownLine = new Line(144, 25, 144, 80);
        configureLine(firstDownLine);

        Line secondDownLine = new Line(492, 24, 492, 80);
        configureLine(secondDownLine);

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[] {
            385d, 135d,
            350d, 440d,
            720d, 420d
        });

        triangle.setStrokeLineJoin(StrokeLineJoin.ROUND);
        triangle.setFill(Color.LIGHTGRAY);

        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(new Double[] {
                335d, 215d,
                180d, 460d,
                30d, 405d,
                60d, 170d
        });

        polygon.setStrokeLineJoin(StrokeLineJoin.ROUND);
        polygon.setFill(Color.LIGHTGREEN);

        root.getChildren().addAll(mainLine, firstDownLine, secondDownLine, triangle, polygon);
        System.out.println(triangle.strokeLineJoinProperty());

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public  void configureLine(Line line) {
        line.setStrokeWidth(8);
        line.setStrokeLineCap(StrokeLineCap.ROUND);
        line.setStroke(Color.YELLOW);
    }
}