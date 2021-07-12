package domain;

import service.Status;

public class Programmer extends Employee{
    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    private int memberId;
    private Status status = Status.FREE;
    private Equipment equipment;

    public String getMemberDetails(){
        return getMemberId()+"/"+getDetails();
    }
    public String getDetailsForTeam(){
        return getMemberDetails()+"\t程序员";
    }
    public Programmer(){}

    public Programmer(int id,String name, int age,double salary,Equipment equipment) {
        super(id,name,age,salary);
        this.equipment = equipment;
    }
    @Override
    public String toString() {
        return getDetails() + "\t程序员\t" + status + "\t\t\t" + equipment.getDescription() ;
    }
}
