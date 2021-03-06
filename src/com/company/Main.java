package com.company;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import static java.lang.StrictMath.*;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in).useLocale(Locale.FRENCH);
        int N = input.nextInt();
        double dt = input.nextDouble();
        Point2D.Double currentCoordinates = new Point2D.Double(.0, .0);
        double currentHeading = .0;
        ArrayList<Point2D.Double> pointList = new ArrayList<>();
        pointList.add((Point2D.Double) currentCoordinates.clone());
        for (int i = 1; i <= N; i++) {
            double linVelocity = input.nextDouble();
            double angVelocity = input.nextDouble();
            double deltaHead = (angVelocity) * dt;
            double deltaX = linVelocity * cos(currentHeading + deltaHead / 2.0) * dt;
            double deltaY = linVelocity * sin(currentHeading + deltaHead / 2.0) * dt;
            if (deltaHead != 0) {
                currentCoordinates.x += (deltaX) * sin(deltaHead) / deltaHead + (deltaY) * (cos(deltaHead) - 1.0) / deltaHead;
                currentCoordinates.y += (deltaX) * (1.0 - cos(deltaHead)) / deltaHead + deltaY * sin(deltaHead) / deltaHead;
            } else {
                currentCoordinates.x += deltaX;
                currentCoordinates.y += deltaY;
            }
            currentHeading += deltaHead;
            pointList.add((Point2D.Double) currentCoordinates.clone());
            for (int j = 1; j < i - 1; j++) {
                double divisor = (pointList.get(j - 1).x - pointList.get(j).x) * (pointList.get(i - 1).y - pointList.get(i).y) - (pointList.get(j - 1).y - pointList.get(j).y) * (pointList.get(i - 1).x - pointList.get(i).x);
                if (divisor != 0) {
                    Point2D.Double intersectionPoint = new Point2D.Double(((pointList.get(j - 1).x * pointList.get(j).y - pointList.get(j - 1).y * pointList.get(j).x) * (pointList.get(i - 1).x - pointList.get(i).x) - (pointList.get(j - 1).x - pointList.get(j).x) * (pointList.get(i - 1).x * pointList.get(i).y - pointList.get(i - 1).y * pointList.get(i).x)) / divisor,
                            ((pointList.get(j - 1).x * pointList.get(j).y - pointList.get(j - 1).y * pointList.get(j).x) * (pointList.get(i - 1).y - pointList.get(i).y) - (pointList.get(j - 1).y - pointList.get(j).y) * (pointList.get(i - 1).x * pointList.get(i).y - pointList.get(i - 1).y * pointList.get(i).x)) / divisor);
                    if (((intersectionPoint.x >= pointList.get(j - 1).x && intersectionPoint.x <= pointList.get(j).x) || (intersectionPoint.x <= pointList.get(j - 1).x && intersectionPoint.x >= pointList.get(j).x)) &&
                            ((intersectionPoint.y >= pointList.get(j - 1).y && intersectionPoint.y <= pointList.get(j).y) || (intersectionPoint.y <= pointList.get(j - 1).y && intersectionPoint.y >= pointList.get(j).y)) &&
                            ((intersectionPoint.x >= pointList.get(i - 1).x && intersectionPoint.x <= pointList.get(i).x) || (intersectionPoint.x <= pointList.get(i - 1).x && intersectionPoint.x >= pointList.get(i).x)) &&
                            ((intersectionPoint.y >= pointList.get(i - 1).y && intersectionPoint.y <= pointList.get(i).y) || (intersectionPoint.y <= pointList.get(i - 1).y && intersectionPoint.y >= pointList.get(i).y))) {
                        System.out.println((int) sqrt(intersectionPoint.x * intersectionPoint.x + intersectionPoint.y * intersectionPoint.y));
                        System.exit(0);
                    }
                }
            }
        }
    }
}
