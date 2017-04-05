package bank;

import model.bank.BankInterestRequest;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class BankMessageListener implements MessageListener {

    JMSBankFrame frame;

    public BankMessageListener(JMSBankFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        BankInterestRequest bir = null;
        try {
            bir = (BankInterestRequest) ((ObjectMessage) message).getObject();
            System.out.println(bir);
            frame.add(bir);
            message.getJMSReplyTo();
        } catch (JMSException e) {
            e.printStackTrace();
        }
//        if(lr !=null) {
//            frame.;
//        }
    }
}
