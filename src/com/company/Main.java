package com.company;

import java.util.*;

import static java.lang.StrictMath.*;

public class Main {
    
    static Coordinates intersectionPoint = null;
    
    static boolean intersects(Coordinates firstStart,Coordinates firstEnd,Coordinates secondStart,Coordinates secondEnd)
    {
        double divisor = (firstStart.x-firstEnd.x)*(secondStart.y-secondEnd.y)-(firstStart.y-firstEnd.y)*(secondStart.x-secondEnd.x);
        if(divisor == 0) return false;
        intersectionPoint = new Coordinates(((firstStart.x*firstEnd.y-firstStart.y*firstEnd.x)*(secondStart.x-secondEnd.x)-(firstStart.x-firstEnd.x)*(secondStart.x*secondEnd.y-secondStart.y*secondEnd.x))/divisor,
                                            ((firstStart.x*firstEnd.y-firstStart.y*firstEnd.x)*(secondStart.y-secondEnd.y)-(firstStart.y-firstEnd.y)*(secondStart.x*secondEnd.y-secondStart.y*secondEnd.x))/divisor
        );

        return ((intersectionPoint.x>=firstStart.x&&intersectionPoint.x<=firstEnd.x)||(intersectionPoint.x<=firstStart.x&&intersectionPoint.x>=firstEnd.x))&&
                ((intersectionPoint.y>=firstStart.y&&intersectionPoint.y<=firstEnd.y)||(intersectionPoint.y<=firstStart.y&&intersectionPoint.y>=firstEnd.y))&&
                ((intersectionPoint.x>=secondStart.x&&intersectionPoint.x<=secondEnd.x)||(intersectionPoint.x<=secondStart.x&&intersectionPoint.x>=secondEnd.x))&&
                ((intersectionPoint.y>=secondStart.y&&intersectionPoint.y<=secondEnd.y)||(intersectionPoint.y<=secondStart.y&&intersectionPoint.y>=secondEnd.y));
    }

    static class Coordinates{
        double x;
        double y;
        double heading;
        public Coordinates(double x, double y, double heading){
            this.x = x;
            this.y = y;
            this.heading = heading;
        }

        public Coordinates(double x, double y){
            this.x = x;
            this.y = y;
            this.heading = Double.NaN;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    ", heading=" + heading +
                    '}';
        }

        @Override
        protected Coordinates clone(){
            return new Coordinates(x,y,heading);
        }
    }

    static Coordinates currentCoordinates = new Coordinates(.0,.0,.0);

    static double t_last = 0;

    static double angVelocity_last = 0;
    static double linVelocityX_last = 0;
    static double linVelocityY_last = 0;

    static void updateOdometry(double linVelocity, double angVelocity, double timestamp){

        double dt = timestamp-t_last;
        t_last = timestamp;

        double deltaHead = (angVelocity+angVelocity_last)*dt/2.0;

        //double deltaHead = (angVelocity)*dt;

        double linVelocityX = linVelocity*cos(currentCoordinates.heading+deltaHead);
        double linVelocityY = linVelocity*sin(currentCoordinates.heading+deltaHead);

        //currentCoordinates.x += (linVelocityX+linVelocityX_last)*dt/2.0;
        currentCoordinates.x += (linVelocityX)*dt;
        //currentCoordinates.y += (linVelocityY+linVelocityY_last)*dt/2.0;
        currentCoordinates.y += (linVelocityY)*dt;

        currentCoordinates.heading += deltaHead;

        angVelocity_last = angVelocity;
        linVelocityX_last = linVelocityX;
        linVelocityY_last = linVelocityY;

    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in).useLocale(Locale.FRENCH);
        int N = input.nextInt();
        double t = input.nextDouble();
        LinkedList<Coordinates> pointList = new LinkedList<>();
        pointList.add(currentCoordinates.clone());
        for(int i = 1; i<=N; i++)
        {
            double linVel = input.nextDouble();
            double angVel = input.nextDouble();
            updateOdometry(linVel,angVel,i*t);
            pointList.add(currentCoordinates.clone());
            for(int j = 1; j<i-1; j++)
            {
                if(intersects(pointList.get(j-1),pointList.get(j),pointList.get(i-1),pointList.get(i))) {
                    System.out.println(intersectionPoint);
                    System.out.println(currentCoordinates);
                    System.out.println((int)sqrt(intersectionPoint.x * intersectionPoint.x + intersectionPoint.y * intersectionPoint.y));
                    System.exit(0);
                }
            }
        }
    }
}
