import java.util.LinkedList;

class Task {
  int id, time, manPower;
  String name;
  int earliestStart, latestStart, slack;
  int cntPredecessors, numOutEdges, numOutEdges1;
  String status;

  int[] dependencies;
  LinkedList<Task> outEdges = new LinkedList<Task>();
  LinkedList<Task> inEdges = new LinkedList<Task>();

  Task(int id, String name, int time, int manPower, int[] dependencies, int cntPred){
    this.id = id;
    this.name = name;
    this.time = time;
    this.manPower = manPower;
    this.cntPredecessors = cntPred;
    this.dependencies = dependencies;
  }

}