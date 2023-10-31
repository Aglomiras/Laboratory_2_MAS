package ru.mpei.Draft1;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;
import java.util.Random;

public class InitiateDistributedCalculation extends TickerBehaviour {
    private double X;
    private double delta;
    private List<String> listAg;
    private MessageTemplate messageTempl;
    private double sumX = 0.0;
    private double sumXDif = 0.0;
    private double sumXSum = 0.0;
    private int count = 0;
    private int messageAllAgents = 0;
    public InitiateDistributedCalculation(Agent agent, long period, List<String> listAg, double X, double delta) {
        super(agent, period);
        this.listAg = listAg;
        this.myAgent = agent;
        this.X = X;
        this.delta = delta;
    }

    @Override
    public void onStart() {
        messageTempl = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.CFP), MessageTemplate.MatchPerformative(ACLMessage.PROPOSE));
    }

    @Override
    protected void onTick() {
        if (count == 0) {
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.setContent(X + "," + delta);
            message.addReceiver(new AID(listAg.get(0), false));
            message.addReceiver(new AID(listAg.get(1), false));
            message.addReceiver(new AID(listAg.get(2), false));
            myAgent.send(message);
            count++;
        }
        if (count == 1) {
            ACLMessage receiveMessage = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.CFP));
            if (receiveMessage != null) {
                this.messageAllAgents++;
                this.sumXDif += Double.parseDouble(receiveMessage.getContent().split(",")[0]);
                this.sumX += Double.parseDouble(receiveMessage.getContent().split(",")[1]);
                this.sumXSum += Double.parseDouble(receiveMessage.getContent().split(",")[2]);

                if (this.messageAllAgents == 3) {
                    count++;
                    System.out.println(sumXDif + " " + sumX + " " + sumXSum);
                }
            } else {
                block();
            }
        }
        if (count == 2) {
            if (sumXDif < sumX && sumXDif < sumXSum) {
                X = X - delta;
            } else if (sumXSum < sumX && sumXSum < sumXDif) {
                X = X + delta;
            } else {
                delta = delta / 2;
            }
            count++;

            ACLMessage message1 = new ACLMessage(ACLMessage.INFORM);
            message1.setContent(X + "," + delta);
            message1.addReceiver(new AID(nextAgent(), false));
            myAgent.send(message1);
            myAgent.removeBehaviour(this);
        }
    }

    @Override
    public int onEnd() {
        return super.onEnd();
    }

    public String nextAgent() {
        String next = "";
        boolean flag = true;
        while (flag) {
            Random r = new Random();
            next = listAg.get(r.nextInt(listAg.size()));
            if (!next.equals(myAgent.getLocalName())) {
                flag = false;
            }
        }
        return next;
    }
}
