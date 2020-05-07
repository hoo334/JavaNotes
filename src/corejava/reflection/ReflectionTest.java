package corejava.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

public class ReflectionTest {
    public static void main(String[] args){
        //read class name from command line args or user input
        String name;
        if(args.length>0)name = args[0];
        else{
            Scanner in = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date)");
            name = in.next();
        }

        try{
            //print class name and superclass name(if!=object)
            Class cl = Class.forName(name);
            Class supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if(modifiers.length()>0)System.out.print(modifiers+" ");

            System.out.print("class"+name);
            if(supercl != null && supercl != Object.class)System.out.print("extends "+supercl.getName());
            System.out.print("\n{\n");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printFields(cl);
            System.out.println("}");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        for(Field f:fields){
            Class type = f.getType();
            String name = f.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(f.getModifiers());
            if(modifiers.length()>0)System.out.print(modifiers+" ");
            System.out.print(type.getName()+" "+name+'(');
        }

    }

    private static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for(Method m:methods){
            Class retType = m.getReturnType();
            String name = m.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(m.getModifiers());
            if(modifiers.length()>0)System.out.print(modifiers+" ");
            System.out.print(retType.getName()+" "+name+'(');

            //print parameter types
            Class[] parameters = m.getParameterTypes();
            for(int j=0;j<parameters.length;++j)
            {
                if(j>0)System.out.print(", ");
                System.out.print(parameters[j].getName());
            }
            System.out.println(");");
        }
    }

    private static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for(Constructor c:constructors){
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if(modifiers.length()>0)System.out.print(modifiers+" ");
            System.out.print(name+'(');

            //print parameter types
            Class[] parameters = c.getParameterTypes();
            for(int j=0;j<parameters.length;++j)
            {
                if(j>0)System.out.print(", ");
                System.out.print(parameters[j].getName());
            }
            System.out.println(");");
        }
    }
}
