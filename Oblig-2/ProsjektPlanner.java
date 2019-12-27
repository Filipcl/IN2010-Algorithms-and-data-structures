import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.LinkedList;

class ProsjektPlanner {
  private int numTasks;
  private HashMap<Integer, Task> taskMap;
  private int shortestTime = 0;
  private boolean hasCycle = false;
  private String cycle = "";

  ProsjektPlanner(int numTasks, HashMap<Integer, Task> taskMap) {
    this.numTasks = numTasks;
    this.taskMap = taskMap;
  }

  // Opprettet tasks fra innlest fil
  public static ProsjektPlanner lesFraFil(File fil) throws FileNotFoundException {
    Scanner inn = new Scanner(fil);
    String linje;
    linje = inn.nextLine();
    int numTask = Integer.parseInt(linje);
    HashMap<Integer, Task> taskm = new HashMap<Integer, Task>();

    while (inn.hasNextLine()) {
      linje = inn.nextLine();

      // Sjekker for tomme linjer
      while (linje.isEmpty()) {
        linje = inn.nextLine();
      }

      String[] biter = linje.split("\\s+");
      int taskId = Integer.parseInt(biter[0]);
      String taskName = biter[1];
      int time = Integer.parseInt(biter[2]);
      int numManPower = Integer.parseInt(biter[3]);

      // antall dependencyEdges er lengden på biter - 5, dersom vi ikke har 0 i lista
      int[] dependencyEdges = new int[biter.length - 5];
      for (int i = 0; i < biter.length - 5; i++) {
        dependencyEdges[i] = Integer.parseInt(biter[i + 4]);
      }

      // Lager ny task
      Task task = new Task(taskId, taskName, time, numManPower, dependencyEdges, dependencyEdges.length);
      taskm.put(taskId, task);
    }

    // Legger til outEdges på hver task
    for (Task t : taskm.values()) {
      for (int i : t.dependencies) {
        taskm.get(i).outEdges.add(t);
        taskm.get(i).numOutEdges++;
        taskm.get(i).numOutEdges1++;
      }
    }

    // Legger til inEdges på hver task
    for (Task t1 : taskm.values()) {
      for (Task t2 : t1.outEdges) {
        t2.inEdges.add(t1);
      }
    }
    inn.close();
    return new ProsjektPlanner(numTask, taskm);
  }

  // Kaller på den rekursive funksjonen cycleSearch
  public boolean hasCycle() {
    resetGraph();
    // Dersom funksjonen finner en cycle, returneres den med en gang
    for (Task t : taskMap.values()) {
      if (t.cntPredecessors > 0) {
        if (cycleSearch(t, "")) {
          return true;
        }
      }
    }
    return false;
  }

  // Funksjonen tar imot en task og sjekker etter cycler 
  public boolean cycleSearch(Task t, String currentTask) {

    // fant cycle og setter hasCycle til true
    if (t.status == "searching") {
      t.status = "searching";
      currentTask += t.id;
      this.cycle = currentTask;
      this.hasCycle = true;
      return true;
    }
    // ferdig med sjekk
    if (t.status == "done") {
      return false;
    }

    // Sjekker etter cycle
    if (t.status == "noCycle") {
      t.status = "searching";
      currentTask += t.id;

      // returnerer true dersom funksjonen finner en cycle
      for (Task outEdge : t.outEdges) {
        if (cycleSearch(outEdge, currentTask)) {
          return true;
        }
      }
      t.status = "done";
    }
    return false;
  }

