### 安装 Nginx
解压 nginx-1.20.2

### 配置
D:\tools\nginx-1.20.2\conf 目录下  打开 nginx.conf 文件

```
server 内添加

listen       80;
        server_name  localhost;

	#######srb 三个微服务 反向代理 开始############
	location ~ /core/ {           
		proxy_pass http://localhost:8110;
	}

	location ~ /sms/ {           
		proxy_pass http://localhost:8120;
	}

	location ~ /oss/ {           
            proxy_pass http://localhost:8130;
	}
	#######srb 三个微服务 反向代理 结束############

        #charset koi8-r;
```

### 启动 nginx
在D:\tools\nginx-1.20.2 目录下cmd 命令行工具
start nginx.exe  即可

启动：start nginx.exe
停止：nginx.exe -s stop
重新加载：nginx.exe -s reload

测试：
http://localhost/
出现  Welcome to nginx! 正常

