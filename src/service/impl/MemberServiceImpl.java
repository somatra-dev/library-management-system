package service.impl;

import domain.Member;
import service.MemberService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MemberServiceImpl implements MemberService {

    private final List<Member> memberList = new ArrayList<>();
    private Integer autoId = 1000;

    @Override
    public void addMember(Member member) {
        //Auto ID logic
        if (member.getId() == null) {
            member.setId(autoId++);
        }
        //Member data if missing
        if (member.getMembershipDate() == null) {
            member.setMembershipDate(LocalDate.now());
        }
        if (member.getIsActive() == null) {
            member.setIsActive(true);
        }

        memberList.add(member);
        System.out.println("Member added: " + member.getName());
    }

    @Override
    public void updateMember(Integer id, Member updatedMember) {
        Member existMember = findMemberById(id);
        // update logic
        if (existMember != null) {
            existMember.setName(updatedMember.getName());
            existMember.setEmail(updatedMember.getEmail());
            existMember.setPhoneNumber(updatedMember.getPhoneNumber());
            existMember.setAddress(updatedMember.getAddress());
            existMember.setMembershipDate(updatedMember.getMembershipDate());
            existMember.setIsActive(updatedMember.getIsActive());

            System.out.println("Member with id : " + id +"has been updated.");
        } else {
            System.out.println("Cannot update: Member with ID " + id + " not found.");
        }
    }

    @Override
    public void deleteMember(Integer id) {
        Member member = findMemberById(id);
        if (member != null) {
            memberList.remove(member);
            System.out.println("Member with id: " + id+" has been deleted.");
        } else {
            System.out.println("Cannot delete: Member with ID " + id + " not found.");
        }
    }

    @Override
    public Member findMemberById(Integer id) {
        return memberList.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Member findMemberByEmail(String email) {
        return memberList.stream()
                .filter(m -> m.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Member> findAllMembers() {
        return new ArrayList<>(memberList);
    }

    @Override
    public List<Member> searchMembersByName(String name) {
        return memberList.stream()
                .filter(m -> m.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }
}