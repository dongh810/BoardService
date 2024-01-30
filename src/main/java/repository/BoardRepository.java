package repository;

import aggregate.Board;
import aggregate.CategoryType;
import stream.MyObjectOutput;

import java.io.*;

import java.util.ArrayList;

public class BoardRepository {

    private ArrayList<Board> boardList = new ArrayList<>();

    /* 설명. 프로그램이 켜지자 마자(MemberRepository()가 실행되자마자) 파일에 dummy 데이터 추가 및 입력받기 */
    public BoardRepository() {

        /* 설명. 회원가입 기능 추가 후 이제는 파일이 기존에 존재하면(처음이 아니므로) 회원 3명으로 초기화 하기를 하지 않는다. */
        File file = new File("src/main/java/db/boardDB.dat");
        if(!file.exists()) {
            ArrayList<Board> boards = new ArrayList<>();
            boards.add(new Board(1,"dongh","아아아아아아앙ㅇㅇㅇ!!!!!","2024-01-30","이잉!", CategoryType.FREE));
            boards.add(new Board(2,"dongh","asdf","2024-01-30","dfsf",CategoryType.QNA));

            saveBoards(boards);
        }

        loadBoards();

//        System.out.println("==== repository에서 회원정보 다 읽어왔는지 확인 ====");
//        for(Member m: memberList) {
//            System.out.println(m);
//        }
    }

    /* 설명. 회원이 담긴 ArrayList를 던지면 파일에 출력하는 기능 */
    private void saveBoards(ArrayList<Board> boards) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream("src/main/java/db/boardDB.dat")));

            /* 설명. 넘어온 회원 수만큼 객체 출력하기 */
            for(Board b: boards) {
                oos.writeObject(b);
            }

            oos.flush();                // 출력 시에는 flush 해 줄것
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* 설명. 파일로부터 회원 객체들을 입력받아 memberList에 쌓기 */
    private void loadBoards() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("src/main/java/db/boardDB.dat")));

            /* 설명. 파일로부터 모든 회원 정보를 읽어 memberList에 추가(add) */
            while(true) {
                boardList.add((Board)(ois.readObject()));
            }

        } catch (EOFException e) {
            System.out.println("회원 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Board> selectAllBoard() {
        return boardList;
    }

    public Board selectMember(int memNo) {
        for(Board b: boardList) {
            if(b.getBoardNo() == memNo) return b;
        }
        return null;
    }

    public int selectLastBoardNo() {
//        Member latestMember = memberList.get(memberList.size() - 1);        // 가장 최근에 가입한 회원
//        return latestMember.getMemNo();                                     // 그 회원의 번호
        return boardList.get(boardList.size() - 1)
                .getBoardNo();
    }

    /* 설명. 기존 회원(객체)에 이어서 파일 출력을 하고 추가 한 객체의 수를 반환(feat.DML 작업의 결과는 int) */
    /* 설명. 객체 출력을 할 예정인데 기존 ObjectOutputStream 대신 새로 정의한 스트림으로 회원 한명 파일 출력해서 int 반환하기(feat.이어쓰기) */
    public int registBoard(Board board) {
        MyObjectOutput moo = null;
        try {
            moo = new MyObjectOutput(
                    new BufferedOutputStream(
                            new FileOutputStream("src/main/java/db/boardDB.dat", true)));

            /* 설명. 파일로 객체 하나 출력하기 */
            moo.writeObject(board);

            /* 설명. repository의 memberList에도 추가 */
            boardList.add(board);

            moo.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return 1;
    }

    public int deleteBoard(int boardNo) {
        for (int i = 0; i < boardList.size(); i++) {
            if(boardList.get(i).getBoardNo() == boardNo) {     // 지울려는 회원과 같은 회원 번호인 index 찾기

                /* 설명. 프로그램 상에서 회원을 관리하는 memberList 뿐 아니라 DB(회원 파일)도 같이 적용되게 함 */
                boardList.remove(i);
                saveBoards(boardList);
                return 1;
            }
        }
        return 0;
    }

    public void searchContent() {
        System.out.println("리포를 통해 디비에서 내용검색해서 꺼내오기");
    }
}
