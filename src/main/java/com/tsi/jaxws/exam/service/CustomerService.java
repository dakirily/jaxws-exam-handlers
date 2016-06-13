package com.tsi.jaxws.exam.service;

import com.tsi.jaxws.exam.bean.CustomerBean;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * Web service WSDL will be available using URL
 * http://localhost:8080/WebServiceExam01/CustomerService?wsdl
 * 
 * @author Даниил
 */
@WebService(serviceName = "CustomerService", targetNamespace = "http://com.tsi.jaxws.exam")
@HandlerChain(file = "handler-chain.xml")
public class CustomerService {

    /**
     * Web service operation
     *
     * @return
     */
    @WebMethod(operationName = "get")
    @RequestWrapper(localName = "get", targetNamespace = "http://com.tsi.jaxws.exam")
    @ResponseWrapper(
            localName = "customer-response", 
            targetNamespace = "http://com.tsi.jaxws.exam",
            className = "com.tsi.jaxws.exam.bean.CustomerResponse"
    )
    public @WebResult(
            name = "customer",
            targetNamespace = "http://com.tsi.jaxws.exam"
    ) CustomerBean get() {
        CustomerBean customer = new CustomerBean();
        customer.setFirstname("Bill");
        customer.setLastname("Cypher");
        customer.setAge(Integer.MAX_VALUE);
        return customer;
    }

}
