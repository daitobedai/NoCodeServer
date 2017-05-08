gradle build -x test 打war包  
在/build/libs路径下找到war包  
用pscp命令将war包发送到服务器，命令如下：pscp war包 root@服务器ip:目标路径  
ps aux 查看所有进程，先杀死之前启动的服务  
nohup java -jar war包 & 启动后台服务  
application.yml存放配置  