import java.util.ArrayList;
import java.util.Random;

public class DNA {
    public ArrayList<String> genes = new ArrayList<>();
    int length;

    double fitness;

    public DNA(int length) {
        this.length = length;
        for(int i =0; i < length; i++) {
            genes.add(generate());
        }
    }

    public DNA(DNA old) {
        this.length = old.length;
        this.fitness = old.getFitness();
        this.genes = new ArrayList<>(old.genes);
    }

    String generate() {
        Random r = new Random();
        int place = r.nextInt(59) + 63;
        if(place == 63)
            place = 32;
        if(place == 64)
            place = 46;
        char c = (char) place;
        return String.valueOf(c);
    }

    void calcFitness(String target) {
        int score = 0;
        for(int i = 0; i < length; i++) {
            if(target.charAt(i) == genes.get(i).charAt(0)) {
                score++;
            }
        }
        fitness = score / (double) length;
        fitness = Math.pow(fitness, 2);
    }
    public double getFitness() {
        return fitness;
    }


    public DNA crossover(DNA parent) {
        Random rand = new Random();
        int cutPoint = rand.nextInt(length);
        for(int i = 0; i < length; i++) {
            if (i > cutPoint) {
                genes.set(i, parent.genes.get(i));
            }
        }
        DNA child = new DNA(this);
        return child;
    }

    public void mutate(double mutationRate) {
        Random rand = new Random();
        for(int i = 0; i < length; i++) {
            if(rand.nextDouble() < mutationRate) {
                genes.set(i, generate());
            }
        }
    }
    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < length; i++) {
            s+=genes.get(i);
        }
        return s;
    }
}
