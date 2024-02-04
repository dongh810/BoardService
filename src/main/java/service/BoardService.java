package service;

import aggregate.Board;
import aggregate.CategoryType;
import repository.BoardRepository;

import java.util.List;
import java.util.ArrayList;

public class BoardService {
    private List<Board> boards;

    private final BoardRepository br = new BoardRepository();

    public BoardService() {
        this.boards = br.selectAllBoard();
    }

    public void selectAllBoards() {
        ArrayList<Board> selectedBoards = br.selectAllBoard();

        /* 설명. 회원이 한명도 없어서 조회 결과가 없더라도 ArrayList객체는 넘어온다.(Empty상태로) */
        if (!selectedBoards.isEmpty()) {        // 회원이 한명이라도 조회 된다면
            System.out.println("==== service까지 잘 반환되어 오나 확인 ====");
            for (Board b : selectedBoards) {
                System.out.println(b);
            }

            return;                             // 이후 코드와 상관 없이 메소드 종료
        }

        /* 설명. 조건이 맞지 않아(회원이 조회되지 않아) 출력을 하는 구문(위의 조건이 맞으면 실행되지 않음)(feat.else 안 쓰기) */
        System.out.println("슬프게도 우리 게시판은 아직 글이 없습니다.");
    }

    public void searchContent(String input) {
        ArrayList<Board> searchingContent = br.searchContent(input);

        /* 설명. 회원이 한명도 없어서 조회 결과가 없더라도 ArrayList객체는 넘어온다.(Empty상태로) */
        if (!searchingContent.isEmpty()) {        // 회원이 한명이라도 조회 된다면
            for (Board b : searchingContent) {
                System.out.println(b);
            }
        } else System.out.println("검색된 데이터가 없습니다.");
    }

    public void deleteBoard(int boardNo, String Member, String YN) {
        int result = br.deleteBoard(boardNo, Member, YN);
        if (result == 1) {
            System.out.println(boardNo + "번 게시물 삭제를 성공하였습니다.");
            return;
        } else if (result == 2) {
            System.out.println("회원 아이디가 일치하지 않습니다.");
        } else if (result == 3) {
            System.out.println("입력이 잘못되었습니다.");
        }
        System.out.println("게시물 삭제에 실패하였습니다.");
    }

    public void registBoard(Board board) {
        int lastBoardNo = br.selectLastBoardNo();
        board.setBoardNo(lastBoardNo + 1);

        int result = br.registBoard(board);
        if (result == 1) {
            System.out.println(board.getBoardNo() + "번째 게시물이 작성되었습니다.");
        }
    }

    public void searchByCategory(CategoryType categoryType) {
        List<Board> result = new ArrayList<>();

        System.out.println("카테고리 검색 시작. 선택한 카테고리: " + categoryType);

        for (Board board : boards) {
            if (board.getCategoryType() == categoryType) {
                result.add(board);
            }
        }

        if (result.isEmpty()) {
            System.out.println("선택한 카테고리에 해당하는 게시글이 없습니다.");
        } else {
            for (Board board : result) {
                displayBoardInfo(board);
            }
        }
    }

    private void displayBoardInfo(Board board) {
        System.out.println("게시글 번호: " + board.getBoardNo());
        System.out.println("작성자: " + board.getId());
        System.out.println("제목: " + board.getTitle());
        System.out.println("내용: " + board.getContent());
        System.out.println("작성날짜: " + board.getDate());
        System.out.println("카테고리: " + board.getCategoryType());
        System.out.println("-----------------------------");
    }
    public void updateBoard(int boardNo){
        br.updateBoard(boardNo);
    }
}
