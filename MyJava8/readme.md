��Android Studio��ʹ��Lambda���ʽ

Lambda���ʽ����JDK 8�п�ʼ֧�ֵ�һ�ֺ���ʽ�Ƶ����ԣ��ܹ��������������ڲ�����������Ĵ��롣��Android�У����Դ���ʹ�������ü����������첽�ص��ȳ�����

����ĿǰAndroid�����Ѿ���������Eclipse ��ADTǨ�Ƶ���Android Studio������Android StudioĿǰ�İ汾��û��ֱ��֧��Lambda���ʽ��֧�֣���Ҫ���֧�֣���Ȼ��JDK�汾Ҳ����ʹ��JDK 8 �������ϡ�

����1.����retrolambda�����

������Module:app��build.gradle�����

apply plugin: 'me.tatarka.retrolambda'
      2. ���ñ���ѡ�����Ҳ���Բ�д��

������Module:app��build.gradle��android�ڵ���������´���

compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
 ����3. ����retrolambda����·������Project��build.gradle�е�buildscript->dependencies�ڵ���������´���

classpath 'me.tatarka:gradle-retrolambda:3.2.0' 

����4. ��build.gradle����build

����5.��д���Դ��룬��д������

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView)findViewById(R.id.text);
        textView.setOnClickListener( v -> Toast.makeText(getApplicationContext(), "Lambda", Toast.LENGTH_LONG).show());
    }
 ��  6.���в鿴�Ƿ�����