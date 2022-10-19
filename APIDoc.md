[TOC]

## 查询系统操作日志

**接口功能**

> 获取某位用户在某段时间（可选），某种状态的操作记录

**地址**

> /GetRecordLog

**HTTP请求方式**

> POST

**请求参数**

> |参数|必选|类型| 说明                |
> |:-----  |:-------|:------------------|-----                               |
> |userAc    |ture    |String| 用户的账号 |
> |status |true |String| 用户操作的状态：success 或 failed |
> |pageNum |true |Integer| 查询第几页 |
> |pageSize |false |Integer| 每页显示条数，默认为 5 |
> |opTimeStart |false |Long| 查询的开始时间戳（毫秒级），默认是最晚时间戳 |
> |opTimeEnd |false |Long| 查询的结束时间戳（毫秒级），默认是最新时间戳 |

**返回字段**

> |字段类型|说明|
> |:-----   |:------|
> |PageInfo<RecordLog>   | 分页后的包装类 |

**接口示例**

> 地址：http://localhost:8088/GetRecordLog?pageNum=1&pageSize=5&userAc=2021013024&status=success
``` javascript
{
  "total": 31,
  "list": [
    {
      "opTime": 1665929538449,
      "opAc": "2021013024",
      "status": "success",
      "opType": "LOGIN",
      "reqUrl": "/Login",
      "reqMtd": "com.jiang.fzte.controller.UserController.login",
      "opDesc": "用户登录",
      "errMsg": null,
      "timeCsm": "438",
      "opIp": "127.0.0.1"
    },
    {
      "opTime": 1665966384742,
      "opAc": "2021013024",
      "status": "success",
      "opType": "LOGIN",
      "reqUrl": "/Login",
      "reqMtd": "com.jiang.fzte.controller.UserController.login",
      "opDesc": "用户登录",
      "errMsg": null,
      "timeCsm": "406",
      "opIp": "127.0.0.1"
    },
    {
      "opTime": 1665966397414,
      "opAc": "2021013024",
      "status": "success",
      "opType": "LOGIN",
      "reqUrl": "/Login",
      "reqMtd": "com.jiang.fzte.controller.UserController.login",
      "opDesc": "用户登录",
      "errMsg": null,
      "timeCsm": "402",
      "opIp": "127.0.0.1"
    },
    {
      "opTime": 1665966466349,
      "opAc": "2021013024",
      "status": "success",
      "opType": "LOGIN",
      "reqUrl": "/Login",
      "reqMtd": "com.jiang.fzte.controller.UserController.login",
      "opDesc": "用户登录",
      "errMsg": null,
      "timeCsm": "395",
      "opIp": "127.0.0.1"
    },
    {
      "opTime": 1665966513508,
      "opAc": "2021013024",
      "status": "success",
      "opType": "LOGIN",
      "reqUrl": "/Login",
      "reqMtd": "com.jiang.fzte.controller.UserController.login",
      "opDesc": "用户登录",
      "errMsg": null,
      "timeCsm": "440",
      "opIp": "127.0.0.1"
    }
  ],
  "pageNum": 1,
  "pageSize": 5,
  "size": 5,
  "startRow": 1,
  "endRow": 5,
  "pages": 7,
  "prePage": 0,
  "nextPage": 2,
  "isFirstPage": true,
  "isLastPage": false,
  "hasPreviousPage": false,
  "hasNextPage": true,
  "navigatePages": 8,
  "navigatepageNums": [
    1,
    2,
    3,
    4,
    5,
    6,
    7
  ],
  "navigateFirstPage": 1,
  "navigateLastPage": 7
}
```



## 获取访问的IP数

**接口功能**

> 获取某一天网站访问的IP数

**地址**

> /GetIP

**HTTP请求方式**

> GET

**请求参数**

> |参数|必选|类型| 说明                |
> |:-----  |:-------|:------------------|-----                               |
> |date    |ture    |String| 格式为 yyyyMMdd 的字符串 |

**返回字段**

> |字段类型|说明|
> |:-----   |:------|
> |String   |在 date 这天访问网站的 IP 数    |
> |null |该天数据不存在 |

**接口示例**

> 地址：[http://localhost:8088/GetIP?date=2022-10-09](http://localhost:8088/GetIP?date=2022-10-09)
``` javascript
19
```

## 获取访问的UV数

**接口功能**

> 获取某一天网站访问的UV数

**地址**

> /GetUV

**HTTP请求方式**

> GET

**请求参数**

> | 参数 | 必选 | 类型   | 说明                     |
> | :--- | :--- |:-----------------------| -------------------------- |
> | date | ture | String | 格式为 yyyyMMdd 的字符串      |

**返回字段**

> | 字段类型 | 说明                         |
> | :------- | :--------------------------- |
> | String   | 在 date 这天访问网站的 UV 数 |
> | null     | 该天数据不存在               |

**接口示例**

> 地址：[http://localhost:8088/GetUV?date=2022-10-09](http://localhost:8088/GetUV?date=2022-10-09)
``` javascript
10
```

## 获取访问的PV数

**接口功能**

> 获取某一天网站访问的PV数

**地址**

> /GetPV

**HTTP请求方式**

> GET

**请求参数**

> | 参数 | 必选 | 类型   | 说明                     |
> | :--- | :--- |:-----------------------| -------------------------- |
> | date | ture | String | 格式为 yyyyMMdd 的字符串      |

**返回字段**

> | 字段类型 | 说明                         |
> | :------- | :--------------------------- |
> | String   | 在 date 这天访问网站的 PV 数 |
> | null     | 该天数据不存在               |

**接口示例**

> 地址：[http://localhost:8088/GetPV?date=2022-10-09](http://localhost:8088/GetPV?date=2022-10-09)
``` javascript
10
```



