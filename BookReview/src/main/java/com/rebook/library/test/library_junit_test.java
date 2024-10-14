package com.rebook.library.test;

import com.rebook.library.model.LibraryDTO;
import com.rebook.library.repository.LibraryDAO;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class library_junit_test {
	
	@Disabled
    @Test
    public void testListLibrary() {
        // LibraryDAO 인스턴스를 가져옴
        LibraryDAO dao = LibraryDAO.getInstance();
        
        // listLibrary 메서드를 호출하여 결과를 얻음
        ArrayList<LibraryDTO> libraryList = dao.listLibrary();
        
        // 결과가 null이 아닌지 확인
        assertNotNull(libraryList, "The list should not be null");
        
        // 리스트가 비어있지 않은지 확인 (데이터가 존재하는지 확인)
        assertTrue(libraryList.size() > 0, "The list should contain library data");
        
        // 리스트에 있는 데이터를 출력하여 확인
        for (LibraryDTO library : libraryList) {
            System.out.println("Library Name: " + library.getName());
            System.out.println("Latitude: " + library.getLat());
            System.out.println("Longitude: " + library.getLng());
            System.out.println("Address: " + library.getAddress());
            System.out.println("Category: " + library.getCategory());
            System.out.println("------------------------------");
        }
    }

    @Test
    public void testSearchLib() {
        // DAO 인스턴스를 가져옴
        LibraryDAO dao = LibraryDAO.getInstance();
        
        // 테스트할 검색어
        String searchKeyword = "광진구";
        
        // searchLib 메서드 호출
        ArrayList<LibraryDTO> resultList = dao.searchLib(searchKeyword);
        
        // 결과가 null이 아닌지 확인
        assertNotNull(resultList, "The result list should not be null");
        
        // 결과 리스트가 비어있지 않은지 확인
        assertTrue(resultList.size() > 0, "The result list should contain at least one item");

        // 결과 출력
        System.out.println("Search results for keyword: " + searchKeyword);
        for (LibraryDTO library : resultList) {
            System.out.println("Library Name: " + library.getName());
            System.out.println("Address: " + library.getAddress());
            System.out.println("Category: " + library.getCategory());
            System.out.println("------------------------------");
            
            // 검색어가 주소에 포함되어 있는지 확인
            assertTrue(library.getAddress().contains(searchKeyword), 
                       "The address should contain the search keyword");
        }
    }
}
