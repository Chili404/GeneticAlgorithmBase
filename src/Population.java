import java.util.ArrayList;
import java.util.Random;

public class Population {
    String target;
    int popmax;
    double mutationRate;

    public ArrayList<DNA> population = new ArrayList();
    private ArrayList<DNA> matingPool = new ArrayList<>();

    int generations = 0;
    boolean finished = false;
    String bestChild;

    public Population(String target, double mutationRate, int popmax) {
        this.target = target;
        this.popmax = popmax;
        this.mutationRate = mutationRate;
        initialize();
        calcFitness();
        evaluate();
    }
    private void initialize() {
        for(int i = 0; i < popmax; i++) {
            population.add(new DNA(target.length()));
        }
    }


    void calcFitness() {
        for(int i = 0; i < popmax; i++) {
            population.get(i).calcFitness(target);
        }
    }
    void naturalSelection() {
        ArrayList<DNA> matingPool = new ArrayList<>();
        double maxFitness = 0;
        for(int i = 0; i < popmax; i++) {
            if(population.get(i).getFitness() > maxFitness) {
                maxFitness = population.get(i).getFitness();
            }
        }

        /*Normalize the fitness value and based on that value add
        it to the mating pool x time*/
        for(int i = 0; i < popmax; i++) {
            double normalizedFitness = population.get(i).getFitness() / maxFitness;
            int n = (int) (normalizedFitness * 100);
            for(int j = 0; j < n; j++) {
                matingPool.add(population.get(i));
            }
        }
        this.matingPool = matingPool;
    }
    void generateNext() {
        Random rand = new Random();
        for(int i = 0; i < popmax; i++) {
            int a = rand.nextInt(matingPool.size());
            int b = rand.nextInt(matingPool.size());
            DNA parentA = matingPool.get(a);
            DNA parentB = matingPool.get(b);
            DNA child = parentA.crossover(parentB);
            child.mutate(mutationRate);
            population.set(i, child);
        }
        generations++;
    }

    void evaluate() {
        double best = 0.0;
        int index = 0;
        for(int i = 0; i < popmax; i++) {
            if(population.get(i).getFitness() > best) {
                best = population.get(i).getFitness();
                index = i;
            }
        }
        if(best == 1) {
            finished = true;
        }

        bestChild = population.get(index).toString();

    }

    public boolean isFinished() {
        return finished;
    }
}
