package org.example.springfirst.ch06.ex_6_2.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransactionHandler implements InvocationHandler {
    // 부가기능을 적용할 실제 오브젝트
    private Object target;

    //트랜잭션 추상화
    private PlatformTransactionManager transactionManager;

    // 이 이름으로 시작하는 메서드에만 트랜잭션 적용
    private String pattern;

    public TransactionHandler(Object target, PlatformTransactionManager transactionManager, String pattern) {
        this.target = target;
        this.transactionManager = transactionManager;
        this.pattern = pattern;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if (method.getName().startsWith(pattern)) {
            return invokeTransaction(method, args);
        }

        return method.invoke(target, args);
    }

    private Object invokeTransaction(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object invoke = method.invoke(target, args);
            transactionManager.commit(status);

            return invoke;
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }
}
