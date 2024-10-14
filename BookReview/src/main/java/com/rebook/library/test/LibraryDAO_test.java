package com.rebook.library.test;

import com.rebook.library.model.LibraryDTO;
import com.rebook.library.repository.LibraryDAO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LibraryDAOTest {

    private static LibraryDAO dao;

    @BeforeAll
    static void setUp() {
        dao = LibraryDAO.getInstance();
    }

    @Test
    void testListLibrary() {
        ArrayList<LibraryDTO> libraries = dao.listLibrary();
        
        // 결과 리스트가 비어있지 않은지 확인
        assertNotNull(libraries, "Library list should not be null");
        assertTrue(libraries.size() > 0, "Library list should contain some libraries");
        
        // 첫 번째 항목을 출력하여 확인
        if (!libraries.isEmpty()) {
            LibraryDTO firstLibrary = libraries.get(0);
            System.out.println("First Library: " + firstLibrary.getName() + ", Address: " + firstLibrary.getAddress());
        }
    }

    @Test
    void testSearchLib() {
        String searchKeyword = "서울"; // 예시 검색어
        ArrayList<LibraryDTO> searchResults = dao.searchLib(searchKeyword);
        
        // 검색 결과 리스트가 비어있지 않은지 확인
        assertNotNull(searchResults, "Search results should not be null");
        assertTrue(searchResults.size() > 0, "Search results should contain matching libraries");
        
        // 첫 번째 항목을 출력하여 확인
        if (!searchResults.isEmpty()) {
            LibraryDTO firstLibrary = searchResults.get(0);
            System.out.println("Search Result - First Library: " + firstLibrary.getName() + ", Address: " + firstLibrary.getAddress());
        }
    }

    @AfterAll
    static void tearDown() {
        dao = null;
    }
}
