class Fibonacci {
    public static void main(String[] args){
        long startTid = System.nanoTime();
        Fibonacci f = new Fibonacci();                  //2
        long argument = Integer.parseInt(args[0]);      //3
        if (argument < 0) argument = 0;                 //1
        System.out.println("f(" + argument + ") = " + f.fibonacci(argument)); //5
            
            long endTime = System.nanoTime();
            long totalTime = endTime - startTid;
            System.out.println(totalTime);
        }

        long fibonacci(long n) {                                            //n=1       //n=2               //n=3
        if (n <= 1) {                                           //1         //1         //1                 // 1
            return n;                                           //1         //1         
        } else {                                                //1             
            return fibonacci(n - 1) + fibonacci(n - 2);         //6                     // + 2 + 2          //  6 + n=2 (11) + n=1 (2) 
        }
    }
}
