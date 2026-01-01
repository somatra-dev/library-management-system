package service.impl;

import domain.Book;
import domain.Loan;
import domain.Member;
import service.BookService;
import service.LoanService;
import service.MemberService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private final List<Member> members = new ArrayList<>();
    private Integer nextId = 1;

    private final BookService bookService;
    private final LoanService loanService;

    public MemberServiceImpl(BookService bookService, LoanService loanService) {
        this.bookService = bookService;
        this.loanService = loanService;
    }

    // ===================== Authentication =====================
    @Override
    public Member login(String email, String password) {
        return members.stream()
                .filter(m -> m.getEmail().equals(email) && m.getPassword().equals(password))
                .filter(Member::getIsActive)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void logout(Integer memberId) {
        System.out.println("Member with ID " + memberId + " has logged out.");
    }

    // ===================== Member CRUD =====================
    @Override
    public void addMember(Member member) {
        member.setId(nextId++);
        member.setIsMember(true);
        member.setIsActive(true);
        members.add(member);
    }

    @Override
    public void updateMember(Integer id, Member member) {
        Member existing = findMemberById(id);
        if (existing != null) {
            existing.setName(member.getName());
            existing.setEmail(member.getEmail());
            existing.setPassword(member.getPassword());
            existing.setPhoneNumber(member.getPhoneNumber());
            existing.setAddress(member.getAddress());
            existing.setIsActive(member.getIsActive());
        }
    }

    @Override
    public void deleteMember(Integer id) {
        members.removeIf(m -> m.getId().equals(id));
    }

    @Override
    public Member findMemberById(Integer id) {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Member> findAllMembers() {
        return new ArrayList<>(members);
    }

    @Override
    public List<Member> searchMembersByName(String name) {
        return members.stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Member Actions
    @Override
    public List<Book> viewAvailableBooks(Integer memberId) {
        Member member = findMemberById(memberId);
        if (member != null && member.getIsMember()) {
            return bookService.findAvailableBooks();
        }
        System.out.println("Access denied: User is not a member.");
        return Collections.emptyList();
    }

    @Override
    public Loan borrowBook(Integer memberId, List<Book> books) {
        Member member = findMemberById(memberId);
        if (member != null && member.getIsMember() && member.getIsActive()) {
            return loanService.borrowBook(member, books);
        }
        System.out.println("Access denied: User is not a member or inactive.");
        return null;
    }

    @Override
    public void returnBook(Integer memberId, Integer loanId) {
        Member member = findMemberById(memberId);
        if (member != null && member.getIsMember()) {
            loanService.returnBook(loanId);
        } else {
            System.out.println("Access denied: User is not a member.");
        }
    }

    @Override
    public List<Loan> viewOwnLoans(Integer memberId) {
        Member member = findMemberById(memberId);
        if (member != null && member.getIsMember()) {
            return loanService.findLoansByMember(memberId);
        }
        System.out.println("Access denied: User is not a member.");
        return Collections.emptyList();
    }

    @Override
    public void updateOwnProfile(Integer memberId, Member updatedInfo) {
        Member member = findMemberById(memberId);
        if (member != null && member.getIsMember()) {
            member.setName(updatedInfo.getName());
            member.setEmail(updatedInfo.getEmail());
            member.setPassword(updatedInfo.getPassword());
            member.setPhoneNumber(updatedInfo.getPhoneNumber());
            member.setAddress(updatedInfo.getAddress());
        } else {
            System.out.println("Access denied: User is not a member.");
        }
    }

}
