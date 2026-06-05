package org.example;

public interface AccountBook {
    void addAccount();    // 1. 내역 추가
    void showAccount();   // 2. 내역 조회
    void deleteAll();     // 3. 전체 삭제
    void deleteItem();    // 4. 내역 삭제
    void showTotal(); // 6. 총 지출
    void updateAccount(); // 7. 수정
}
