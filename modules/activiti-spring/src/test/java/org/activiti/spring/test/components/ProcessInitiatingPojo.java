package org.activiti.spring.test.components;


import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.spring.annotations.BusinessKey;
import org.activiti.spring.annotations.ProcessVariable;
import org.activiti.spring.annotations.StartProcess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * simple class that demonstrates the annotations to implicitly handle annotation-driven process managment
 *
 * @author Josh Long
 * @since 5.3
 */
public class ProcessInitiatingPojo {

    private Logger log = LoggerFactory.getLogger(getClass());

    private int methodState = 0;

    private ScopedCustomer customer;

    public void reset() {
        this.methodState = 0;
    }

    public void setCustomer(ScopedCustomer customer) {
        this.customer = customer;
    }

    public void logScopedCustomer(ProcessInstance processInstance) {
        System.out.println("ProcessInstance ID:" + processInstance.getId() + "; Name: " + this.customer.getName());
    }

    @StartProcess(processKey = "b")
    public void startProcess(@ProcessVariable long customerId) {
        log.info("starting 'b' with customerId # {}", customerId);
        this.methodState += 1;
        log.info("up'd the method state");
    }

    public int getMethodState() {
        return methodState;
    }

    @StartProcess(processKey = "waiter", returnProcessInstanceId = true)
    public String startProcessA(@ProcessVariable("customerId") long cId) {
        return null;
    }

    @StartProcess(processKey = "waiter")
    public ProcessInstance enrollCustomer(@BusinessKey String key, @ProcessVariable long customerId) {
        return null;
    }

    @StartProcess(processKey = "component-waiter")
    public void startScopedProcess(@ProcessVariable long customerId) {
        log.info("start scoped 'component-waiter' process.");

    }


}
