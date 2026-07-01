package org.example.spring_aop.service;

public class MemberServiceImpl implements MemberService {
    @Override
    public String register(String id) {
        sleep(50);
        return "register: " + id;
    }

    private void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
    }
}