  // Topologisk sorterer Tasks inn i en prioritetskø. 
  public boolean topologiskSort() {
    this.resetGraph();
    LinkedList<Task> queue = new LinkedList<Task>();
    Task task;
    int counter = 0;

    for (Task t : taskMap.values()) {
      t.earliestStart = 0;
      if (t.cntPredecessors == 0) {
        t.earliestStart = 0;
        queue.add(t);
      }
    }

    while (!queue.isEmpty()) {
      task = queue.removeFirst();
      counter++;
      for (Task t : task.outEdges) {
        t.cntPredecessors--;
        // Neste task sin starttid må være lik predecessor pluss tiden det tar for predecessor task
        if (t.earliestStart < (task.earliestStart + task.time)) {
          t.earliestStart = task.earliestStart + task.time;
        }
        // Sjekker om tasken som avsluttes er lengre enn nåværende shortestTime
        if (t.earliestStart + t.time > this.shortestTime) {
          this.shortestTime = t.earliestStart + t.time;
        }
        if (t.cntPredecessors == 0) {
          queue.add(t);
        }
      }
    }

    queue = new LinkedList<Task>();
    // Legger inn en task dersom ingen avhengier av den
    for (Task t : taskMap.values()) {
      t.latestStart = this.shortestTime;
      if (t.numOutEdges == 0) {
        t.latestStart = this.shortestTime - t.time;
        queue.add(t);
      }
    }

    // Itererer gjennom predecessor tasks (inEdges) dersom lateStart av forrige task er senere enn latestStart av denne task minus tiden for forrige task, oppdater slik at latestStart av forrige task er lik dette. Dette passer på at thisTask kan starte akkurat på sin latestStart-tid
    while (!queue.isEmpty()) {
      Task thisTask = queue.remove();
      for (Task predTask : thisTask.inEdges) {
        if (predTask.latestStart > (thisTask.latestStart - predTask.time)) {
          predTask.latestStart = thisTask.latestStart - predTask.time;
        }
        predTask.numOutEdges--;

        if (predTask.numOutEdges == 0) {
          queue.add(predTask);
        }
      }
    }
    
    // Sjekker om en task blir lagt til i køen flere enn en ganger
    if (counter < this.numTasks) {
      this.hasCycle = true;
      return false;
    } 
     for (Task t : taskMap.values()) {
      t.slack = t.latestStart - t.earliestStart;
    }

    return true;
  }


  public void printOptimal() {
    // Sorterer grafen topologisk
    if (!this.topologiskSort()) {
      return;
    }
    int currentManPower = 0;
      
    // Itererer gjennom tiden og sjekker om det utføres noe task
    for (int i = 0; i <= shortestTime; i++) {
      boolean sjekk = false;
      for (Task t : taskMap.values()) {
        //Sjekker om tasken starter       
        if (t.earliestStart == i) {
          if (!sjekk) {
            System.out.println("Current time: " + i);
            sjekk = true;
          }
          System.out.println("Starting: " + t.id);
          currentManPower += t.manPower;
        }
        // sjekker om tasken slutter
        else if (t.earliestStart + t.time == i) {
          if (!sjekk) {
            System.out.println("Current time: " + i);
            sjekk = true;
          }
          System.out.println("Finished: " + t.id);
          currentManPower -= t.manPower;
        }
      }
      // skrive ut antall manpower dersom noe skjer i denne tiden
      if (sjekk) {
        System.out.println("Current Manpower: " + currentManPower);
        System.out.println();
      }
    }
    System.out.println("--- Shortest possible time: " + shortestTime + " ---");
  }

  public void skrivUtInfo() {
    System.out.println("----- PROJECT INFO -----");
    if (this.hasCycle) {
      System.out.println("This project contains a cycle and is therefor not realizable.");
      System.out.println("Tasks in cycle: " + this.cycle);
    }
    for (Task t : taskMap.values()) {
      System.out.println();
      System.out.println("Task id: " + t.id);
      System.out.println("Task name: " + t.name);
      System.out.println("Required time: " + t.time);
      System.out.println("Required manpower: " + t.manPower);
      
      if (!hasCycle) {
        System.out.println("Earliest start time: " + t.earliestStart);
        System.out.println("Latest start time: " + t.latestStart);
        System.out.println("Slack: " + t.slack);
        System.out.println("Critical: " + (t.slack == 0));
      }

      String dependencies = "";
      for (Task out : t.outEdges) {
        dependencies += out.id + " ";
      }
      System.out.println("Depening on this task : " + dependencies);

    } 
  }

    // Hjelpemetode for å nullstiller grafen. 
    private void resetGraph() {
      this.hasCycle = false;
      this.shortestTime = 0;
  
      for (Task t : taskMap.values()) {
        t.status = "noCycle";
        t.earliestStart = 0;
        t.latestStart = 0;
        t.slack = 0;
        t.numOutEdges = t.numOutEdges1;
        t.cntPredecessors = t.cntPredecessors;
      }
    }

}