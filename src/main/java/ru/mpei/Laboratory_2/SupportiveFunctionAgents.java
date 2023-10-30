package ru.mpei.Laboratory_2;

import java.util.ArrayList;
import java.util.List;

public class SupportiveFunctionAgents {
    public List<Double> Agent1(double argument, double delta) {
        List<Double> agent1 = new ArrayList<>();
        List<Double> support = List.of(argument-delta, argument, argument+delta);

        for (int i = 0; i < support.size(); i++) {
            double arg = - (support.get(i) * support.get(i)) + 5;
            agent1.add(arg);
        }

        return agent1;
    }
    public List<Double> Agent2(double argument, double delta) {
        List<Double> agent2 = new ArrayList<>();
        List<Double> support = List.of(argument-delta, argument, argument+delta);

        for (int i = 0; i < support.size(); i++) {
            double arg = 2 * support.get(i) + 2;
            agent2.add(arg);
        }

        return agent2;
    }
    public List<Double> Agent3(double argument, double delta) {
        List<Double> agent3 = new ArrayList<>();
        List<Double> support = List.of(argument-delta, argument, argument+delta);

        for (int i = 0; i < support.size(); i++) {
            double arg = Math.sin(support.get(i));
            agent3.add(arg);
        }

        return agent3;
    }
}
