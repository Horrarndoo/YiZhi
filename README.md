
# 一之
----
### 简介

“一之”是一款资讯类App，主要实现的内容如下：

- 主页（知乎日报、每日新闻、微信精选）
- 干货（每日推荐、干货定制、福利）
- 影视（最新影视资讯，Top250电影推荐）
- 书籍（文学类、文化类、生活类）
- 个人（头像设置）

项目地址：[https://github.com/Horrarndoo/YiZhi](https://github.com/Horrarndoo/YiZhi)

----

### 运行截图
#### 运行时权限&懒加载
![运行时权限][img_permission] ![懒加载][img_lazy_init] 


#### 主页
![主页1][img_home1] ![主页2][img_home2]
![主页_项目主页][img_home_project_home] ![主页_夜间模式][img_home_night_mode]
![主页_分享][img_home_share]

#### 干货
![干货_主页刷新][img_gankio_refesh] ![干货_定制][img_gankio_custom]
![干货_福利][img_gankio_fuli]

#### 影视&书籍
![影视_hot][img_movie_hot] ![书籍_hot][img_book]

#### 个人
![个人_head1][img_person_head] ![个人_head2][img_person_head2]

----

### 敏感权限说明

```xml
	<!--用于进行网络定位-->
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
	<!--用于访问GPS定位-->
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
	<!--获取运营商信息，用于支持提供运营商信息相关的接口-->
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	<!--用于访问wifi网络信息-->
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	<!--这个权限用于获取wifi的获取权限-->
	<uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
	<!--用于访问网络-->
	<uses-permission android:name="android.permission.INTERNET"/>
	<!--用于读取手机当前的状态-->
	<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	<!--写入扩展存储，向扩展卡写入数据，用于保存在线图片数据-->
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
	<!--用于头像设置-->
	<uses-permission android:name="android.permission.CAMERA"/>
	<!--用于分享App-->
	<uses-permission android:name="android.permission.MANAGE_ACCOUNTS"/>
	<!--用于分享App-->
    <uses-permission android:name="android.permission.GET_ACCOUNTS"/>
```

----

### 下载地址

Fir.im: http://fir.im/s4lr

----

### API来源

知乎日报以及每日新闻API源于Github开源项目中提供的API，干货API使用的是GankIo提供的API，微信精选API来源于聚合数据，影视以及图书都是来源于豆瓣API。

1. [知乎日报][zhihu]
2. [每日新闻][news]
3. [微信精选][juhe]
4. [干货][gankio]
5. [影视][douban1]
6. [图书][douban2]

----

### 开源项目

1. [Rxjava][1]
2. [RxAndroid][2]
3. [Retrofit][3]
4. [Glide][4]
5. [Butterknife][5]
6. [Fragmentation][6]
7. [Logger][7]
8. [BaseRecyclerViewAdapterHelper][8]
9. [PhotoView][9]
10. [CircleImageView][10]
11. [RxPermissions][11]

----

### 感谢

感谢Github的各位开源作者提供宝贵的代码参考。

- [@evan.wang][evan]
- [@Jingbin][Jingbin]
- [@SuperMan42][SuperMan42]

[1]: https://github.com/ReactiveX/RxJava
[2]: https://github.com/ReactiveX/RxAndroid
[3]: https://github.com/square/retrofit
[4]: https://github.com/bumptech/glide
[5]: https://github.com/JakeWharton/butterknife
[6]: https://github.com/YoKeyword/Fragmentation
[7]: https://github.com/orhanobut/logger
[8]: https://github.com/CymChad/BaseRecyclerViewAdapterHelper
[9]: https://github.com/chrisbanes/PhotoView
[10]: https://github.com/hdodenhof/CircleImageView
[11]: https://github.com/tbruyelle/RxPermissions
[11]: https://github.com/tbruyelle/RxPermissions
[11]: https://github.com/tbruyelle/RxPermissions

[zhihu]: https://github.com/izzyleung/ZhihuDailyPurify
[news]: https://github.com/OCNYang/QBox
[juhe]: https://www.juhe.cn/docs/api/id/147
[gankio]: https://gank.io/api
[douban1]: https://developers.douban.com/wiki/?title=movie_v2
[douban2]: https://developers.douban.com/wiki/?title=book_v2

[evan]: https://github.com/wsy858
[Jingbin]: https://github.com/youlookwhat
[SuperMan42]: https://github.com/SuperMan42

[img_permission]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/flash_permission.gif
[img_lazy_init]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_lazy_init.gif
[img_home1]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_detail_pic.gif
[img_home2]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_detail_pic2.gif
[img_home_night_mode]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_night_mode.gif
[img_home_project_home]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_project_home.gif
[img_home_share]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/home_share.gif
[img_gankio_refesh]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/gankio_refesh.gif
[img_gankio_custom]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/gankio_custom.gif
[img_gankio_fuli]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/gankio_fuli.gif
[img_book]: https://github.com/Horrarndoo/imageAssets/blob/master/yizhi/book.gif?raw=true
[img_head_setting]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/head_setting.gif
[img_movie_hot]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/movie_hot.gif
[img_person_head]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/person_head.gif
[img_person_head2]: https://raw.githubusercontent.com/Horrarndoo/imageAssets/master/yizhi/person_head2.gif


----

### 关于作者

Horrarndoo

- csdn: http://blog.csdn.net/oqinyou
- 简书：http://www.jianshu.com/u/5c92e4ae9dc3
- github: https://github.com/Horrarndoo
- e-mail: 237077219@qq.com
- QQ: 237077219

## LICENSE

    Copyright 2017 Horrarndoo

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
