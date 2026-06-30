package org.example.springfirst.ch05.ex_5_4.service;

// 아무 것도 하지 않는 대역
// 개발중에 메일 발송이 핵심이 아닌 상황에 사용하는 더미
// 운영 환경에서는 등으로 실제 발송하는 구현을 해주면 된다. 코드 변경 없이 설정만으로 가능

public class DummyMailSender implements MailSender {
    @Override
    public void send(Mail mail) {

    }
}
