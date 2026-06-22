package org.example.springfirst.oh01.ex_1_3;

import org.example.springfirst.oh01.ex_1_3.dao.ConnectionMaker;
import org.example.springfirst.oh01.ex_1_3.dao.NConnectionMaker;
import org.example.springfirst.oh01.ex_1_3.dao.UserDAO;

// 개방 폐쇄 원칙(OCP)

// * 전략 패턴
// 자신의 기능 맥락에서, 필요에 따라 변경이 필요한 알고리즘을 인터페이스를 통해 통째로 외부로 분리시키고,
// 이를 구현한 구체적인 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴이다.
// UserDAO는 컨텍스트에 해당한다.
// 컨텍스트(UserDAO)를 사용하는 클라이언트(Start)는
// 컨텍스트가 사용할 전략(SimpleConnectionMaker를 구현한 클래스)을 컨텍스트의 생성자 등을 통해 제공해주는 게 일반적이다.

public class Start {
    public static void main(String[] args) {
        ConnectionMaker conn = new NConnectionMaker();
        UserDAO userDAO = new UserDAO(conn);

    }
}
