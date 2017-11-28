# mvp+rxjava+retrofit网络请求框架

[更详细的查看Wiki](https://github.com/Alin520/Mvp-Rxjava-Retrofit/wiki/%E7%AC%AC%E4%B8%80%E7%AF%87--%E5%BC%80%E9%A2%98%E7%AF%87-mvp-rxjava-retrofit%E6%9E%B6%E6%9E%84) <br>
[点击我查看CSDN文档](http://blog.csdn.net/hailin123123/article/details/78643330)

从事Android四年多来，接手的项目也是各式各样，所以，不同项目中用的框架也是五花八门，项目中涉及的技术也是形形色色，这其中，有的开始用的很爽，但是，随着版本的不断迭代，慢慢的发现有很别扭，被诟病了。有的是从始至终就一路暴爽。比如：之前的一个项目，是购物平台类的。需要是这样的：开始商家端和买家端在一个应用中，后来要把商家端的独立出来，单独开发一个商家端的应用。这个就比较坑了。由于项目是mvc的架构，项目耦合性太大了，尤其是商家端和买家端的逻辑处理，很多都是关联的。这个太改版就太痛苦了。后来想想，如果当时架构是MVP架构，降低耦合，业务分离，那么，这个过程会开心很多的。<br>
后来，retrofit+RxJava出来了，看到这种链式编程，代码易读性强。尤其是结合mvp简直就是无缝连接，对网路请求以及错误处理、数据变换真的是屌炸天了。瞬间就对他爱不释手了。<br> 
# 一、大概会分七个篇幅来阐述。分别是：
 ###    [一、开题篇。](https://github.com/Alin520/Mvp-Rxjava-Retrofit/wiki/%E7%AC%AC%E4%B8%80%E7%AF%87--%E5%BC%80%E9%A2%98%E7%AF%87-mvp-rxjava-retrofit%E6%9E%B6%E6%9E%84/_edit)
###     [二、mvp框架搭建。](https://github.com/Alin520/Mvp-Rxjava-Retrofit/wiki/%E7%AC%AC%E4%BA%8C%E7%AF%87--MVP%E6%A1%86%E6%9E%B6%E5%AE%8C%E6%88%90)
###     三、常用的框架搭建（如上三种常见的框架）
###     四、普通网络请求的封装。
###     五、rxjava+retrofit网络请求的封装。
###     六、缓存的处理。
###     七、补充偏 。
	
# 二、框架的优点：

	1、本框架支持两种架构，更加灵活使用。如果是MVC，则子类Activity只需继承CommonActivity，如果是使用mvp架构，则子类的Activity需要继承MvpActivity。
	2、Presenter的生命周期与Activity、Fragment的生命周期绑定，Activity、Fragment创建（onCreate），则Presenter同时也被创建。Activity、Fragment被销毁（onDestory），Presenter同时也被销毁。
	3、Presenter进行了缓存、复用，防止多次创建消耗内存。
	4、对异常情况，Presenter的缓存处理，如横竖屏切换、内存不够杀死APP。
	5、用注解的方式，直接获取需要的Presenter
	6、其他的优点：toast提示，Handler的获取、针对不同的状态页面（成功、失败、数据为空的页面）处理等。
	
	
# 三、涉及的技术大概有：<br>
eventBus、rxjava+okhttp、数据解析fastjson、图片加载fresco、沉浸式状态栏systembartint、下拉刷新框架   TwinklingRefreshLayout、注解框架butterknife、官方权限适配easypermissions等火热技术。<br>
其中，也设计到Java中常用的反射技术，也会涉及到几种设计模式：build模式、单例模式、简单工厂、抽象工厂模式、代理模式、策略模式等。 

## 具体使用说明：
### (1)步骤：
1、TestActivity extends MvpActivity
2、泛型中指定TestActivity 的Presenter逻辑处理TestPresenter，这样获取TestPresenter只需要直接调用createPresenter（）方法即可获取。
3、开始进行网络（或其他逻辑）请求是，只需要调用startWork（）方法即可，即mPresenter.startWork()

### (2)再来看看逻辑处理类Presenter
TestPresenter中需用重写getDataFromNet（）方法，因为在这里考虑一般的都会有网络请求业务。如果还要获取更多的数据，则可以调用getMoreDataFromNet（）方法。在getDataFromNet（）请求成功后，分别调用 mView.showContentData(bean)、mView.showContentView()方法。再在TestActivity 中设置数据。
	
# 四、优点阐述：
   1、Presenter的生命周期与Activity、Fragment的生命周期绑定，Activity、Fragment创建（onCreate），则Presenter同时也被创建。Activity、Fragment被销毁（onDestory），Presenter同时也被销毁。 
   2、Presenter进行了缓存、复用，防止多次创建消耗内存。在PresenterStorage中，使用软引用new WeakHashMap<br>
   
## Presenter缓存思路总结：
### <1>.存储过程：
   1）用HashMap缓存Presenter。<br>
   2）将PresenterId缓存到bundle中，若内存中有新的bundle出现时（如横竖屏切换、内存吃紧强制退出app，保存了上次退出的bundle），都重新缓存PresenterId，onSaveInstanceState().<br>
注意：每次bundle出现时，都需要重新缓存bundle，onRestoreInstanceState()<br>

### <2>.取出过程：
   1）先将缓存在bundle中的PresenterId取出，然后通过PresenterId从WeakHashMap集合中取出Presenter<br>
   2）若内存中没有缓存bundle，然后PresenterFactory中回调获取Presenter<br>

# 五、其他优点：
1、弹窗toast<br>
   首先注解@TargetLog(类名.class) ， 然后<br>
若在fragment、activity中，只需要调用showLog(“打印的日志内容”);<br>
若其他类中LogUtil.showLog(类名.class,“打印的日志内容”);<br>
注意：默认是debug级别，如果需要指定日志级别（如是error级别），则showLog(“打印的日志内容”,LogUtil.Logs.e);<br>

2、Handler的获取<br>
  使用了build模式，在HandlerUtil中封装了handler的对象的获取，以及message的发送<br>

更详细的可以去看CSDN文档 http://blog.csdn.net/hailin123123/article/details/78643330
如果有不明白的或者发现有问题的，请联系我。我的邮箱是：649605126@qq.com


[更详细的查看Wiki](https://github.com/Alin520/Mvp-Rxjava-Retrofit/wiki/%E7%AC%AC%E4%B8%80%E7%AF%87--%E5%BC%80%E9%A2%98%E7%AF%87-mvp-rxjava-retrofit%E6%9E%B6%E6%9E%84) <br>
[点击我查看CSDN文档](http://blog.csdn.net/hailin123123/article/details/78643330)
