package service;

import domain.Book;
import domain.Loan;
import domain.Member;

import java.util.List;

public interface MemberService {

    Member login(String email, String password);

    void logout(Integer memberId);

    void addMember(Member member);

    void updateMember(Integer id, Member member);

    void deleteMember(Integer id);

    Member findMemberById(Integer id);

    List<Member> findAllMembers();

    List<Member> searchMembersByName(String name);

    List<Book> viewAvailableBooks(Integer memberId);

    Loan borrowBook(Integer memberId, List<Book> books);

    void returnBook(Integer memberId, Integer loanId);

    List<Loan> viewOwnLoans(Integer memberId);

    void updateOwnProfile(Integer memberId, Member updatedInfo);

}
