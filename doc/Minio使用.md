### 一、 下载  minio 客户端（Windows）  minio.exe
1，将下载下来的minio.exe放入D:\Net_Program\Net_MinIO中
2，以管理员身份运行CMD并定位到D:\Net_Program\Net_MinIO，输入如下命令启动minio服务
命令：.\minio.exe server D:\Net_Program\Net_MinIO\Data
其中红色位子提示修改默认的用户名和密码，无须理会。
3，将MinIO设置为Windows系统服务
使用NSSM工具将其设置为系统服务
先下载 NSSM 并解压
打开NSSM的win64的文件夹
    然后按住Shift+鼠标右键点击文件夹空白处，选择“在此处打开Powershell窗口”
    然后输入.\nssm install调出设置窗体
    在弹出的窗体中，Application选项卡中，
        Path：选择kafka-server-start.bat所在的路径，如D:\Net_Program\Net_MinIO\minio.exe
        Startup directory：选择Path后，Startup directory选择会自动填充
        Argument：为启动服务的参数，此处填写server D:\Net_Program\Net_MinIO\Data
        Service name：服务名称，如MinIO-Service
    设置完成后点击Install service按钮即完成，此时去查看系统服务有多了一个叫MinIO-Service的服务了
    
4，访问：http://192.168.50.249:9000
5，用户名密码：minioadmin/minioadmin

