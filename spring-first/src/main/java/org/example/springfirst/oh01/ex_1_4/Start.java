package org.example.springfirst.oh01.ex_1_4;

// 문제점
// 클라이언트는 기존에 UserDAO가 직접 담당하는 기능인
// 어떤 ConnectionMake 구현 클래스를 사용할지를 결정하는 기능을 담당하게 되었음
// 원래 Start는 UserDAO의 기능 테스트용으로 만든것인데 다른 책임도 담당하게 되어서 문제점이 있다

// 오브젝트 팩토리
// 객체의 생성 방법을 결정하고 그렇게 만들어진 오브젝트를 돌려주는 일을 하는 오브젝트를 팩토리 라고 한다

// 제어의 역전
// 오브젝트가 자신이 사용할 오브젝트를 스스로 선택 및 생성까지 않고,
// 언제 어떻게 만들어짖는지조차 모른 채 그 제어권을 외부의 넘기는것

// UserDAO 입장에서 두 가지 제어권이 사라졌음
// 1. 어떤 ConnectionMaker를 줄지에 대한 제어권
//  public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
//      this.simpleConnectionMaker = simpleConnectionMaker;
//  }

// 2. 자기 자신의 생성에 대한 제어권
//  public UserDAO userDao() {
//      UserDAO userDAO = new UserDAO(connectionMaker());  // 생성 + 관계설정을 팩토리가 담당
//      return userDAO;
//  }

//  private SimpleConnectionMaker connectionMaker() {
//      return new DConnectionMaker();
//  }
// UserDAO가 언제, 어떤 의존성과 함께 만들어지는지를 DaoFactory가 결정한다.

//  원래 테스트용이던 클라이언트가 "어떤 ConnectionMaker를 쓸지 결정하는 책임"까지 떠맡던 문제를, 팩토리를 도입해
//  "컴포넌트 역할 오브젝트"와 "구조를 결정하는 오브젝트"를 분리한 것이다.

// 템플릿 메서드는 제어의 역전이라는 개념을 활용해 문제를 해결하는 디자인 패턴이라고 볼 수 있다.

// 스프링은 제어의 역전을 모든 기능의 기초가 되는 기반기술로 삼고 있으며,
// 제어의 역전을 극한까지 사용하는 프레임워크이다.

import org.example.springfirst.oh01.ex_1_4.dao.DaoFactory;
import org.example.springfirst.oh01.ex_1_4.dao.UserDAO;

public class Start {
    public static void main(String[] args) {
        UserDAO dao = new DaoFactory().userDAO();
    }

}
