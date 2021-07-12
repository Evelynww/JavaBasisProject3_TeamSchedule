package view;

import domain.Employee;
import domain.Programmer;
import service.NameListService;
import service.TeamException;
import service.TeamService;

public class TeamView {
    private NameListService nameListService;
    private TeamService teamService;

    public TeamView(){
        nameListService = new NameListService();
        teamService = new TeamService();
    }

    /**
     * 主界面显示及控制操作
     */
    public void enterMainMenu(){
        boolean loopFlag = true;
        char key ;
        do{
            System.out.println("-------------------------------------开发团队调度软件--------------------------------------");
            listAllEmployees();
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.print("1-团队列表  2-添加团队成员  3-删除团队成员 4-退出   请选择(1-4)：");
            key = TSUtility.readMenuSelection();
            switch (key){
                case '1':
                    getTeam();
                    break;
                case '2':
                    addMember();
                    break;
                case '3':
                    deleteMember();
                    break;
                case '4':
                    System.out.print("确定要退出吗('Y')or('N'):");
                    char loopSelection = TSUtility.readConfirmSelection();
                    if(loopSelection=='Y')
                        loopFlag = false;
                    break;
            }
        }while (loopFlag);

    }

    /**
     * 以表格形式列出公司所有成员
     */
    public void listAllEmployees(){
        System.out.println("-------------------------------------员工列表--------------------------------------");

        System.out.println();

        Employee[] employees = nameListService.getEmployees();
        if(employees.length==0){
            System.out.println("没有成员记录");
        }else{
            System.out.println("ID\t姓名\t年龄\t工资\t职位\t状态\t奖金\t股票\t领用设备");
        }
        for(Employee e:employees){
            System.out.println(" "+e);
        }
        System.out.println("---------------------------------------------------------------------------------");
    }

    /**
     * 显示团队成员列表
     */

    public void getTeam(){
        System.out
                .println("\n--------------------团队成员列表---------------------\n");
        Programmer[] team = teamService.getTeam();
        if (team.length == 0) {
            System.out.println("开发团队目前没有成员！");
        } else {
            System.out.println("TID/ID\t姓名\t年龄\t工资\t职位\t奖金\t股票");
        }

        for (Programmer p : team) {
            System.out.println(" " + p.getDetailsForTeam());
        }
        System.out
                .println("-----------------------------------------------------");
    }

    /**
     * 实现添加成员操作
     */
    public void addMember(){
        System.out.println("---------------------添加成员---------------------");
        System.out.print("请输入要添加的员工ID：");
        int id = TSUtility.readInt();

        try {
            Employee e = nameListService.getEmployee(id);
            teamService.addMember(e);
            System.out.println("添加成功");
        } catch (TeamException e) {
            System.out.println("添加失败，原因：" + e.getMessage());
        }
        // 按回车键继续...
        TSUtility.readReturn();
    }

    /**
     * 实现删除成员操作
     */
    public void deleteMember(){
        System.out.println("---------------------删除成员---------------------");
        System.out.print("请输入要删除员工的TID：");
        int id = TSUtility.readInt();
        System.out.print("确认是否删除(Y/N)：");
        char yn = TSUtility.readConfirmSelection();
        if (yn == 'N')
            return;
        try {
            teamService.removeMember(id);
            System.out.println("删除成功");
        } catch (TeamException e) {
            System.out.println("删除失败，原因:"+e.getMessage());
        }
        TSUtility.readReturn();
    }

    public static void main(String[] args) {
        TeamView teamView = new TeamView();
        teamView.enterMainMenu();
    }
}