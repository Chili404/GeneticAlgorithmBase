public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();

        System.out.println("Hello");
        String target = "To be or not to be";
        double mutationRate = 0.005;
        int popmax = 1000;

        Population pop = new Population(target, mutationRate, popmax);
        while(pop.isFinished() != true) {
            pop.naturalSelection();
            pop.generateNext();
            pop.calcFitness();
            pop.evaluate();
            System.out.println("Generation = " + pop.generations);
            System.out.println("Best Child: " + pop.bestChild);
        }
        System.out.println("Final Child is " + pop.bestChild);
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("Time is " + (totalTime / (double)1000000000));
    }
}
