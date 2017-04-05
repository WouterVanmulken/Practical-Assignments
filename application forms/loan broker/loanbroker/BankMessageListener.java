package loanbroker;

import loanclient.Messager;
import model.bank.BankInterestReply;
import model.loan.LoanReply;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Created by Wouter Vanmulken on 5-4-2017.
 */
public class BankMessageListener implements MessageListener {
    LoanBrokerFrame frame;

    public BankMessageListener(LoanBrokerFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onMessage(Message message) {
        BankInterestReply bankInterestReply = null;
        String correlationId=null;
        try {
            bankInterestReply = (BankInterestReply)((ObjectMessage) message).getObject();
            correlationId = message.getJMSCorrelationID();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        if (bankInterestReply != null) {

            frame.add(bankInterestReply,correlationId);
            LoanReply loanReply = new LoanReply(bankInterestReply.getInterest(),bankInterestReply.getQuoteId());
            Messager.Send(loanReply, "myFourthDestination",correlationId);
        }

    }
}
