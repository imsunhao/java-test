### 如何开发

#### 使用 docker-compose 进行快速搭建开发环境

- 安装docker, 安装docker-compose
- 启动命令 docker-compose up -d

#### 如何使用 IDEA 进行开发

- 设置中文，选择插件里面找一下
- 导入项目后，右侧有个maven按钮，点击重新加载
- 完成后就可以启动了，中间上面有个绿色的play按钮。

### 注册

http://127.0.0.1:8801/api/user/register

```json
{
    "phone": "18899889988",
    "username": "admin",
    "password": "123456",
    "nickname": "测试账号"
}
```

### 登录

http://127.0.0.1:8801/api/user/login

```json
{
    "username": "admin",
    "password": "123456"
}
```

### 分页查询

http://127.0.0.1:8801/api/user/list
  
* username = "admin" 
 
    ```json
    {
        "where": {
            "username": "admin"
        }
    }
    ```

    ```json
    {
        "where": {
            "username": {
                "_eq": "admin"
            }
        }
    }
    ```

* username != "admin"

    ```json
    {
        "where": {
            "username": {
                "_ne": "admin"
            }
        }
    }
    ```

* username is null

    ```json
    {
        "where": {
            "username": {
                "_is": null
            }
        }
    }
    ```

* username is not null

    ```json
    {
        "where": {
            "username": {
                "_not": null
            }
        }
    }
    ```

* username like "%admin%"

    ```json
    {
        "where": {
            "username": {
                "_like": "%admin%"
            }
        }
    }
    ```

* username not like "%admin%"

    ```json
    {
        "where": {
            "username": {
                "_notLike": "%admin%"
            }
        }
    }
    ```

* username in ("admin", "test")

    ```json
    {
        "where": {
            "username": {
                "_in": ["admin", "test"]
            }
        }
    }
    ```

* username not in ("admin", "test")

    ```json
    {
        "where": {
            "username": {
                "_notIn": ["admin", "test"]
            }
        }
    }
    ```

* enableFlag > 1

    ```json
    {
        "where": {
            "enableFlag": {
                "_gt": 1
            }
        }
    }
    ```

* enableFlag >= 1

    ```json
    {
        "where": {
            "enableFlag": {
                "_gte": 1
            }
        }
    }
    ```

* enableFlag < 1

    ```json
    {
        "where": {
            "enableFlag": {
                "_lt": 1
            }
        }
    }
    ```

* enableFlag <= 1

    ```json
    {
        "where": {
            "enableFlag": {
                "_lte": 1
            }
        }
    }
    ```

* 复杂查询

    ```json
    {
        "where": {
            "_or": {
                "username": {
                    "_eq": "admin1"
                },
                "phone": "18899889981"
            },
            "enableFlag": 1
        },
        "orderBy": [
            "enableFlag",
            "-id"
        ],
        "page": 0,
        "size": 1000
    }
    ```
  
    ```json
    {
        "where": {
            "_and": {
                "username": {
                    "_eq": "admin1"
                },
                "phone": "18899889981"
            },
            "enableFlag": 1
        },
        "orderBy": [
            "enableFlag",
            "-id"
        ],
        "page": 0,
        "size": 1000
    }
    ```