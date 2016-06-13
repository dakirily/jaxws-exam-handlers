package com.tsi.jaxws.exam.handler.soap;

import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Web service SOAP handler, which can perform operations on message headers.
 * 
 * @author Даниил
 */
public class SoapHandler01 implements SOAPHandler<SOAPMessageContext> {

    /** 
     * Internal logger
     */
    private final Logger logger = LoggerFactory.getLogger(SoapHandler01.class);

    /**
     * Handles message on both request and response routes.
     * 
     * @param context SOAPMessageContext instance
     * 
     * @return true / false
     */
    @Override
    public boolean handleMessage(SOAPMessageContext context) {

        // check message direction
        Boolean isRequest = !(Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        // log execution
        logger.info((isRequest ? "Request route. " : "Response route. ") + "Calling SOAP handler " + this.getClass().getSimpleName());
        
        try {
            
            // retrieve SOAPMessage from context
            SOAPMessage msg = context.getMessage();
            SOAPEnvelope envelope = msg.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            
            // add header
            if (isRequest) {
                header.addHeaderElement(new QName("http://com.tsi.jaxws.exam", "request-soap-header-01"));
            } else {
                header.addHeaderElement(new QName("http://com.tsi.jaxws.exam", "response-soap-header-01"));
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }
    
    @Override
    public Set<QName> getHeaders() {
        return new HashSet<>();
    }
    
    @Override
    public void close(MessageContext context) {
        
    }

}
