package com.example.myjava8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class MainActivity extends AppCompatActivity {

    //注意:
    //Android Studio不支持java8，jack虽然支持但是jack与retrolambda冲突
    // （studio不添加retrolambda不支持lambda表达式，不添加jack不支持java8，
    // 都添加会冲突报Error:Cannot get property 'destinationDir' on null object）。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Person> list = new ArrayList<>();

        list.add(new Person("王一", "男", 20));
        list.add(new Person("王二", "女", 22));
        list.add(new Person("王三", "男", 25));
        list.add(new Person("李一", "男", 45));
        list.add(new Person("李二", "男", 33));
        list.add(new Person("李三", "女", 34));
        list.add(new Person("李四", "女", 35));

        //要求Android SDK min 24;JDK 1.8
        //计数
        long count = list.stream().collect(Collectors.counting());
        Log.d("Test", "count=" + count);

        long count1 = list.stream().count();
        Log.d("Test", "count1=" + count1);

        //找出大于30岁的人
//        List<Person> result = filter(list, person -> {
//            if (person.getAge() >= 30)
//                return true;
//            return false;
//        });

        //最值
        //找出所有人中年龄最大的人
        Optional<Person> oldPerson = list.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Person::getAge)));
        Log.d("Test", "oldPerson=" + oldPerson.toString());

        //求和
        //计算所有人的年龄总和
        int summing = list.stream()
                .collect(Collectors.summingInt(Person::getAge));
        Log.d("Test", "summing=" + summing);

        //采用单参数的reducing计算所有人的年龄总和
//        Optional<Integer> sumAge = list.stream()
//                .filter(Person::getAge)
//                .collect(Collectors.reducing((i, j) -> i + j));
//        Log.d("Test", "sumAge=" + sumAge);
//
//        Optional<Integer> sumAge1 = list.stream()
//                .collect(Collectors.reducing(0, Person::getAge, (i, j) -> i + j));
//        Log.d("Test", "sumAge1=" + sumAge1);

        //找出年龄最大的
        OptionalInt maxAge = list.stream()
                .mapToInt(Person::getAge)
                .max();
        Log.d("Test", "maxAge=" + maxAge);

        //去掉重复的
        List<Person> result = list.stream()
                .distinct()
                .collect(toList());

        //截取流的前N个元素：
        List<Person> result1 = list.stream()
                .limit(3)
                .collect(toList());

        //跳过流的前n个元素：
        List<Person> result2 = list.stream()
                .skip(3)
                .collect(toList());

        //平均值
        //计算所有人的年龄平均值
        double avg = list.stream()
                .collect(Collectors.averagingInt(Person::getAge));

        List<String> list1 = new ArrayList<>();
        list1.add("小王");
        list1.add("小李");
        list1.add("小张");

        //连接字符串
        //将所有人的名字连接成一个字符串
        String names = list1.stream()
                .collect(Collectors.joining());
        Log.d("Test", "names=" + names);

        //指定分隔符
        String names1 = list1.stream()
                .collect(Collectors.joining(", "));
        Log.d("Test", "names1=" + names1);

        //分组
        //一级分组

        //将所有人分为老年人、中年人、青年人
//        Map<String, List<Person>> result = list.stream()
//                .collect(Collectors.groupingby((person) -> {
//                    if (person.getAge() > 60)
//                        return "老年人";
//                    else if (person.getAge() > 40)
//                        return "中年人";
//                    else
//                        return "青年人";
//                }));

        //多级分组
        //将所有人分为老年人、中年人、青年人，并且将每个小组再分成：男女两组。
//        Map<String, Map<String, List<Person>>> result1 = list.stream()
//                .collect(Collectors.groupingby((person) -> {
//                            if (person.getAge() > 60)
//                                return "老年人";
//                            else if (person.getAge() > 40)
//                                return "中年人";
//                            else
//                                return "青年人";
//                        },
//                        groupingby(Person::getSex)));

        // 对分组进行统计
        //统计每一组的人数
//        Map<String, Long> result2 = list.stream()
//                .collect(Collectors.groupingby((person) -> {
//                            if (person.getAge() > 60)
//                                return "老年人";
//                            else if (person.getAge() > 40)
//                                return "中年人";
//                            else
//                                return "青年人";
//                        },
//                        counting()));

        //将所有人按性别划分，并计算每组最大的年龄。
//        Map<String, Integer> map = list.stream()
//                .collect(groupingBy(Person::getSex,
//                        collectingAndThen(
//                                maxBy(comparingInt(Person::getAge)),
//                                Optional::get
//                        )));

        //分区
        //分区是分组的一种特殊情况，它只能分成true、false两组。
//        分组使用partitioningBy方法，该方法接收一个Lambda表达式，该表达是必须返回boolean类型，partitioningBy方法会将Lambda返回结果为true和false的元素各分成一组。
//        partitioningBy方法返回的结果为Map< Boolean,List< T>>。
//        此外，partitioningBy方法和groupingBy方法一样，也可以接收第二个参数，实现二级分区或对分区结果进行统计。
    }

//    private List<Person> filter(List<Person> list, FilterProcessor<Person> filterProcessor) {
//        List<Person> result = new ArrayList<>();
//        for (Person t : list) {
//            if (filterProcessor.process(t))
//                result.add(t);
//        }
//        return result;
//    }

}
