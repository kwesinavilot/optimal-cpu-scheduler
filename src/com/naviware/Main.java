package com.naviware;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int numberOfProcesses;   // total number of processes

    static ArrayList<Integer> processes;
    static ArrayList<Integer> arrivalTime;       // the arrival time of processes
    static ArrayList<Integer> burstTime;         // the burst or execution time of processes
    static ArrayList<Integer> completionTime;    // the time at which processes complete execution
    static ArrayList<Integer> turnAroundTime;    // the difference b/n completion time and arrival time
    static ArrayList<Integer> waitingTime;       // the difference between turn around time and burst time

    static float totalWaitTime = 0; // the total time it takes to wait
    static float totalTurnAroundTime = 0;   // the total turn around time

    static Scanner scanner = new Scanner(System.in);        //Scanner to receive user inputs

    public static void main(String[] args) {

        //Ask the user for their number of processes
        System.out.print("Enter no of processes: ");
        numberOfProcesses = scanner.nextInt();

        //assign proportionate values to all components of the algorithm
        processes = new ArrayList<>(numberOfProcesses);
        arrivalTime = new ArrayList<>(numberOfProcesses);
        burstTime = new ArrayList<>(numberOfProcesses);
        completionTime = new ArrayList<>(numberOfProcesses);
        turnAroundTime = new ArrayList<>(numberOfProcesses);
        //waitingTime = new ArrayList<>(numberOfProcesses);

        //Receive the arrival time and burst time for each process
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.print("Enter arrival time for Process " + (i + 1) + ": ");
            arrivalTime.add(i, scanner.nextInt());

            System.out.print("Enter burst time for Process " + (i + 1) + ": ");
            burstTime.add(i, scanner.nextInt());

            processes.add(i, i + 1);

            //completion time of current process = arrivalTime + burstTime
            completionTime.add(i, arrivalTime.get(i) + burstTime.get(i));

            //turnaround time = completion time - arrival time
            turnAroundTime.add(i, completionTime.get(i) - arrivalTime.get(i));
        }

        System.out.println("\nProcessID\tArrival Time\tBurst Time\tCompletion Time\tTurn Around");

        for(int i = 0; i < numberOfProcesses; i++) {
            System.out.println("\t" + processes.get(i) + "\t\t\t" + arrivalTime.get(i) + "\t\t\t\t" + burstTime.get(i)
                    + "\t\t\t\t" + completionTime.get(i) + "\t\t\t" + turnAroundTime.get(i));
        }

        roundRobin(processes, arrivalTime, burstTime, completionTime, turnAroundTime);
    }

    public static void roundRobin(ArrayList<Integer> processes, ArrayList<Integer> arrivalTime, ArrayList<Integer> burstTime,
                                  ArrayList<Integer> completionTime, ArrayList<Integer> turnAroundTime) {
        int temp;       //variable for holding temporal values

        System.out.print("\nEnter time quantum: ");
        int timeQuantum = scanner.nextInt();

        for (int i = 0; i < processes.size(); i++) {
            if (burstTime.get(i) > timeQuantum) {
                //process and update the burst time
                temp = burstTime.get(i) - timeQuantum;
                burstTime.set(i, temp);

                //completion time of current process = arrivalTime + burstTime
                completionTime.set(i, arrivalTime.get(i) + burstTime.get(i));

                //turnaround time for the process
                turnAroundTime.set(i, completionTime.get(i) - arrivalTime.get(i));
            } else {
                burstTime.set(i, 0);
                processes.set(i, 0);
                arrivalTime.set(i, 0);
                turnAroundTime.set(i, 0);
                completionTime.set(i, 0);
            }

            //Calculate the totals and throughput
            totalTurnAroundTime += turnAroundTime.get(i);
        }

        //Remove all elements that are 0s
        processes.removeIf(val -> (val < 1));
        burstTime.removeIf(val -> (val < 1));
        arrivalTime.removeIf(val -> (val < 1));
        turnAroundTime.removeIf(val -> (val < 1));
        completionTime.removeIf(val -> (val < 1));

        //Trim the ArrayList down to size
        processes.trimToSize();
        burstTime.trimToSize();
        arrivalTime.trimToSize();
        turnAroundTime.trimToSize();
        completionTime.trimToSize();

        //Display remaining processes and their burst times after performing Round Robin
        System.out.println("\nProcessID\tArrival Time\tBurst Time\tCompletion Time\tTurn Around");
        for(int i = 0; i < processes.size(); i++) {
            System.out.println("\t" + processes.get(i) + "\t\t\t" + arrivalTime.get(i) + "\t\t\t\t" + burstTime.get(i)
                    + "\t\t\t\t" + completionTime.get(i) + "\t\t\t" + turnAroundTime.get(i));
        }

        System.out.println("The Average Turn Around Time is : " + (totalTurnAroundTime / numberOfProcesses));    // printing average turnaround time.
    }

    public static void shortestRemainingTimeFirst() {

    }

}
