package service;

import domain.*;

import java.time.Instant;

import static service.Data.*;

//这个类来实现员工列表的增删查
public class NameListService{
    private Employee[] employees;

    /*
    * 根据提供的Data的EMPLOYEES重构Employee对象，并加到对象列表中去
    * */
    public NameListService(){
        employees = new Employee[EMPLOYEES.length];
        for(int i = 0;i<employees.length;i++){
            //获取Employee的类型，是普通的Employee,还是Programmer,Designer或者Architect,然后重构对应的对象
            int type = Integer.parseInt(EMPLOYEES[i][0]);
            //获取对应的id,name,age,salary
            int id = Integer.parseInt(EMPLOYEES[i][1]);
            String name = EMPLOYEES[i][2];
            int age = Integer.parseInt(EMPLOYEES[i][3]);
            double salary = Double.parseDouble(EMPLOYEES[i][4]);
            Equipment equipment;
            double bonus;
            int stock;
            //根据获取到的type构造对象的对象并加到list里面
            switch (type){
                //如果type是10(Data里面定义了10就是EMPLOYEE)，就是Employee,直接根据上面得到的构造Employee即可
                case EMPLOYEE:
                    employees[i] = new Employee(id, name, age, salary);
                    break;
                //如果type是11，说明是Programmer, 还需要Equipment,因为后面的Designer和Architect都有Equipment这个成员变量，所以我们在之前就定义好
                case PROGRAMMER:
                    equipment = createEquipment(i);
                    employees[i] = new Programmer(id, name, age, salary,equipment);
                    break;
                case DESIGNER:
                    bonus = Double.parseDouble(EMPLOYEES[i][5]);
                    equipment = createEquipment(i);
                    employees[i] = new Designer(id, name, age, salary, equipment, bonus);
                    break;
                case ARCHITECT:
                    bonus = Double.parseDouble(EMPLOYEES[i][5]);
                    stock = Integer.parseInt(EMPLOYEES[i][6]);
                    equipment = createEquipment(i);
                    employees[i] = new Architect(id, name, age, salary, equipment, bonus, stock);
                    break;
            }

        }
    }
    /*
     * 根据提供的Data的EQUIPMENTS重构Equipment对象，并用来用于每一个Employee的重构
     * */

    /**
     *
     * @param i:员工列表的索引
     * @return 员工对应的器材
     */
    private Equipment createEquipment(int i) {
        int type = Integer.parseInt(EQUIPMENTS[i][0]);//类型
        String model = EQUIPMENTS[i][1];
        String name = EQUIPMENTS[i][2];

        switch (type){
            case PC:
                return  new PC(model, name);
            case NOTEBOOK:
                return new Notebook(model, Double.parseDouble(name));
            case PRINTER:
                return new Printer(model, name);
        }
        return null;//如果为空的话返回null
    }

    /**
     * 获取所有员工组成的数组
     * @return 所有员工的信息
     */
    public Employee[] getEmployees(){
        return employees;
    }

    /**
     * 根据用户输入的员工id，获取员工信息
     * @param id: 要查找的员工的id
     * @return 查找的id 为id的员工的信息
     * @throws TeamException 如果没有该员工，提示用户
     */
    //
    public Employee getEmployee(int id) throws TeamException{
        for(int i = 0;i<employees.length;i++){
            if(employees[i].getId()==id){
                return employees[i];
            }
        }
        throw new TeamException("找不到指定的员工");
    }

}
