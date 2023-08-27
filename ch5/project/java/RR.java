
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class RR implements Algorithm{
    private int index = 0;
    List<Task> queue = new ArrayList<>();
    private double currentTime = 0;
    private double totalTurnAround = 0;
    private double totalWait = 0;
    private double n; //number of processes in queue. value assigned in constructor.
    private double avgWait = 0;
    private double avgTurnAround = 0;
    private double totalBurst = 0;
    public void schedule(){
        while(this.queue.size() != 0) {
            //if remaining burst is greater than 10, subtract and increment queue index to work on next task.
            if (pickNextTask().getBurst() > 10) {
                CPU.run(pickNextTask(), 10);
                pickNextTask().setBurst(pickNextTask().getBurst() - 10);
                currentTime += 10;
                if(index >= queue.size()-1){
                    index = 0;
                }else{
                    index++;
                }
            }
            //if remaining burst is less than or equal to 10, set remaining burst to 0 and remove task from queue.
            //do not increment as this will lead to task being skipped, since one item was removed from the queue.
            else {
                CPU.run(pickNextTask(), pickNextTask().getBurst());
                currentTime += pickNextTask().getBurst();
                totalTurnAround += currentTime;
                pickNextTask().setBurst(0);
                System.out.println("Task " + pickNextTask().getName() + " finished.\n");
                queue.remove(index);
            }

            if(this.queue.size() == 0){
                totalWait = (totalTurnAround - totalBurst) ;
                avgWait = totalWait / n ;
                avgTurnAround = totalTurnAround / n;
                System.out.println("Average Wait Time " + avgWait);
                System.out.println("Average Turn Around Time " + avgTurnAround);
            }
        }

    }
    public Task pickNextTask(){
        return queue.get(index);
    }

    public RR(List<Task> queue){
        System.out.println("\nRound Robin\n");
        this.queue = queue;
        n = queue.size();
        findTotalburst();
        schedule();
    }

    //gets total bursts time. sets double totalBurst before scheduling begind and bursts are reduced.
    private void findTotalburst(){
        for(int i = 0; i < queue.size(); i++)
        {
            Task task = queue.get(i);
            totalBurst += task.getBurst();
        }
    }
}
