import java.util.ArrayList;
import java.util.List;
public class Priority implements Algorithm {
    private int index;
    private double waitTime = 0;
    private double turnaroundTime = 0;
    private int currentTime = 0;
    List<Task> queue = new ArrayList<Task>();
    private int count = 0;
    private double totalWait = 0;
    private double totalTurnAround = 0;
    private double avgWait = 0;
    private double avgTurnAround = 0;

    public Priority(List<Task> queue) {
        this.queue = queue;
        System.out.println("\nPriority\n");
        schedule();

    }
    public void schedule(){

        int length = this.queue.size();
        for (int i = 0; i < length - 1; i++)
            for (int j = 0; j < length - i - 1; j++)
                if (queue.get(j).getPriority() > queue.get(j + 1).getPriority() ){
                    // swap arr[j+1] and arr[j]
                    Task temp = queue.get(j);
                    queue.set(j, queue.get(j + 1));
                    queue.set(j + 1, temp);
                }
        while( this.queue.size()!=0) {
            CPU.run(pickNextTask(), pickNextTask().getBurst());
            waitTime = currentTime;
            totalWait = totalWait + waitTime;
            System.out.println("Task " + pickNextTask().getName() + " finished.\n");
            currentTime = currentTime + pickNextTask().getBurst();
            turnaroundTime = currentTime;
            totalTurnAround = totalTurnAround + turnaroundTime;
            queue.remove(index);
            ++count;
            if(this.queue.size() == 0){
                avgWait = totalWait / count;
                avgTurnAround = totalTurnAround / count;
                System.out.println("Average Wait Time " + avgWait);
                System.out.println("Average Turn Around Time " + avgTurnAround);
            }
        }
    }

    @Override
    public Task pickNextTask(){
        return queue.get(index);
    }


}

