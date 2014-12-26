# fastconfig-maven-plugin 使用指南
---
在项目中我们需要对应不同的(**开发**,**测试**,**生产**)环境定制不同的**参数**(如:在**开发,测试**环境中我们的日志级别会设置成*debug*这样方便我们调试.但是在**生产**环境中我们的日志通常都不可能是*debug*级别,这时我们就需要对应不同的环境设置不同的参数,并且每次都会进行该~~繁琐~~的工作).

fastconfig可以通过定制参数,在打包之前自动根据环境替换对应的参数设置,减少手动配置的工作量. 在之前有[AutoConfig](http://www.openwebx.org/docs/autoconfig.html)及[portable-config-maven-config](https://github.com/juven/portable-config-maven-plugin)去做这样的工作.

fastconfig的优点:
  1. 几乎支持所有的文本文件替换
  2. 支持替换的模式丰富(properties, [xpath](http://www.w3schools.com/xpath/xpath_examples.asp),      [jsonpath](http://goessner.net/articles/JsonPath/), [regex](http://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html))
  3. 支持多文件替换
  4. 支持Maven参数引用

###[样例](https://github.com/skfiy/fastconfig-maven-plugin/tree/master/src/it/simple-it)
1. pom.xml配置插件
  ```xml
  <plugin>
    <groupId>org.skfiy.maven.plugin.fastconfig</groupId>
    <artifaceId>fastconfig-maven-plugin</artifaceId>
    <version>1.0</version>
    <executions>
  	<execution>
  	  <id>config-resources</id>
  	  <goals>
  	    <goal>configure</goal>
  	  </goals>
      </execution>
    </executions>
    <configuration>
      <config>fast-config.xml</config>
    </configuration>
  </plugin>
  ```

2. fast-config.xml配置文件
  ```xml
  <fast-config>
    <config-file path="test.properties">
      <replacement expression="test.name">fastconfig-unit hello</replacement>
      <replacement expression="test.version">0.0.1</replacement>
    </config-file>
    <config-file path="test.xml">
      <replacement expression="/server/port">80</replacement>
      <replacement expression="//host[@id='1']">192.168.1.1</replacement>
      <replacement expression="//host[@id='2']">192.168.1.2</replacement>
      <replacement expression="/server/mode/@value">run</replacement>
    </config-file>
    <config-file path="test.json">
      <replacement expression="$.store.book[?(@.author='Evelyn Waugh')].author">Kevin Zou</replacement>
      <replacement expression="$.store.bicycle.color">${hello.param}</replacement>
      <replacement expression="$.store.bicycle.price">29.99</replacement>
    </config-file>
    <config-file path="test.html" mode="regex">
      <replacement expression="&lt;p&gt;(.*?)&lt;/p&gt;">&lt;a&gt;$1__Testing__\\__\$2&lt;/a&gt;</replacement>
    </config-file>
  </fast-config>
  ```

+ **config-file**节点配置所需要替换的配置文件信息
  * **path**属性表示配置文件的路径
  * **mode**替换模式(属性值:*property*, *xpath*, *jsonpath*, *regex*), 如果文件的后缀名为.properties, .xml, .json时fastconfig会根据文件后缀名自动识别替换模式. 如果你替换的模式为*regex*那则必须显示声明fastconfig无法自识别, 因为该模式可以用于任何文本文件的替换工作.

+ **replacement**节点具体的替换表达式及替换结果
  * **expression**替换表达式支持(property, xpath, jsonpath, regex)
  * **#text**替换的结果(可使用Maven参数,如**${hello.param}**).
