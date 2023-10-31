package ru.mpei.Draft1;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

public class CatchInitiative extends Behaviour {
    private MessageTemplate messageTemplate;
    private double X;
    private double delta;
    private List<String> agents = List.of("Agent1", "Agent2", "Agent3");
    @Override
    public void onStart() {
        this.messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }

    @Override
    public void action() {
        ACLMessage receiveMessage = myAgent.receive(messageTemplate);
        if (receiveMessage != null) {
            System.out.println("Old leader: " + receiveMessage.getSender().getLocalName());
            System.out.println("New leader: " + myAgent.getLocalName());

            this.X = Double.parseDouble(receiveMessage.getContent().split(",")[0]);
            this.delta = Double.parseDouble(receiveMessage.getContent().split(",")[1]);
            System.out.println("Значение x: " + X + "\nЗначение delta: " + delta);

            if (delta < 0.001) {
                SupportiveFunctionAgents support = new SupportiveFunctionAgents();

                double xProv = Double.parseDouble(support.Agent1(X)) + Double.parseDouble(support.Agent2(X)) + Double.parseDouble(support.Agent3(X));
                double xProvDif = Double.parseDouble(support.Agent1(X - 3.14159 * 2)) + Double.parseDouble(support.Agent2(X - 3.14159 * 2)) + Double.parseDouble(support.Agent3(X - 3.14159 * 2));
                double xProvSum = Double.parseDouble(support.Agent1(X + 3.14159 * 2)) + Double.parseDouble(support.Agent2(X + 3.14159 * 2)) + Double.parseDouble(support.Agent3(X + 3.14159 * 2));

                if (xProvDif < xProv && xProvDif < xProvSum) {
                    myAgent.addBehaviour(new InitiateDistributedCalculation(this.getAgent(), 1000, agents, X - 3.14159, delta));
                    System.out.println("Был найден локальный экстремум, необходимо проверить значения слева!");
                } else if (xProvSum < xProv && xProvSum < xProvDif) {
                    myAgent.addBehaviour(new InitiateDistributedCalculation(this.getAgent(), 1000, agents, X + 3.14159, delta));
                    System.out.println("Был найден локальный экстремум, необходимо проверить значения справа!");
                } else {
                    System.out.println("Конец расчета!");
                }

            } else {
                myAgent.addBehaviour(new InitiateDistributedCalculation(this.getAgent(), 1000, agents, X, delta));
            }
        } else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }
}
