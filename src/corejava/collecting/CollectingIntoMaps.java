package corejava.collecting;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectingIntoMaps {
    public static class Person{
        private int id;
        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public static Stream<Person> people(){
        return Stream.of(new Person(1001,"Peter"),new Person(1002,"Paul"),new Person(1003,"Mary"));
    }

    public static void main(String[] args) {
        //将Stream<Person>转换为Map
        Map<Integer,String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName"+idToName);

        //Function.identity()代表stream中的一个对象本身。
        Map<Integer,Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson"+idToPerson.getClass().getName()+idToPerson);

        idToPerson = people().collect(Collectors.toMap(Person::getId,Function.identity(),(existingValue, newValue) ->{
            //一个编号对应多个人的话 就会抛出异常，因为一个键对应多个元素
            throw new IllegalStateException();
        }, TreeMap::new));
        System.out.println("idToPerson:"+idToPerson.getClass().getName()+idToPerson);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        //正则用来提供一个mergeFunction 来合并具有相同键的值
        Map<String,String> languageNames = locales.collect(
          Collectors.toMap(Locale::getDisplayLanguage, l ->l.getDisplayLanguage(l),
                  (existingValue,newValue) -> existingValue));//我们不关心同一种语言出现两次，只记录第一项
        System.out.println("languageNames: "+languageNames);

        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryLanguageSets = locales.collect(
                Collectors.toMap(Locale::getDisplayCountry, l -> Collections.singleton(l.getDisplayLanguage()),
                        (a,b) ->{//只要找到给定国家的新语言都要将已有集和新集做并操作。
                    Set<String> union = new HashSet<>(a);
                    union.addAll(b);
                    return union;
                        }));

        System.out.println("countryLanguageSets: "+countryLanguageSets);
    }
}
