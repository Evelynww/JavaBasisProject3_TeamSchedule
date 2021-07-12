package service;


import domain.Architect;
import domain.Designer;
import domain.Employee;
import domain.Programmer;

public class TeamService {
    private static int memberId = 0; //用来为开发团队新增成员自动生成团队中的唯一ID
    private final int MAX_MEMBER = 5; //开发团队中最大成员数
    private Programmer[] team = new Programmer[MAX_MEMBER];//保存当前团队中的各成员对象
    private int total = 0;//团队实际人数
    public TeamService(){}
    /**
     * 返回当前团队的所有对象
     * @return 当前团队所有成员对象的数组
     */
    public Programmer[] getTeam(){
        Programmer[] team = new Programmer[total];
        for(int i = 0;i<total;i++){
            team[i] = this.team[i];
        }
        return team;
    }

    /**
     * 想团队中添加成员
     * @param e 待添加成员的对象
     * @throws TeamException 添加失败。
     */
    public void addMember(Employee e) throws TeamException{
        if(total>=MAX_MEMBER){
            throw new TeamException("成员已满，无法添加");
        }
        if(!(e instanceof Programmer)){
            throw new TeamException("该成员不是开发人员，无法添加");
        }
        Programmer p = (Programmer)e;
        if(p.getStatus()==Status.BUSY){
            throw new TeamException("该员工已是某团队成员");
        }
        if(p.getStatus()==Status.VOCATION){
            throw new TeamException("该员正在休假，无法添加");
        }
        if(isExists(p)){
            throw new TeamException("该员工已在本团队中");
        }
        // 团队中至多只能有一名架构师
        // 团队中至多只能有两名设计师
        // 团队中至多只能有三名程序员
        int numOfArc = 0,numOfDes=0,numOfPro=0;
        for(int i = 0;i<total;i++){
            if(team[i] instanceof Architect){
                numOfArc++;
            }else if(team[i] instanceof Designer){
                numOfDes++;
            }else{
                numOfPro++;
            }
        }
        if(p instanceof Architect && numOfArc>=1){
            throw new TeamException("团队中至多只能有一名架构师");
        }else if(p instanceof Designer&&numOfDes>=2){
            throw new TeamException("团队中至多只能有两名设计师");
        }else if(p instanceof Programmer && numOfPro>=3){
            throw new TeamException("团队中至多只能有三名程序员");
        }

        //以上就是所有添加不进去的情况
        //如果上面的哪些都通过了就可以顺利添加了
        p.setStatus(Status.BUSY);
        p.setMemberId(++memberId);
        team[total++] = p;
    }

    /*

     */

    /**
     * 判断员工e在不在团队里面
     * @param e 员工
     * @return 如果员工在团队中返回true,否则返回false
     */
    private boolean isExists(Employee e) {
        for(int i = 0;i<total;i++){
            if(team[i].getId()==e.getId()){
                return true;
            }
        }
        return false;
    }

    /**
     * 从团队中删除成员
     * @param memberId  待删除成员的memberID
     * @throws TeamException 找不到指定memberId的成员，删除失败
     */
    public void removeMember(int memberId) throws TeamException{
        int i;
        for(i = 0;i<total;i++){
            if(team[i].getMemberId()==memberId){
                team[i].setStatus(Status.FREE);
                break;
            }
        }
        //找到最后了还没找到
        if(i==total){
            throw new TeamException("找不到指定memberId的员工，删除失败");
        }
        //把删除的员工后面的往前移，并将total-1,最后一位设为null
        for(int j = i;j<total-1;j++){
            team[j] = team[j+1];
        }
        team[--total] = null;
    }
}
