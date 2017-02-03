在Android Studio中使用Lambda表达式

Lambda表达式是在JDK 8中开始支持的一种函数式推导语言，能够大量减少匿名内部类那种冗余的代码。在Android中，可以大量使用在设置监听，设置异步回调等场景。

　　目前Android开发已经渐渐到从Eclipse 的ADT迁移到了Android Studio，但是Android Studio目前的版本还没有直接支持Lambda表达式的支持，需要插件支持，当然，JDK版本也必须使用JDK 8 或者以上。

　　1.引入retrolambda插件：

　　在Module:app的build.gradle中添加

apply plugin: 'me.tatarka.retrolambda'
      2. 设置编译选项（可能也可以不写）

　　在Module:app的build.gradle的android节点中添加如下代码

compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
 　　3. 引入retrolambda的类路径，在Project的build.gradle中的buildscript->dependencies节点中添加如下代码

classpath 'me.tatarka:gradle-retrolambda:3.2.0' 

　　4. 对build.gradle进行build

　　5.编写测试代码，简单写法如下

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        textView.setOnClickListener( v -> Toast.makeText(getApplicationContext(), "Lambda", Toast.LENGTH_LONG).show());
    }
 　  6.运行查看是否正常