package ru.mpei.Laboratory_2;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.Random;

public class InitiateDistributedCalculation extends TickerBehaviour {
    private double X;
    private double delta;
    private MessageTemplate messageTempl;
    private double sumX = 0.0;
    private double sumXDif = 0.0;
    private double sumXSum = 0.0;
    private int messageAllAgents = 0;
    private int count = 0;
    public InitiateDistributedCalculation(Agent agent, long period, double X, double delta) {
        super(agent, period);
        this.myAgent = agent;
        this.X = X;
        this.delta = delta;
    }

    @Override
    public void onStart() {
        this.messageTempl = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
    }

    @Override
    protected void onTick() {
        if (count == 0) {
            ACLMessage message1 = new ACLMessage(ACLMessage.CFP);
            message1.setContent(X + "," + delta);
            message1.addReceiver(new AID("Agent1", false));
            message1.addReceiver(new AID("Agent2", false));
            message1.addReceiver(new AID("Agent3", false));
            myAgent.send(message1);
            count++;
        }
        if (count == 1) {
            ACLMessage receive = myAgent.receive();
            if (receive != null) {
                if (receive.getPerformative() == ACLMessage.PROPOSE) {
                    this.messageAllAgents++;
                    this.sumXDif += Double.parseDouble(receive.getContent().split(",")[0]);
                    this.sumX += Double.parseDouble(receive.getContent().split(",")[1]);
                    this.sumXSum += Double.parseDouble(receive.getContent().split(",")[2]);
                }
                if (this.messageAllAgents == 3) {
                    count++;
                    System.out.println(sumXDif + " " + sumX + " " + sumXSum);
                }
            } else {
                block();
            }
        }
        if (count == 2) {
            if (sumXDif > sumX && sumXDif > sumXSum) {
                X = X - delta;
            } else if (sumXSum > sumX && sumXSum > sumXDif) {
                X = X + delta;
            } else {
                delta = delta / 2;
            }
            count++;

            String[] agents = new String[] {"Agent1", "Agent2", "Agent3"};
            String myAstel = "";

            boolean flag = true;
            while (flag == true) {
                Random r = new Random();
                int arg = r.nextInt(agents.length);
                myAstel = agents[arg];
                if (myAstel != myAgent.getLocalName()) {
                    flag = false;
                } else {
                    flag = true;
                }
            }

            ACLMessage message2 = new ACLMessage(ACLMessage.INFORM);
            message2.setContent(X + "," + delta);
            message2.addReceiver(new AID(myAstel, false));
            myAgent.send(message2);

            myAgent.removeBehaviour(this);
        }
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }

}
