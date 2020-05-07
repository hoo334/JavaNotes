package corejava.randomAccess;

import corejava.inheritance.Employee;

import java.io.*;
import java.time.LocalDate;

public class RandonAccessTest {
    private static final int RECORD_SIZE = 100;
    private static final int NAME_SIZE =40;//unicode编码一个字符占2个Byte

    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Carker",75000,1987,12,15);
        staff[1] = new Employee("Harry Hacker",50000,1989,10,1);
        staff[2] = new Employee("Tony Tester",40000,1990,3,15);
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.dat"))){
            //save all employee records to the file employee.dat
            for (Employee e : staff) {
                writeData(out,e);
            }
        }

        try(RandomAccessFile in = new RandomAccessFile("employee.dat","r")){
            //retrieve all records into a new array
            //compute the array size

            int n = (int)(in.length()/RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            //read employees in reverse order
            for (int i = n -1; i >= 0 ; i--) {
                newStaff[i] = new Employee();
                in.seek(i*RECORD_SIZE);
                newStaff[i] = readData(in);
            }

            //print the newly read employee records
            for (Employee e : newStaff) {
                System.out.println(e);
            }
        }
    }

    public static void writeData(DataOutput out,Employee e)throws IOException{
        writeFixedString(e.getName(),NAME_SIZE,out);
        out.writeDouble(e.getSalary());
        LocalDate hireDay = e.getHireDay();
        out.writeInt(hireDay.getYear());
        out.writeInt(hireDay.getMonthValue());
        out.writeInt(hireDay.getDayOfMonth());
    }

    public static Employee readData(DataInput in) throws IOException{
        String name = readFixedString(NAME_SIZE,in);
        double salary = in.readDouble();
        return new Employee(name,salary,in.readInt(),in.readInt(),in.readInt());
    }

    public static void writeFixedString (String s,int size,DataOutput out) throws IOException{
        for (int i = 0; i < size; i++) {
            char ch = 0;
            if(i<s.length())ch = s.charAt(i);
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size,DataInput in) throws IOException{
        StringBuilder res = new StringBuilder(size);
        int i = 0;
        boolean more = true;
        while(more && i<size){
            char ch = in.readChar();
            i++;
            if(ch == 0)more = false;
            else res.append(ch);
        }
        in.skipBytes(2*(size-i));//跳过剩下的空字符
        return res.toString();
    }
}
