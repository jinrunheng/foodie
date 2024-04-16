## TODO
finished step1,week4,2-4

## 运行前端项目

- 下载 Tomcat：[下载地址](https://tomcat.apache.org/) ；我使用的版本为 apache-tomcat-8.5.69
- 解压安装包后，进入到 webapps 目录下，放入整个前端项目(拷贝项目根目录下的 foodie-shop 目录)
- 进入到 apache-tomcat 目录下的 bin 目录，并运行 startup 启动程序；使用命令：`./startup.sh`
- 我们可以通过修改 conf 目录下的 server.xml 来自定义 tomcat 访问的端口号；浏览器访问 `localhost:[port]`，如果未修改，默认端口号为 `8080`
- 页面地址：`http://localhost:8080/foodie-shop/index.html`
- 关闭 tomcat：在 apache-tomcat 目录下的 bin 目录，运行：`./shutdown.sh`

## Swagger2 
- `http://localhost:8088/doc.html`

## 核心模块
### 购物车
Cookie + Redis

     