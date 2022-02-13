## 脚本
```sql 
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR,
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);


```
## 资料
[Elastic中文社区](https://elasticsearch.cn/) \
[视频教程地址](https://www.bilibili.com/video/BV1r4411r7au?p=20) \
[h2数据库官网](http://www.h2database.com/html/quickstart.html) \
[Markdown语法](https://markdown.com.cn/basic-syntax/line-breaks.html) \
[BootStrap中文网](https://v3.bootcss.com/) \
[Maven仓库](https://mvnrepository.com/)