package ru.mpei.Draft1;

import jade.core.Agent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FunctionAgent extends Agent {
    @Override
    protected void setup() {
        System.out.println("I is " + getLocalName() + " born!");

        List<String> agents = new ArrayList<>();
        agents.add("Agent1");
        agents.add("Agent2");
        agents.add("Agent3");

        if (this.getLocalName().equals("Agent1")) {
            this.addBehaviour(new InitiateDistributedCalculation(this, 1000, agents, randomNumbers(), randomNumbers()));
        }
        this.addBehaviour(new CalcMyFunction());
        this.addBehaviour(new CatchInitiative());
    }

    public static double randomNumbers() {
        Random r = new Random();
        return r.nextDouble(10);
    }
}
