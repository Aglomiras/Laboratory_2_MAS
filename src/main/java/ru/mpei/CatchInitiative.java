package ru.mpei;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

public class CatchInitiative extends Behaviour {
    @Override
    public void action() {
        ACLMessage receive = myAgent.receive();

        List<Double> result = new ArrayList<>();
        int count = 0;

        if (receive != null) {
            int chislo = receive.getContent().split(",").length;

            if ((chislo == 4) && receive.getContent().split(",")[chislo-1].equals(myAgent.getLocalName())) {
                System.out.println("ПОЙМАЛ" + receive.getSender().getLocalName());
                count++;

                String[] dateRes = receive.getContent().split(",");

                for (int i = 0; i < dateRes.length - 1; i++) {
                    result.add(Double.parseDouble(dateRes[i]));
                }

            } else {
                block();
            }
        } else {
            block();
        }

        if (count == 3) {
            System.out.println("count " + count + " " + myAgent.getLocalName());
            System.out.println(result + myAgent.getLocalName());
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
