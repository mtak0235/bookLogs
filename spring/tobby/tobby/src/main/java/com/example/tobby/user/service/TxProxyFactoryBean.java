package com.example.tobby.user.service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import java.lang.reflect.Proxy;

public class TxProxyFactoryBean implements FactoryBean<Object> {

  Object target;
  PlatformTransactionManager transactionManager;
  String pattern;
  Class<?> serviceInterface;

  public void setTarget(Object target) {
    this.target = target;
  }

  public void setTransactionManager(PlatformTransactionManager transactionManager) {
    this.transactionManager = transactionManager;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setServiceInterface(Class<?> serviceInterface) {
    this.serviceInterface = serviceInterface;
  }

  @Override
  public Object getObject() throws Exception {
    TransactionHandler transactionHandler = new TransactionHandler();
    transactionHandler.setTarget(target);
    transactionHandler.setPattern(pattern);
    transactionHandler.setTransactionManager(transactionManager);
    return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface},
        transactionHandler);
  }

  @Override
  public Class<?> getObjectType() {
    return serviceInterface;
  }

  @Override
  public boolean isSingleton() {
    return false;
  }
}
