## GitHub 建立个人Maven 仓库

> Maven 仓库：[wxw-maven-repo](https://github.com/GitHubWxw/wxw-maven-repo) 

---

### 一、GitHub 上建立个人自己的仓库

- 官方推荐文档：[配置Apache Maven与GitHub软件包一起使用](https://docs.github.com/cn/free-pro-team@latest/packages/guides/configuring-apache-maven-for-use-with-github-packages) 
- Maven官网: https://maven.apache.org/repository/index.html

#### 1.1 在GitHub上创建自己的仓库

- **wxw-mvn-repo** 如下图所示：

![1607667900026](assets/1607667900026.png) 

#### 1.2 配置本地setting文件

有两种选择

1. 可以选择配置username和password
2. 选择配置Personal access tokens<OAUTH2TOKEN>（也是填充到password字段）

**（1）优先使用Personal access tokens**

因为有些公司内部可能会对用户名和密码做限制，前者即是GitHub的用户名以及密码，后者需要在GitHub上进行申请，步骤如下：

- setting——>Developer settings ——>Personal access tokens——>generate new Token

具体如下图所示：

1. **setting** 

![1607668255905](assets/1607668255905.png)  



2. **Developer settings** 

    ![1607668752616](assets/1607668752616.png) 

3. **Personal access tokens** 

![1607668671208](assets/1607668671208.png)  

4. **generate new Token** 

![1607668831455](assets/1607668831455.png) 

点击化后会获得Token值；

**（2）找到本地的maven settings文件，配置server：** 

   ```xml
	<!--设置用户信息-->
    <server>
      <id>github</id>
      <username>GitHubWxw</username>
      <password>github生成Oauth2.0 权限校验 token值</password>
    </server>
   ```

 注：此token会不断发生变化，每一次查看都会更新，更新后之前的不可用，所以要妥善保存

**（3）回到自己要发布的maven项目配置pom**

**增加本地临时存储库：** 

在pom文件添加插件配置：

```xml
            <!--本地 临时存储库-->
            <plugin>
                <artifactId>maven-deploy-plugin</artifactId>
                <version>2.8.2</version>
                <configuration>
                    <!-- altDeploymentRepository ：编译本地仓库包插件，生成target/wxw-maven-repo下面 -->
                    <altDeploymentRepository>
                        internal.repo::default::file://${project.build.directory}/wxw-maven-repo
                    </altDeploymentRepository>
                </configuration>
            </plugin>
```

执行：` mvn clean deploy`  会在本地生成你要发布的包

![clip_image009[6]](assets/1628496-20201122141631701-942244277.png) 

![clip_image010[6]](assets/1628496-20201122141634753-1175897428.png) 

**配置远程的 github 服务** 

为了方便管理 github 官方给了更方便的解决办法，在pom添加如下配置：

```xml
	<!--版本控制-->
    <properties>
        <github.global.server>github</github.global.server>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>8</java.version>
    </properties>
```

```xml
<!--maven 在需要发布的项目使用 发布管理-->
    <distributionManagement>
        <repository>
            <id>github</id>
            <name>GitHub OWNER Apache Maven Packages</name>
            <url>https://maven.pkg.github.com/GitHubWxw/wxw-maven-repo</url>
        </repository>
    </distributionManagement>
```

再次执行如下命令会发布到 GitHub仓库中：

```bash
mvn clean deploy
```

![1607670274297](assets/1607670274297.png) 

如上图即为成功后，我们可以查看我们的wxw-maven-repo项目，发现内容已经发生了变化：

![1607670357142](assets/1607670357142.png) 

### 二、使用依赖包

#### 2.1 在新项目的pom文件中增加配置

```xml
<repositories>
    <repository>
        <id>mvn-repo</id>
        <!-- https://raw.github.com/用户名/仓库名/分支名 -->
        <url>https://raw.github.com/GitHubWxw/wxw-maven-repo/master</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```

#### 2.2 在新项目的pom文件中增加依赖：

```xml
<dependencies>
    <dependency>
        <groupId>com.wxw</groupId>
        <artifactId>wxw-tools</artifactId>
        <version>1.0.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

 **相关文章** 

1. [GitHub建立个人Maven仓库](https://www.cnblogs.com/aeolian/p/12444569.html)



