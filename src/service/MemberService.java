package service;

import domain.Member;

import java.util.List;

public interface MemberService {

    void addMember(Member member);

    void updateMember(Integer id, Member member);

    void deleteMember(Integer id);

    Member findMemberById(Integer id);

    Member findMemberByEmail(String email);

    List<Member> findAllMembers();

    List<Member> searchMembersByName(String name);

}
