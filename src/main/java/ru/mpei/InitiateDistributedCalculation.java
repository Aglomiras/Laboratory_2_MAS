package ru.mpei;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.List;

public class InitiateDistributedCalculation extends Behaviour {
    @Override
    public void action() {
        ACLMessage receive = myAgent.receive();
        if (receive != null) {
            String[] dateStr = receive.getContent().split(",");

            if (dateStr.length == 2) {
                System.out.println("Начало расчета " + myAgent.getLocalName());

                SupportiveFunctionAgents ag = new SupportiveFunctionAgents();
                List<Double> arg = null;

                if (myAgent.getLocalName().equals("Agent1")) {
                    arg = ag.Agent1(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
                } else if (myAgent.getLocalName().equals("Agent2")) {
                    arg = ag.Agent2(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
                } else if (myAgent.getLocalName().equals("Agent3")) {
                    arg = ag.Agent3(Double.parseDouble(dateStr[0]), Double.parseDouble(dateStr[1]));
                } else {
                    System.out.println("Error");
                }

                String argStr = "";
                for (int i = 0; i < arg.size(); i++) {
                    argStr = argStr + String.valueOf(arg.get(i)) + ",";
//                    if (i != arg.size() -1 ) {
//                        argStr = argStr + String.valueOf(arg.get(i)) + ",";
//                    } else {
//                        argStr = argStr + String.valueOf(arg.get(i));
//                    }
                }
                argStr = argStr + receive.getSender().getLocalName();

                System.out.println(argStr + " " + myAgent.getLocalName());
                System.out.println(receive.getSender().getLocalName());

                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setContent(argStr);
                message.addReceiver(new AID(receive.getSender().getLocalName(), false));
                myAgent.send(message);

            } else {
                block();
            }

        } else {
            block();
        }
    }

    @Override
    public void onStart() {

    }

    @Override
    public boolean done() {
        return false;
    }
}
