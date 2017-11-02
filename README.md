# MyNewsApp
## Introduction 
由于部门变量命名和项目结构等的更新，MyNewsApp无法正常运行，此项目时对MyNewsApp的重新提交。
主要向知乎开放API https://news-at.zhihu.com/api/4/news/latest 请求数据，返回news的title，id以及imageUrl，这个API不返回content。需要根据id再去请求content，url结构是https://news-at.zhihu.com/api/4/news/ + id。
## 实现的功能
登录：将account 和 password 存放到sqlite中，登录的时候再取出然后比较。
news列表：需要进行网络请求，如果没有开启数据开关或者wifi，则请求不了数据。对于news列表item的点击，然后请求该详情页面，返回的时css和html相关代码，需要进行webview的相关设置。
