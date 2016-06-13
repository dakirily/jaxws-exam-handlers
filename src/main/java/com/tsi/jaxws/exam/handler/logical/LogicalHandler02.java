package com.tsi.jaxws.exam.handler.logical;

import com.tsi.jaxws.exam.bean.CustomerBean;
import com.tsi.jaxws.exam.bean.CustomerResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.ws.LogicalMessage;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.handler.LogicalMessageContext;
import javax.xml.ws.handler.MessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logical handler, which can perform operations on message payload.
 * 
 * @author Даниил
 */
public class LogicalHandler02 implements LogicalHandler<LogicalMessageContext> {

    /**
     * JAXB context
     */
    private final JAXBContext ctx;
    
    /**
     * Internal logger
     */
    private final Logger logger = LoggerFactory.getLogger(LogicalHandler02.class);

    /**
     * Constructor.
     * 
     * Initializes JAXB context.
     * 
     * @throws JAXBException 
     */
    public LogicalHandler02() throws JAXBException {
        ctx = JAXBContext.newInstance(CustomerResponse.class);
    }

    /**
     * Accesses message payload and performs manipulations with it.
     * 
     * @param context LogicalMessageContext instance
     * 
     * @return true / false
     */
    @Override
    public boolean handleMessage(LogicalMessageContext context) {

        // retrieve LogicalMessage from context
        LogicalMessage msg = context.getMessage();

        // check message direction
        Boolean isRequest = !(Boolean) context.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        // log execution
        logger.info((isRequest ? "Request route. " : "Response route. ") + "Calling logical handler " + this.getClass().getSimpleName());
        
        // change message body
        if (!isRequest) {
            CustomerResponse response = (CustomerResponse) msg.getPayload(ctx);
            CustomerBean customer = response.getCustomer();
            customer.setLastname(customer.getLastname() + " logical-handler-02");
            msg.setPayload(response, ctx);
        }
        return true;
    }

    @Override
    public boolean handleFault(LogicalMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {

    }

}
