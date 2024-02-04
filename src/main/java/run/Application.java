package run;

import aggregate.Board;
import aggregate.CategoryType;
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
                    System.out.println("9. 뒤로가기");
                    System.out.print("조회할 분류를 선택해 주세요: ");
                    int input1 = sc.nextInt();
                    switch (input1) {
                        case 1:
                            bs.selectAllBoards();
                            break;
                        case 2:
                            displayCategoryOptions();
                            System.out.print("카테고리를 선택해 주세요: ");
                            int categoryInput = sc.nextInt();
                            CategoryType selectedCategory = getCategoryType(categoryInput);
                            bs.searchByCategory(selectedCategory);
                            break;
                        case 3:
                            System.out.print("검색할 내용을 입력해주세요: ");
                            sc.nextLine();
                            String searchInput = sc.nextLine();
                            while (true) {
                                if (searchInput != null) {
                                    bs.searchContent(searchInput);
                                    break;
                                } else break;
                            }

                            break;
                        case 9:
                            break;
                        default:
                            System.out.println("번호를 제대로 다시 입력해 주세요");
                    }
                    break;
                case 2:
                    bs.registBoard(writeContent());
                    break;
                case 3:
                    bs.deleteBoard(chooseBoardNo(), chooseMember(), deleteYN());
                    break;
                case 4:
                    System.out.print("수정할 게시글의 번호입력 : ");
                    int boardNo = sc.nextInt();
                    sc.nextLine();
                    bs.updateBoard(boardNo);
                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default:
                    System.out.println("번호를 제대로 다시 입력해 주세요");
            }
        }
    }

    public static Board writeContent() {

        Board newInfo = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("ID를 입력하세요: ");
        String id = sc.nextLine();

        System.out.print("게시물 제목을 입력하세요: ");
        String title = sc.nextLine();

        System.out.print("게시물 내용을 입력하세요: ");
        String content = sc.nextLine();

        newInfo = new Board(id, title, content);

        System.out.print("게시판 종류를 입력하세요: ");
        String categoryType = sc.next().toUpperCase();
        CategoryType ct = null;
        switch (categoryType) {
            case "Free":
                ct = CategoryType.FREE;
                break;
            case "Share":
                ct = CategoryType.SHARE;
                break;
            case "Into":
                ct = CategoryType.INTRO;
                break;
            case "Review":
                ct = CategoryType.REVIEW;
                break;
            case "QNA":
                ct = CategoryType.QNA;
                break;
        }

        newInfo.setCategoryType(ct);

        return newInfo;
    }

    private static int chooseBoardNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("게시글 번호를 입력하세요: ");
        return sc.nextInt();
    }

    private static void displayCategoryOptions() {
        System.out.println("1. FREE");
        System.out.println("2. SHARE");
        System.out.println("3. INTRO");
        System.out.println("4. REVIEW");
        System.out.println("5. QNA");
    }

    private static CategoryType getCategoryType(int input) {
        switch (input) {
            case 1:
                return CategoryType.FREE;
            case 2:
                return CategoryType.SHARE;
            case 3:
                return CategoryType.INTRO;
            case 4:
                return CategoryType.REVIEW;
            case 5:
                return CategoryType.QNA;
            default:
                throw new IllegalArgumentException("유효하지 않은 카테고리 입력");
        }
    }


    private static String deleteYN() {
        Scanner sc = new Scanner(System.in);
        System.out.print("삭제하려면 'Y'를 입력하세요: ");

        return sc.nextLine().toUpperCase();
    }

    private static String chooseMember() {
        Scanner sc = new Scanner(System.in);
        System.out.print("작성자 아이디를 입력하세요:");
        return sc.nextLine();
    }
}
