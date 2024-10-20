package com.rebook.mybook.repository.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.rebook.mybook.model.MarkDTO;
import com.rebook.mybook.repository.MarkDAO;

public class MarkDAOTest {

    private MarkDAO markDAO;

    @BeforeEach
    public void setUp() {
        // 테스트 시작 전, DAO 인스턴스 초기화
        markDAO = MarkDAO.getInstance();
    }

    @Test
    public void testListMark() {
        // listMark() 메서드를 호출하여 결과를 얻음
        ArrayList<MarkDTO> result = markDAO.listMark();

        // 결과가 null이 아닌지 확인
        assertNotNull(result);

        // 결과 리스트가 비어있지 않은지 확인 (데이터베이스에 최소한 하나의 데이터가 있다고 가정)
        assertTrue(result.size() > 0, "리스트가 비어 있습니다.");

        // 첫 번째 항목에 대한 테스트
        MarkDTO firstMark = result.get(0);
        assertNotNull(firstMark.getMembername(), "회원 이름이 null입니다.");
        assertNotNull(firstMark.getScore(), "점수가 null입니다.");
        assertNotNull(firstMark.getBookname(), "책 이름이 null입니다.");
        assertNotNull(firstMark.getCover(), "책 커버가 null입니다.");
    }

    @AfterEach
    public void tearDown() {
        // 테스트 후 정리 작업을 여기서 수행 (필요한 경우)
    }
}

