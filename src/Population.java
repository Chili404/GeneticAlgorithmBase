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

    void generateNext() {

        double totalFitness = 0;
        double maxFitness = 0.0;
        for(int i = 0; i < popmax; i++) {
            totalFitness += population.get(i).getFitness();
            if(population.get(i).getFitness() > maxFitness) {
                maxFitness = population.get(i).getFitness();
            }
        }
        //normalize fitness values
        for(int i = 0; i < popmax; i++) {
            population.get(i).fitness = population.get(i).getFitness() / totalFitness;
        }


        Random rand = new Random();
        ArrayList<DNA> placeholder = new ArrayList<>();
        for(int i = 0; i < popmax; i++) {
            DNA parentA = acceptReject(totalFitness, maxFitness);
            DNA parentB = acceptReject(totalFitness, maxFitness);
            DNA child = parentA.crossover(parentB);
            child.mutate(mutationRate);
            placeholder.add(child);
        }
        population = placeholder;
        generations++;
    }

    DNA acceptReject(double totalFitness, double maxFitness) {
        Random rand = new Random();
        //Method1
        double thresholdValue = rand.nextDouble();
        int index = 0;
        while(thresholdValue > 0) {
            thresholdValue -= population.get(index).getFitness();
            index++;
        }
        index--;
        return population.get(index);

        //Method2
        /*
        while(true) {
            int index = rand.nextInt(popmax);
            DNA partner = population.get(index);
            double r = rand.nextDouble() * maxFitness;
            if(r < partner.fitness) {
                return partner;
            }

        }*/
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
