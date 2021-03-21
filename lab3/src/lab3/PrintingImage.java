package lab3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrintingImage extends Application {

    private HeaderBitmapImage image; // �������� ����, ��� ������ ��'��� � ����������� ��� ��������� ��������
    private int numberOfPixels; // �������� ���� ��� ���������� ������� ������ � ����� 

    public PrintingImage() {
    }

    public PrintingImage(HeaderBitmapImage image) // �������������� ����������� �����������
    {
        this.image = image;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ReadingImageFromFile.loadBitmapImage("D:/KPI/MAOKG/maokg_labs_repo/lab3/sources/trajectory.bmp");
        this.image = ReadingImageFromFile.pr.image;
        int width = (int) this.image.getWidth();
        int height = (int) this.image.getHeight();
        int half = (int) image.getHalfOfWidth();

        Group root = new Group();
        Scene scene = new Scene(root, width, 300 + height);
        scene.setFill(Color.LIGHTBLUE);
        Circle cir;

        int let = 0;
        int let1 = 0;
        int let2 = 0;
        char[][] map = new char[width][height];
        // �������� ���������� ����� ��� ���
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream("pixels.txt"));

        for (int i = 0; i < height; i++) // ���� �� ����� ���������� �� ���
        {
            for (int j = 0; j < half; j++) // ���� �� ����� ���������� �� ����
            {
                let = reader.read(); // ������� ���� ������ � ���
                let1 = let;
                let2 = let;
                let1 = let1 & (0xf0); // ������� ���� - ������ ����
                let1 = let1 >> 4; // ���� �� 4 �������
                let2 = let2 & (0x0f); // �������� ���� - ������ ������
                if (j * 2 < width) // ��� �� 1 ������ ���� 2 ����� ��� ��������� ������ �� �������� ����������
                {
                    cir = new Circle((j) * 2, (height - 1 - i), 1, Color.valueOf((returnPixelColor(let1))));
                    // root.getChildren().add(cir); //������ ��'��� � ���

                    if (returnPixelColor(let1) == "BLACK") // ���� ���� ������ ������, �� ������� � ����� 1
                    {
                        map[j * 2][height - 1 - i] = '1';
                        numberOfPixels++; // �������� ������� ������ ������
                    } else {
                        map[j * 2][height - 1 - i] = '0';
                    }
                }
                if (j * 2 + 1 < width) // ��� ������� ������
                {
                    cir = new Circle((j) * 2 + 1, (height - 1 - i), 1, Color.valueOf((returnPixelColor(let2))));
                    // root.getChildren().add(cir);

                    if (returnPixelColor(let2) == "BLACK") {
                        map[j * 2 + 1][height - 1 - i] = '1';
                        numberOfPixels++;
                    } else {
                        map[j * 2 + 1][height - 1 - i] = '0';
                    }
                }
            }
        }
        primaryStage.setScene(scene); // �����������
        primaryStage.show(); // ���������

        reader.close();

        int[][] black;
        black = new int[numberOfPixels][2];
        int lich = 0;

        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("map.txt")); // �������� ����� ��� ���� �� ��������
        for (int i = 0; i < height; i++) // ���� �� ����� ���������� �� �����
        {
            for (int j = 0; j < width; j++) // ���� �� ����� ���������� �� �������
            {
                if (map[j][i] == '1') {
                    black[lich][0] = j;
                    black[lich][1] = i;
                    lich++;
                }
                writer.write(map[j][i]);
            }
            writer.write(10);
        }
        writer.close();

        System.out.println("number of black color pixels = " + numberOfPixels);

        Path path2 = new Path();
        for (int l = 0; l < numberOfPixels - 1; l++) {
            path2.getElements().addAll(new MoveTo(black[l][0], black[l][1]),
                    new LineTo(black[l + 1][0], black[l + 1][1]));
        }

        // Body
        {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(115);
            ellipse.setCenterY(140);
            ellipse.setRadiusX(95);
            ellipse.setRadiusY(95);
            ellipse.setFill(Color.BLACK);
            root.getChildren().add(ellipse);
        }

        // Lower gray ellipse
        {
            Path p = new Path();
            MoveTo mv = new MoveTo(55, 213);
            QuadCurveTo qt1 = new QuadCurveTo(135, 120, 188, 200);
            QuadCurveTo qt2 = new QuadCurveTo(130, 265, 55, 213);
            p.setFill(Color.DARKGRAY);
            p.getElements().addAll(mv, qt1, qt2);
            root.getChildren().add(p);
        }

        // Black hair
        {
            Path p = new Path();
            MoveTo mv = new MoveTo(135, 50);
            QuadCurveTo qt1 = new QuadCurveTo(122, 20, 85, 10);
            QuadCurveTo qt2 = new QuadCurveTo(56, 7, 58, 35);
            QuadCurveTo qt3 = new QuadCurveTo(84, 36, 95, 46);
            p.setStrokeWidth(5);
            p.setStrokeLineJoin(StrokeLineJoin.ROUND);
            p.setStrokeLineCap(StrokeLineCap.ROUND);
            p.setStroke(Color.BLACK);
            p.setFill(Color.BLACK);
            p.getElements().addAll(mv, qt1, qt2, qt3);
            root.getChildren().add(p);
        }

        // Orange hair
        {
            Path p = new Path();
            p.getElements().addAll(new MoveTo(88, 23), new LineTo(99, 17), new LineTo(78, 14),
                    new ArcTo(1, 1, 0, 73, 30, false, false), new LineTo(90, 32), new LineTo(88, 23));
            p.setStrokeWidth(1);
            p.setFill(Color.DARKORANGE);
            root.getChildren().add(p);
        }

        // White circle on forehead
        {
            Ellipse ellipse = new Ellipse();
            ellipse.setCenterX(140);
            ellipse.setCenterY(70);
            ellipse.setRadiusX(10);
            ellipse.setRadiusY(7);
            ellipse.setFill(Color.WHITE);
            root.getChildren().add(ellipse);
        }


        //// Mouth

        // lower part
        {
            Path p = new Path();
            p.getElements().addAll(new MoveTo(115, 140), new LineTo(152, 121), new LineTo(180, 130),
                    new ArcTo(6, 3, -15, 115, 140, false, true));
            p.setStrokeWidth(1);
            p.setFill(Color.ORANGE);
            p.setStrokeLineCap(StrokeLineCap.ROUND);
            p.setStrokeLineJoin(StrokeLineJoin.ROUND);
            root.getChildren().add(p);
        }

        // upper part
        {
            Path p = new Path();
            p.getElements().addAll(new MoveTo(115, 115), new LineTo(132, 105), new QuadCurveTo(169, 101, 193, 132),
                    new QuadCurveTo(160, 112, 115, 115));
            p.setStrokeWidth(1);
            p.setFill(Color.ORANGE);
            root.getChildren().add(p);
        }

        // teeth
        {
            Path p = new Path();
            p.setStrokeWidth(1);
            p.setStroke(Color.WHITE);
            p.setFill(Color.WHITE);
            p.getElements().add(new MoveTo(145, 120));
            p.getElements().add(new QuadCurveTo(122, 118, 116, 124));
            // p.getElements().add(new LineTo(116, 124));
            p.getElements().add(new ArcTo(3, 3, 0, 120, 133, false, false));
            root.getChildren().add(p);
        }


        //// Eyes

        // grey part
        {
            Path p = new Path();
            p.getElements().addAll(new MoveTo(70, 95), new ArcTo(4, 6, 20, 104, 100, false, true),
                    new QuadCurveTo(70, 115, 70, 95));
            p.setStrokeWidth(1);
            p.setStroke(Color.GRAY);
            p.setFill(Color.GRAY);
            root.getChildren().add(p);

            Path path = new Path();
            path.getElements().addAll(new MoveTo(160, 103), new ArcTo(3, 6, 0, 190, 120, false, true),
                    new QuadCurveTo(176, 107, 160, 103));

            path.setStrokeWidth(1);
            path.setStroke(Color.GRAY);
            path.setFill(Color.GRAY);
            root.getChildren().add(path);
        }

        // white part
        {
            Path p = new Path();
            p.getElements().addAll(new MoveTo(102, 98), new ArcTo(3, 6, 15, 75, 99, false, false),
                    new QuadCurveTo(89, 90, 102, 98));
            p.setStrokeWidth(1);
            p.setStroke(Color.BLACK);
            p.setFill(Color.WHITE);
            root.getChildren().add(p);

            Path path = new Path();
            path.getElements().addAll(new MoveTo(165, 102), new ArcTo(4, 10, 9, 185, 105, false, true),
                    new QuadCurveTo(179, 98, 165, 102));
            path.setStrokeWidth(1);
            path.setStroke(Color.BLACK);
            path.setFill(Color.WHITE);
            root.getChildren().add(path);
        }

        // pupil of the eye
        {
            Circle circle = new Circle(94, 88, 6);
            circle.setFill(Color.BLACK);

            Circle circle1 = new Circle(179, 95, 5);
            circle1.setFill(Color.BLACK);

            root.getChildren().addAll(circle, circle1);
        }

        // eyebrows
        {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[] { 111.0, 75.0, 105.0, 85.0, 45.0, 55.0, 60.0, 45.0 });
            polygon.setFill(Color.BROWN);
            root.getChildren().add(polygon);
        }
        {
            Polygon polygon = new Polygon();
            polygon.getPoints().addAll(new Double[] { 162.0, 82.0, 170.0, 93.0, 213.0, 76.0, 207.0, 67.0 });
            polygon.setFill(Color.BROWN);
            root.getChildren().add(polygon);
        }

        int time = 2000;

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(5000));
        pathTransition.setPath(path2);

        pathTransition.setNode(root);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(3);
        rotateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time*2), root);
        scaleTransition.setToX(-1);
        scaleTransition.setToY(-1);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition, scaleTransition, pathTransition);
        // parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();

    }

    private String returnPixelColor(int color) // ����� ��� ������������ ������� 16-������ �������
    {
        String col = "BLACK";

        switch (color) 
        {
            case 0: return "BLACK";
            case 1: return "LIGHTCORAL";
            case 2: return "GREEN";
            case 3: return "BROWN";
            case 4: return "BLUE";
            case 5: return "MAGENTA";
            case 6: return "CYAN";
            case 7: return "LIGHTGRAY";
            case 8: return "DARKGRAY";
            case 9: return "RED";
            case 10: return "LIGHTGREEN";
            case 11: return "YELLOW";
            case 12: return "LIGHTBLUE";
            case 13: return "LIGHTPINK";
            case 14: return "LIGHTCYAN";
            case 15: return "WHITE";
        }

        return col;
    }

    public static void main(String args[]) {
        launch(args);
    }

}
