  
import java.io.File;
import java.io.FileNotFoundException;

class Oblig2 {
  public static void main(String[] args) {
    String filnavn = null;
    
    if (args.length > 0) {
      filnavn = args[0];
  } else {
      System.out.println("Error! skriv :  " +"java Oblig2 <prosjektfil> " + " for aa kompilere");
      return;
  }

    // leser inn fra fil, sjekker om kompilator får riktig fil.
    File fil = new File(filnavn);
    ProsjektPlanner pp = null;
    try {
        pp = ProsjektPlanner.lesFraFil(fil);
    } 
    catch (FileNotFoundException e) {
        System.out.printf("Error: '%s'\n", filnavn +" finnes ikke");
        System.exit(1);
    }


    //Printer graf og info
    System.out.println();
    pp.hasCycle();
    pp.printOptimal();
    System.out.println();
    pp.skrivUtInfo();

  }
}