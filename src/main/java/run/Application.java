package run;

import service.BoardService;

import java.util.Scanner;

public class Application {

    private static final BoardService bs = new BoardService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("======= 게시판 =======");
            System.out.println("1. 조회");
            System.out.println("2. 게시글 작성");
            System.out.println("3. 게시글 삭제");
            System.out.println("4. 게시글 수정");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴를 선택해 주세요: ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    System.out.println("1. 전체");
                    System.out.println("2. 카테고리");
                    System.out.println("3. 내용");
                    System.out.print("조회할 분류를 선택해 주세요: ");
                    int input1 = sc.nextInt();
                    switch (input1){
                        case 1:
                            // 서비스
                            break;
                        case 2:
                            //서비스
                            break;
                        case 3:
                            bs.searchContent();
                            break;
                        case 9:
                            System.out.println("뒤로가기");
                            return;
                        default:
                            System.out.println("번호를 제대로 다시 입력해 주세요");
                    }
                    break;
//                case 2:
//                    bs.selectMember(chooseBoardNo());
//                    break;
//                case 3:
//                    bs.registMember(signUp());
//                    break;
//                case 4:
//                    bs.deleteMember(chooseMemberNo());
//                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 제대로 다시 입력해 주세요");
            }
        }
    }

    private static int chooseBoardNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("회원번호를 입력하세요: ");
        return sc.nextInt();
    }
}
